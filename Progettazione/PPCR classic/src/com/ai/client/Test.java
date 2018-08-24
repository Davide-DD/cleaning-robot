package com.ai.client;

import java.util.ArrayList;
import java.util.List;

import com.ai.components.Node;
import com.ai.components.Path;
import com.ai.ppcr.Ppcr;

public class Test 
{
	public static void main(String[] args) 
	{
		//Prova api: 9 colonne da 160ms e 8 righe da 160ms.
		loop(8,9);			
		//Prova api: 15 colonne da 96ms e 14 righe da 91ms.
		//loop(14,15);
		
		
		//Prova algoritmo vero e proprio
		/*Client cl = new Client();

		int rows = 8;
		int cols = rows+1;
		
		//PPCR inizialization
		Node initialNode = new Node(0, 0);
		Node finalNode = new Node(rows-1, cols-1);

		Ppcr ppcr = new Ppcr(rows, cols, initialNode, finalNode);    	    			

		//Blocchi aggiunti subito
		/*List<int[]> blocks = new ArrayList<>();
	    blocks.add(new int[]{2,0});
	    ppcr.setBlocks(blocks);

		Path path = ppcr.sweep(); 
		path.printPath();
		//System.out.println(cl.getActions(path, "up"));
		cl.sendCommands(cl.getActions(path, "up"),rows,cols);*/
		
		
    }	
	
	public static void loop(int rows, int cols)
	{
		/*Conoscenza:
		La lunghezza della stanza è rappresentata da 9 colonne da 160ms.
	    La larghezza della stanza è rappresentata da 8 righe da 160ms.
	    Perciò ad esempio:
	    15 colonne e 14 righe
	    Tempo colonna: 9:15=x:160   -> x è 96
	    Tempo riga: 8:14=x:160 -> x è circa 91*/
		
		int colTime = 160*9/cols;
		int rowTime = 160*8/rows;
		
		
		try {
			Client.initClientConn();
			System.out.println("STARTING ... ");
			while(true)
			{			
				Client.mbotRight(colTime);
				Thread.sleep(1000);
				for(int i=0;i<cols; i++)
				{
					Client.mbotForward(colTime);
					Thread.sleep(1000);
				}	
				Client.mbotRight(rowTime);
				Thread.sleep(1000);
				for(int i=0;i<rows; i++)
				{
					Client.mbotForward(rowTime);
					Thread.sleep(1000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
