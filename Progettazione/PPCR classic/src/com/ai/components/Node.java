package com.ai.components;

public class Node {

	protected int row;
	protected int col;
	protected boolean isBlock;

	public Node(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	public boolean isBlock() {
		return isBlock;
	}

	public void setBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	@Override
	public String toString() {
		return "Node [row=" + row + ", col=" + col + "]";

	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			Node n = (Node) obj;
			return row == n.getRow() && col == n.getCol() && isBlock == n.isBlock();
		}
		return false;
	}
}
