package it.unibo.ppcr.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import it.unibo.hue.components.hueClient;
import it.unibo.hue.components.hueClientSpecific;
import it.unibo.ppcr.ai.ppcr;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket listener = new ServerSocket(1800);
		ppcr.init(ppcr.getGrid());

		// TEST
		boolean testLamp = true;

		System.out.println("Server started!");
		hueClient.setAddress("192.168.1.5");

		Socket socket = listener.accept();
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		String nextMove = ppcr.getNextMove("clear");
		System.out.println(nextMove);
		out.println("manageCommands(SENDER, data(" + nextMove + "))\n");
		if (testLamp)
			hueClientSpecific.blink(null);

		String response = "";
		String found = "";

		try {
			while (true) {
				response = in.readLine();
				System.out.println("Response from Soffritti: " + response);
				found = response.contains("obstacle") ? "obstacle" : "clear";
				nextMove = ppcr.getNextMove(found);
				if (nextMove == "finished") {
					if (testLamp)
						hueClientSpecific.off(null);
					break;
				} else {
					if (nextMove == "obstructedFinalPosition") {
						if (testLamp)
							hueClientSpecific.off(null);
						break;
					}

					else {
						nextMove = "manageCommands(SENDER, data(" + nextMove + "))\n";
						System.out.println("Sending:" + nextMove);
						out.println(nextMove);
					}
				}

			}
		} finally {
			listener.close();
		}
	}
}