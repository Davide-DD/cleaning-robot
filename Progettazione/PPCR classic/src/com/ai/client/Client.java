package com.ai.client;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

import com.ai.components.Node;
import com.ai.components.Path;

public class Client 
{	
	private static String hostName= "localhost";
	private static int port = 8999;
	private static String sep=";";
 	protected static Socket clientSocket ;
	protected static PrintWriter outToServer;
	public Client() 
	{
		try {
			initClientConn();
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}    
	
	protected static void initClientConn() throws Exception {
		 clientSocket = new Socket(hostName, port);
		 outToServer  = new PrintWriter(clientSocket.getOutputStream());
	}

	public static void sendCmd(String msg, int time) throws Exception {
		if( outToServer == null ) return;
		String jsonString = "{ 'type': '" + msg + "', 'arg': "+time+" }";
		JSONObject jsonObject = new JSONObject(jsonString);
		msg = sep+jsonObject.toString()+sep;
		System.out.println("sending msg=" + msg);
		outToServer.println(msg);
		outToServer.flush();
	}
	
	protected void println(String msg) {
		System.out.println(  msg);
	}
 	
	public static void mbotForward(int time) {
 		try {  sendCmd("moveForward",time); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotBackward(int time) {
		try { sendCmd("moveBackward",time); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotLeft(int time) {
		try { sendCmd("turnLeft",time); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotRight(int time) {
		try { sendCmd("turnRight",time); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotStop(int time) {
		try { sendCmd("alarm",time); } catch (Exception e) {e.printStackTrace();}
	}   
	
	public List<Command> getActions(Path path, String startingDirection)
	{
		List<Node> nodesList = path.getPath();
    	List<Command> commands =new ArrayList<Command>();
    	for(int i=0; i<nodesList.size()-1; i++)
    	{
    		Node currentNode = nodesList.get(i);
    		Node nextNode = nodesList.get(i+1);
    		
    		//Movimento verso destra
    		if(nextNode.getCol()>currentNode.getCol())
    		{
    			switch(startingDirection) {
    				case "right":
    					break;
    				case "left":
    					commands.add(new Command("turnRight", "alongTheColumn"));
    					commands.add(new Command("turnRight", "alongTheColumn"));
    					break;
    				case "up":
    					commands.add(new Command("turnRight", "alongTheColumn"));
    					break;
    				case "down":
    					commands.add(new Command("turnLeft", "alongTheColumn"));
    					break;
    			}
    			commands.add(new Command("moveForward", "alongTheColumn"));
    			startingDirection="right";
    		}
    		//Movimento verso sinistra
    		if(nextNode.getCol()<currentNode.getCol())
    		{
    			switch(startingDirection) {
    				case "right":
    					commands.add(new Command("turnLeft", "alongTheColumn"));
    					commands.add(new Command("turnLeft", "alongTheColumn"));
    					break;
    				case "left":
    					break;
    				case "up":
    					commands.add(new Command("turnLeft", "alongTheColumn"));
    					break;
    				case "down":
    					commands.add(new Command("turnRight", "alongTheColumn"));
    					break;
    			}
    			commands.add(new Command("moveForward", "alongTheColumn"));
    			startingDirection="left";
    		}
    		//Movimento verso il basso
    		if(nextNode.getRow()>currentNode.getRow())
    		{
    			switch(startingDirection) {
    				case "right":
    					commands.add(new Command("turnRight", "alongTheRow"));
    					break;
    				case "left":
    					commands.add(new Command("turnLeft", "alongTheRow"));
    					break;
    				case "up":
    					commands.add(new Command("turnLeft", "alongTheRow"));
    					commands.add(new Command("turnLeft", "alongTheRow"));
    					break;
    				case "down":
    					break;
    			}
    			commands.add(new Command("moveForward", "alongTheRow"));
    			startingDirection="down";
    		}
    		//Movimento verso l'alto
    		if(nextNode.getRow()<currentNode.getRow())
    		{
    			switch(startingDirection) {
    				case "right":
    					commands.add(new Command("turnLeft", "alongTheRow"));
    					break;
    				case "left":
    					commands.add(new Command("turnRight", "alongTheRow"));
    					break;
    				case "up":
    					break;
    				case "down":
    					commands.add(new Command("turnLeft", "alongTheRow"));
    					commands.add(new Command("turnLeft", "alongTheRow"));
    					break;
    			}
    			commands.add(new Command("moveForward", "alongTheRow"));
    			startingDirection="up";
    		}   					
    	}	
    	return commands;
	}
	
	public void sendCommands(List<Command> commands, int rows, int cols)
	{
		int colTime = 160*9/(cols-1);
		int rowTime = 160*8/(rows-1);
		
		for(Command command: commands)
			try {
				sendCmd(command.getAction(), command.getDirection().equals("alongTheRow")?rowTime:colTime);
				Thread.sleep(rowTime+50);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
    
}
