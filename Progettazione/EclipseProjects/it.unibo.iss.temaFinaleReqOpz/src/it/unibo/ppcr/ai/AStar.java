package it.unibo.ppcr.ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import it.unibo.ppcr.components.AStarNode;
import it.unibo.ppcr.components.Node;

public class AStar {
	private int hvCost = 10;
	private boolean[][] obstaclesMap;
	private AStarNode[][] searchArea;
	private PriorityQueue<AStarNode> openList;
	private List<AStarNode> closedList;
	private AStarNode initialNode;
	private AStarNode finalNode;

	public AStar(Node initialNode, Node finalNode, boolean[][] obstaclesMap) {
		setInitialNode(initialNode);
		setFinalNode(finalNode);
		this.obstaclesMap = obstaclesMap;
		this.searchArea = new AStarNode[obstaclesMap.length][obstaclesMap[0].length];
		this.openList = new PriorityQueue<AStarNode>(new Comparator<AStarNode>() {
			@Override
			public int compare(AStarNode node0, AStarNode node1) {
				return node0.getF() < node1.getF() ? -1 : node0.getF() > node1.getF() ? 1 : 0;
			}
		});
		setNodes();
		this.closedList = new ArrayList<AStarNode>();
	}

	private void setNodes() {
		for (int i = 0; i < searchArea.length; i++) {
			for (int j = 0; j < searchArea[0].length; j++) {
				AStarNode node = new AStarNode(i, j);
				node.calculateHeuristic(getFinalNode());
				this.searchArea[i][j] = node;
			}
		}
	}

	public List<Node> findPath() {
		openList.add(initialNode);
		while (!isEmpty(openList)) {
			AStarNode currentNode = openList.poll();
			closedList.add(currentNode);
			if (isFinalNode(currentNode)) {
				return getPath(currentNode);
			} else {
				addAdjacentNodes(currentNode);
			}
		}
		return new ArrayList<Node>();
	}

	private List<Node> getPath(AStarNode currentNode) {
		List<Node> path = new ArrayList<Node>();
		path.add(currentNode);
		AStarNode parent;
		while ((parent = currentNode.getParent()) != null) {
			path.add(0, parent);
			currentNode = parent;
		}
		return path;
	}

	private void addAdjacentNodes(AStarNode currentNode) {
		addAdjacentUpperRow(currentNode);
		addAdjacentMiddleRow(currentNode);
		addAdjacentLowerRow(currentNode);
	}

	private void addAdjacentLowerRow(AStarNode currentNode) {
		int row = currentNode.getX();
		int col = currentNode.getY();
		int lowerRow = row + 1;
		if (lowerRow < getSearchArea().length) {
			checkNode(currentNode, col, lowerRow, getHvCost());
		}
	}

	private void addAdjacentMiddleRow(AStarNode currentNode) {
		int row = currentNode.getX();
		int col = currentNode.getY();
		int middleRow = row;
		if (col - 1 >= 0) {
			checkNode(currentNode, col - 1, middleRow, getHvCost());
		}
		if (col + 1 < getSearchArea()[0].length) {
			checkNode(currentNode, col + 1, middleRow, getHvCost());
		}
	}

	private void addAdjacentUpperRow(AStarNode currentNode) {
		int row = currentNode.getX();
		int col = currentNode.getY();
		int upperRow = row - 1;
		if (upperRow >= 0) {
			checkNode(currentNode, col, upperRow, getHvCost());
		}
	}

	private void checkNode(AStarNode currentNode, int col, int row, int cost) {
		AStarNode adjacentNode = getSearchArea()[row][col];
		if (!isBlock(adjacentNode) && !getClosedList().contains(adjacentNode)) {
			if (!getOpenList().contains(adjacentNode)) {
				adjacentNode.setNodeData(currentNode, cost);
				getOpenList().add(adjacentNode);
			} else {
				boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
				if (changed) {
					// Remove and Add the changed node, so that the PriorityQueue can sort again its
					// contents with the modified "finalCost" value of the modified node
					getOpenList().remove(adjacentNode);
					getOpenList().add(adjacentNode);
				}
			}
		}
	}
	
	private boolean isBlock(AStarNode node)
	{
		return obstaclesMap[node.getX()][node.getY()];
	}

	private boolean isFinalNode(AStarNode currentNode) {
		return currentNode.equals(finalNode);
	}

	private boolean isEmpty(PriorityQueue<AStarNode> openList) {
		return openList.size() == 0;
	}

	public AStarNode getInitialNode() {
		return initialNode;
	}

	public void setInitialNode(Node initialNode) {
		this.initialNode = new AStarNode(initialNode.getX(), initialNode.getY());
	}

	public AStarNode getFinalNode() {
		return finalNode;
	}

	public void setFinalNode(Node finalNode) {
		this.finalNode = new AStarNode(finalNode.getX(), finalNode.getY());
	}

	public AStarNode[][] getSearchArea() {
		return searchArea;
	}

	public void setSearchArea(AStarNode[][] searchArea) {
		this.searchArea = searchArea;
	}

	public PriorityQueue<AStarNode> getOpenList() {
		return openList;
	}

	public void setOpenList(PriorityQueue<AStarNode> openList) {
		this.openList = openList;
	}

	public List<AStarNode> getClosedList() {
		return closedList;
	}

	public void setClosedList(List<AStarNode> closedList) {
		this.closedList = closedList;
	}

	public int getHvCost() {
		return hvCost;
	}
}
