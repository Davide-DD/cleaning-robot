package com.ai.ppcr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.ai.components.Node;
import com.ai.components.Path;
import com.ai.gui.Grid;

public class TestPPCR 
{
	public static void main(String[] args) 
    {    
        int rows = 8;
        int cols = 8;
                
        Grid grid = new Grid(rows,cols,false);        
        grid.getStart().addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	    	new Thread() {
    	    		public void run() 
    	    		{
    	    			//PPCR inizialization
    	    	        Node initialNode = new Node(0, 0);
    	    	        Node finalNode = new Node(rows-1, cols-1);
    	    	        
    	    			Ppcr ppcr = new Ppcr(rows, cols, initialNode, finalNode);    	    			
    	    			
    	    			//Blocchi aggiunti subito
    	    			/*List<int[]> blocks = new ArrayList<>();
    	    	        blocks.add(new int[]{2,0});*/
    	    			
    	    			ppcr.setBlocks(grid.getBlocks());
    	    			Path path = ppcr.sweep(); 
    	    			path.printPath();    	    			
    	    			
    	    			grid.drawPath(path,true);
    	    		}
    	    		}.start();   	        
    	    }
    	});
    }
}
