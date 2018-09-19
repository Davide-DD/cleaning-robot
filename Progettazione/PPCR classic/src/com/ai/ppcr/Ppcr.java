package com.ai.ppcr;

import java.util.ArrayList;
import java.util.List;

import com.ai.bfs.BfsNode;
import com.ai.astar.AStar;
import com.ai.astar.AStarNode;
import com.ai.bfs.Bfs;
import com.ai.components.Node;
import com.ai.components.Path;

public class Ppcr {
	
	private Node finalNode;
	private int currentDirection; //direzione: 0=giu, 1=sinistra, 2=su, 3=destra 
	private int rows;
	private int cols;
	private List<int[]> blocks;
	private Node initialNode;
	
	public Ppcr(int rows, int cols, Node initialNode, Node finalNode)
	{
		//PPCR sta per path planning of coverage region
		this.rows=rows;
		this.cols=cols;
		this.initialNode=initialNode;
		this.finalNode=finalNode;        
        this.currentDirection=0;
        this.blocks = new ArrayList<int[]>();
	}
	
	public Path sweep()
	{		
		//Prima parte: bfs -> pulisco tutta la stanza evitando gli ostacoli
		List<BfsNode> firstPhaseBfsPath = new ArrayList<BfsNode>();
		
		Bfs bfs = new Bfs(rows, cols); 		
		bfs.setBlocks(blocks);
		bfs.setVisitedNode(initialNode);
		
		List<BfsNode> partialPath = new ArrayList<BfsNode>();	
		
		Node currentNode = initialNode;
		while(true)
		{
			//Trovo il path per arrivare al prossimo nodo trovato da bfs
			partialPath = bfs.find_nearest_unvisited_node(currentNode, currentDirection);

			
			//Se non esiste, ho pulito tutta la stanza
			if(partialPath==null)
				break;
			
			//Cambio nodo e direzione
			currentNode=partialPath.get(partialPath.size()-1);
			currentDirection=partialPath.get(partialPath.size()-1).getDirection();
			
			firstPhaseBfsPath.addAll(partialPath);			
		}
		
		List<Node> firstPath = getNodes(firstPhaseBfsPath);
		firstPath.add(0, initialNode);

		//Seconda parte: a* -> mi sposto alla posizione finale		
		Node lastFirstPathNode = firstPath.get(firstPath.size()-1);
		AStarNode aStarInitialNode = new AStarNode(lastFirstPathNode.getRow(), lastFirstPathNode.getCol());
		AStarNode aStarFinalNode = new AStarNode(finalNode.getRow(),finalNode.getCol());
		
		AStar aStar = new AStar(rows, cols, aStarInitialNode, aStarFinalNode);
		aStar.setBlocks(blocks);
		List<Node> secondPath = aStar.findPath();
		
		firstPath.remove(firstPath.size()-1);
		firstPath.addAll(secondPath);
		
		return new Path(firstPath);	
	}
	
	private List<Node> getNodes(List<BfsNode> path) {
        List<Node> res = new ArrayList<Node>();
        for(BfsNode node: path)
        	res.add(node);
        return res;
    }
	
	public boolean move() {
		return false;
	}

	public void setBlocks(List<int[]> blocks) {
		this.blocks.addAll(blocks);
	}
}
