package com.ai.bfs;

import com.ai.astar.AStarNode;

public class BfsNode extends AStarNode{

	private int direction;
	private BfsNode parent;

	public BfsNode(int row, int col, int direction, BfsNode parent) {
		super(row, col);
		this.direction = direction;
		this.parent = parent;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public BfsNode getParent() {
		return parent;
	}	

}
