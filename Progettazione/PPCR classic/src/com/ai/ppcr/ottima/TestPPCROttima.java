package com.ai.ppcr.ottima;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ai.astar.AStarNode;
import com.ai.components.Path;
import com.ai.gui.Grid;

public class TestPPCROttima 
{
	public static void main(String[] args) 
    {    
        int rows = 5;
        int cols = 5;
                
        Grid grid = new Grid(rows,cols,true);        
        grid.getStart().addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	    	new Thread() {
    	    		public void run() 
    	    		{
    	    			//PPCR inizialization
    	    	        AStarNode initialNode = new AStarNode(0, 0);
    	    	        AStarNode finalNode = new AStarNode(rows-1, cols-1);
    	    	        
    	    			PpcrOttima ppcr = new PpcrOttima(rows, cols, initialNode, finalNode);    	    			
    	    			
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
