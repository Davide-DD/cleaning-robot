package com.ai.astar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.ai.astar.AStar;
import com.ai.astar.AStarNode;
import com.ai.components.Path;
import com.ai.gui.Grid;

public class Test 
{
	public static void main(String[] args) 
    {
		//Astar inizialization
        AStarNode initialNode = new AStarNode(1, 4);
        AStarNode finalNode = new AStarNode(24, 24);
        int rows = 25;
        int cols = 25;
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
        
        Grid grid = new Grid(rows,cols,false);  
        grid.getFrame().setName("AStar");
        grid.getStart().addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	    	new Thread() {
    	    		public void run() 
    	    		{
    	    			aStar.setBlocks(grid.getBlocks());
    	    	        Path path = new Path(aStar.findPath());
    	    			path.printPath();    	    			
    	    			
    	    			grid.drawPath(path,false);
    	    		}
    	    		}.start();   	        
    	    }
    	});
    }
}
