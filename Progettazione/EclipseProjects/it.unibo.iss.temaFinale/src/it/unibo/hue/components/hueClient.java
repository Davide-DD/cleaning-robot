package it.unibo.hue.components;

import org.json.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;

public class hueClient {
	private static QActorContext ctx = null;
	private static String evId = null;
	private static String hueBridgeAddr = "127.0.0.1";
	// private static String username = "2GgKjwy9JAlW57Dl7qJ1ZRgEpWvjZi6ghN6hesAC";
	private static String username = "newdeveloper";
	private static String hueCmdPrefix = "http://" + hueBridgeAddr + "/api/" + username + "/";
	private static CloseableHttpClient httpclient = null;
	private static Map<Integer, Thread> threads = new HashMap<Integer, Thread>();
	private static String globalInfo = "";
	private static boolean unreacheableLamp = false;

	public static void setQaCtx(QActor qa, String curevId) {
		ctx = qa.getQActorContext();
		evId = curevId;
	}

	public static void setAddress(String addr) {
		hueBridgeAddr = addr;
		hueCmdPrefix = "http://" + hueBridgeAddr + "/api/" + username + "/";
	}

	public static void sendPut(QActor qa, String data, String url) {
		// System.out.println("sendPut " + url);
		try {
			data = data.replace("'", "\"");
			url = hueCmdPrefix + url.replace("'", "\"");
			if (httpclient == null)
				HttpClients.createDefault();
			HttpPut request = new HttpPut(url);
			StringEntity params = new StringEntity(data, "UTF-8");
			params.setContentType("application/json");
			request.addHeader("content-type", "application/json");
			request.addHeader("Accept", "*/*");
			request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
			request.addHeader("Accept-Language", "en-US,en;q=0.8");
			request.setEntity(params);
			CloseableHttpResponse response = httpclient.execute(request);
			getAnswerFromServer(response, "put");

		} catch (Exception e) {
			if (!unreacheableLamp)
				System.out.println("ERROR " + e.getMessage());
			unreacheableLamp = true;
		}
	}

	public static void sendGet(QActor qa, String cmd) {
		try {
			if (httpclient == null)
				httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(hueCmdPrefix + cmd);
			CloseableHttpResponse response = httpclient.execute(httpGet);
			getAnswerFromServer(response, "get");
		} catch (Exception e) {
			if (!unreacheableLamp)
				System.out.println("ERROR " + e.getMessage());
			unreacheableLamp = true;
		}
	}

	public static boolean testConnection(int lamp) {
		unreacheableLamp = false;
		sendGet(null, "lights/" + lamp);
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return unreacheableLamp;
	}

	public static void on(int lamp) {
		sendGet(null, "lights/" + lamp);
		if (threads.get(lamp) != null) {
			threads.get(lamp).interrupt();
			threads.remove(lamp); // Avoiding duplicate threads 
		}
		String cmd = "{'on':true, \"bri\":167}";
		sendPut(null, cmd, "lights/" + lamp + "/state");
	}

	public static void off(int lamp) {
		sendGet(null, "lights/" + lamp);
		if (threads.get(lamp) != null) {
			threads.get(lamp).interrupt();
			threads.remove(lamp); // Avoiding duplicate threads 
		}
		String cmd = "{'on':false}";
		sendPut(null, cmd, "lights/" + lamp + "/state");
	}

	public static void blink(int lamp) {
				
		// Avoiding duplicate blink threads and waste of resources
		if (threads.get(lamp) != null) {
			System.out.println("WARNING: lamp " + lamp + " already blinking, doing nothing.");
			return;
		}
		
		sendGet(null, "lights/" + lamp);
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				String cmd = "";
				while (!Thread.currentThread().isInterrupted()) {
					try {
						cmd = "{'on':false}";
						hueClient.sendPut(null, cmd, "lights/" + lamp + "/state");
						Thread.sleep(1000);
						cmd = "{'on':true, \"bri\":167}";
						hueClient.sendPut(null, cmd, "lights/" + lamp + "/state");
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		});
		threads.put(lamp, t);
		t.start();
	}

	protected static void getAnswerFromServer(CloseableHttpResponse response, String verb) {
		try {
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String output;
			String info = "";
			while ((output = br.readLine()) != null) {
				info = info + output;
			}
			System.out.println("Output from Server .... ");
			System.out.println(info);
			globalInfo = info;
			if (ctx != null) {
				String msg = evId + "(" + verb + ", '" + info + "')";
				// System.out.println(msg);
				QActorUtils.raiseEvent(ctx, "clienthttp", evId, msg);
			}
		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
		}

	}

	public static int getNumLights() {
		sendGet(null, "lights/");
		return new JSONObject(globalInfo).keySet().size();
	}

	// TEST
	public static void main(String args[]) {
		/*
		 * try { on(2); Thread.sleep(2000); off(2); Thread.sleep(2000); blink(2);
		 * Thread.sleep(10000); off(2);
		 * 
		 * } catch (InterruptedException e) { e.printStackTrace(); }
		 * System.out.println(getNumLights());
		 */

	}
}