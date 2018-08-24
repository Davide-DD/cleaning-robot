package com.ai.astar;

import java.util.ArrayList;
import java.util.List;

import com.ai.components.Node;
import com.ai.components.Path;

public class AStarPath {
	private List<AStarNode> path;

	public List<AStarNode> getPath() {
		return path;
	}

	public AStarPath(List<AStarNode> path) {
		this.path = path;
	}

	public void printPath() {
		for (AStarNode node : path) {
			System.out.println(node);
		}
	}

	public int getRow(int i) // Get row dell'iesimo nodo nel path
	{
		return path.get(i).getRow();
	}

	public int getCol(int i) // Get col dell'iesimo nodo nel path
	{
		return path.get(i).getCol();
	}

	public int cleanedSpots() {
		Path simplePath = new Path(new ArrayList<Node>());
		for (AStarNode node : path) {
			Node n = new Node(node.getRow(), node.getCol());
			if (!simplePath.getPath().contains(n))
				simplePath.getPath().add(n);
		}
		return simplePath.getPath().size();
	}
	
	public boolean containsNode(AStarNode node)
	{
		for(Node n: path)
		{
			if(n.getCol()==node.getCol() && n.getRow()==node.getRow())
				return true;
		}
		return false;
		
	}

}
