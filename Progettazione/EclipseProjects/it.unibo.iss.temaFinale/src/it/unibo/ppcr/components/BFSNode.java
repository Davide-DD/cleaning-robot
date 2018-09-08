package it.unibo.ppcr.components;

public class BFSNode extends Node{

	private int direction;
	private BFSNode parent;

	public BFSNode(int row, int col, int direction, BFSNode parent) {
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

	public BFSNode getParent() {
		return parent;
	}	

}
