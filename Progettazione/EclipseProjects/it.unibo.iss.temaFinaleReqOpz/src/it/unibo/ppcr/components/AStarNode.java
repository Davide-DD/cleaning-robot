package it.unibo.ppcr.components;

public class AStarNode extends Node {   

	private int g;
    private int f;
    private int h;
    private AStarNode parent;
    
    public AStarNode(int row, int col) {
		super(row, col);
	}


    public void calculateHeuristic(AStarNode finalNode) {
        this.h = Math.abs(finalNode.getX() - getX()) + Math.abs(finalNode.getY() - getY());
    }

    public void setNodeData(AStarNode currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        setParent(currentNode);
        setG(gCost);
        calculateFinalCost();
    }

    public boolean checkBetterPath(AStarNode currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        if (gCost < getG()) {
            setNodeData(currentNode, cost);
            return true;
        }
        return false;
    }

    private void calculateFinalCost() {
        int finalCost = getG() + getH();
        setF(finalCost);
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public AStarNode getParent() {
        return parent;
    }

    public void setParent(AStarNode parent) {
        this.parent = parent;
    }
}
