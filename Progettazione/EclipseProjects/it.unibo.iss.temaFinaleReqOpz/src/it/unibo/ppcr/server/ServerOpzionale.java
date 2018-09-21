package it.unibo.ppcr.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unibo.hue.components.hueClient;
import it.unibo.hue.components.hueClientSpecific;
import it.unibo.ppcr.ai.ppcr;
import it.unibo.ppcr.gui.Grid;

public class ServerOpzionale {

	public static void main(String[] args) throws IOException {
		ServerSocket listener = new ServerSocket(8019);
		Grid grid = new Grid(ppcr.getRows(), ppcr.getCols());
		ppcr.init(grid);

		// TEST
		boolean testLamp = true;

		System.out.println("Server started!");
		//hueClient.setAddress("192.168.1.5");

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
					//RESTART
					JFrame frame = new JFrame();
					String[] options = new String[2];
					options[0] = new String("Yes");
					options[1] = new String("No");
					int choice = JOptionPane.showOptionDialog(frame.getContentPane(),"Wanna clean with optimal path?","Restarting application...", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
					if (choice==0) {
					    ppcr.setOptimal(null, "");
					    System.out.println("\nRestarting with optimal cleaning path!");
					} else {
						System.out.println("\nRestarting with non optimal cleaning path!");
						ppcr.setOptimal(null, "");
					}
					
					//Tempo per aggiornare la pagina localhost (appena scade il tempo, riparte tutto)
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					nextMove = ppcr.getNextMove("clear");
					System.out.println(nextMove);
					out.println("manageCommands(SENDER, data(" + nextMove + "))\n");
					if (testLamp)
						hueClientSpecific.blink(null);
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