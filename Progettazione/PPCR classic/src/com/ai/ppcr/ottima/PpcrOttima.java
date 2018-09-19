package com.ai.ppcr.ottima;

import java.util.ArrayList;
import java.util.List;

import com.ai.astar.AStar;
import com.ai.astar.AStarNode;
import com.ai.astar.AStarPath;
import com.ai.components.Node;
import com.ai.components.Path;

public class PpcrOttima {

	private AStarNode finalNode;
	// private int currentDirection; // direzione: 0=giu, 1=sinistra, 2=su, 3=destra
	private int rows;
	private int cols;

	private AStarPath bestPath;
	private AStarPath currentPath;
	private List<AStarNode> unexploredSpots;
	private AStarNode currentNode;

	private boolean[][] blocks;
	private int cleaneableSpots;

	public PpcrOttima(int rows, int cols, AStarNode initialNode, AStarNode finalNode) {
		// PPCR sta per path planning of coverage region
		this.rows = rows;
		this.cols = cols;
		this.finalNode = finalNode;
		// this.currentDirection = 0;

		this.bestPath = new AStarPath(new ArrayList<AStarNode>());
		this.currentPath = new AStarPath(new ArrayList<AStarNode>());
		this.unexploredSpots = new ArrayList<AStarNode>();

		this.currentNode = initialNode;
		this.currentNode.setFather(null);

		blocks = new boolean[rows][cols];
	}

	public Path sweep() {
		if (currentPath.getPath().size() == 0)
			currentPath.getPath().add(currentNode);
		while (currentNode != null) {
			List<AStarNode> futureNodes = getFutureNodes(currentNode);
			currentNode = chooseFutureNode(futureNodes);
			currentPath.getPath().add(currentNode);
			if (currentNode.getCol() == finalNode.getCol() && currentNode.getRow() == finalNode.getRow() && currentPath.cleanedSpots() >= cleaneableSpots)
				currentNode = null;
				
		}
		bestPath = compare(currentPath, bestPath);
		//checkUnexploredSpots();
		if (bestPath.getPath().size() == cleaneableSpots && currentPath.cleanedSpots() == cleaneableSpots)
			return getPath(bestPath);
		else {
			if (unexploredSpots.size() != 0) {
				currentNode = unexploredSpots.remove(unexploredSpots.size() - 1);
				currentPath = findSubPath(currentNode);
				sweep();
			} else
				return getPath(bestPath);
		}
		return getPath(bestPath);
	}

	private AStarPath compare(AStarPath currentPath, AStarPath bestPath) {
		if (currentPath.getPath().size() == 0) {
			return bestPath;
		}
		if (bestPath.getPath().size() == 0) {
			return currentPath;
		}
		if (currentPath.cleanedSpots() > bestPath.cleanedSpots()) {
			return currentPath;
		} else {
			if (currentPath.cleanedSpots() == bestPath.cleanedSpots()) {
				if (currentPath.getPath().size() > bestPath.getPath().size()) {
					return bestPath;
				} else
					return currentPath;
			} else
				return bestPath;
		}
	}

	private List<AStarNode> getFutureNodes(AStarNode currentNode) {
		List<AStarNode> res = new ArrayList<AStarNode>();
		Node father = currentNode.getFather();

		if (father == null) {
			// Nodo sotto
			if (currentNode.getRow() + 1 != rows)
				if (!blocks[currentNode.getRow() + 1][currentNode.getCol()]) {
					AStarNode down = new AStarNode(currentNode.getRow() + 1, currentNode.getCol());
					down.setFather(currentNode);
					res.add(down);
				}
			// Nodo destro
			if (currentNode.getCol() + 1 != cols)
				if (!blocks[currentNode.getRow()][currentNode.getCol() + 1]) {
					AStarNode right = new AStarNode(currentNode.getRow(), currentNode.getCol() + 1);
					right.setFather(currentNode);
					res.add(right);
				}

			// Nodo sopra
			if (currentNode.getRow() - 1 >= 0)
				if (!blocks[currentNode.getRow() - 1][currentNode.getCol()]) {
					AStarNode up = new AStarNode(currentNode.getRow() - 1, currentNode.getCol());
					up.setFather(currentNode);
					res.add(up);
				}

			// Nodo sinistro
			if (currentNode.getCol() - 1 >= 0)
				if (!blocks[currentNode.getRow()][currentNode.getCol() - 1]) {
					AStarNode left = new AStarNode(currentNode.getRow(), currentNode.getCol() - 1);
					left.setFather(currentNode);
					res.add(left);
				}

		} else {

			if (father.getRow() == currentNode.getRow() && father.getCol() < currentNode.getCol()) // padre è a sinistra
																									// del
																									// figlio
			{
				// Nodo destro
				if (currentNode.getCol() + 1 != cols)
					if (!blocks[currentNode.getRow()][currentNode.getCol() + 1]) {
						AStarNode right = new AStarNode(currentNode.getRow(), currentNode.getCol() + 1);
						right.setFather(currentNode);
						res.add(right);
					}
				// Nodo sotto
				if (currentNode.getRow() + 1 != rows)
					if (!blocks[currentNode.getRow() + 1][currentNode.getCol()]) {
						AStarNode down = new AStarNode(currentNode.getRow() + 1, currentNode.getCol());
						down.setFather(currentNode);
						res.add(down);
					}
				// Nodo sopra
				if (currentNode.getRow() - 1 >= 0)
					if (!blocks[currentNode.getRow() - 1][currentNode.getCol()]) {
						AStarNode up = new AStarNode(currentNode.getRow() - 1, currentNode.getCol());
						up.setFather(currentNode);
						res.add(up);
					}

				// Nodo sinistro
				AStarNode left = new AStarNode(currentNode.getRow(), currentNode.getCol() - 1);
				left.setFather(currentNode);
				res.add(left);

			}
			if (father.getRow() == currentNode.getRow() && father.getCol() > currentNode.getCol()) // padre è a destra
																									// del
																									// figlio
			{
				// Nodo sinistro
				if (currentNode.getCol() - 1 >= 0)
					if (!blocks[currentNode.getRow()][currentNode.getCol() - 1]) {
						AStarNode left = new AStarNode(currentNode.getRow(), currentNode.getCol() - 1);
						left.setFather(currentNode);
						res.add(left);
					}
				// Nodo sopra
				if (currentNode.getRow() - 1 >= 0)
					if (!blocks[currentNode.getRow() - 1][currentNode.getCol()]) {
						AStarNode up = new AStarNode(currentNode.getRow() - 1, currentNode.getCol());
						up.setFather(currentNode);
						res.add(up);
					}
				// Nodo sotto
				if (currentNode.getRow() + 1 != rows)
					if (!blocks[currentNode.getRow() + 1][currentNode.getCol()]) {
						AStarNode down = new AStarNode(currentNode.getRow() + 1, currentNode.getCol());
						down.setFather(currentNode);
						res.add(down);
					}

				// Nodo destro
				AStarNode right = new AStarNode(currentNode.getRow(), currentNode.getCol() + 1);
				right.setFather(currentNode);
				res.add(right);

			}
			if (father.getRow() < currentNode.getRow() && father.getCol() == currentNode.getCol()) // padre è a sopra al
																									// figlio
			{
				// Nodo sotto
				if (currentNode.getRow() + 1 != rows)
					if (!blocks[currentNode.getRow() + 1][currentNode.getCol()]) {
						AStarNode down = new AStarNode(currentNode.getRow() + 1, currentNode.getCol());
						down.setFather(currentNode);
						res.add(down);
					}
				// Nodo sinistro
				if (currentNode.getCol() - 1 >= 0)
					if (!blocks[currentNode.getRow()][currentNode.getCol() - 1]) {
						AStarNode left = new AStarNode(currentNode.getRow(), currentNode.getCol() - 1);
						left.setFather(currentNode);
						res.add(left);
					}
				// Nodo destro
				if (currentNode.getCol() + 1 != cols)
					if (!blocks[currentNode.getRow()][currentNode.getCol() + 1]) {
						AStarNode right = new AStarNode(currentNode.getRow(), currentNode.getCol() + 1);
						right.setFather(currentNode);
						res.add(right);
					}

				// Nodo sopra
				AStarNode up = new AStarNode(currentNode.getRow() - 1, currentNode.getCol());
				up.setFather(currentNode);
				res.add(up);

			}
			if (father.getRow() > currentNode.getRow() && father.getCol() == currentNode.getCol()) // padre è a sotto al
																									// figlio
			{
				// Nodo sopra
				if (currentNode.getRow() - 1 >= 0)
					if (!blocks[currentNode.getRow() - 1][currentNode.getCol()]) {
						AStarNode up = new AStarNode(currentNode.getRow() - 1, currentNode.getCol());
						up.setFather(currentNode);
						res.add(up);
					}

				// Nodo destro
				if (currentNode.getCol() + 1 != cols)
					if (!blocks[currentNode.getRow()][currentNode.getCol() + 1]) {
						AStarNode right = new AStarNode(currentNode.getRow(), currentNode.getCol() + 1);
						right.setFather(currentNode);
						res.add(right);
					}
				// Nodo sinistro
				if (currentNode.getCol() - 1 >= 0)
					if (!blocks[currentNode.getRow()][currentNode.getCol() - 1]) {
						AStarNode left = new AStarNode(currentNode.getRow(), currentNode.getCol() - 1);
						left.setFather(currentNode);
						res.add(left);
					}

				// Nodo sotto
				AStarNode down = new AStarNode(currentNode.getRow() + 1, currentNode.getCol());
				down.setFather(currentNode);
				res.add(down);

			}
		}

		return res;
	}

	private AStarPath findSubPath(AStarNode currentNode) {
		unexploredSpots = new ArrayList<AStarNode>();
		AStarPath res = currentPath;
		for (int i = res.getPath().size()-1; i>=0; i--) {
			AStarNode node = res.getPath().get(i);
			if (node.getCol() == currentNode.getFather().getCol()
					&& node.getRow() == currentNode.getFather().getRow()) {
				res.getPath().add(currentNode);
				return res;
			}
			else 
			{
				res.getPath().remove(i);
				unexploredSpots.add(node);
			}
				
		}
		return res;
	}

	private AStarNode chooseFutureNode(List<AStarNode> futureNodes) {
		for (AStarNode node : futureNodes)
			if (!unexploredSpots.contains(node) && !currentPath.containsNode(node))
				unexploredSpots.add(node);

		AStarNode best = null;
		for (AStarNode node : futureNodes) {
			if (!loop(node, futureNodes.size() == 1)) {

				if (best == null) {
					best = node;
				} else {
					if (currentPath.containsNode(node) && currentPath.containsNode(best)) {
						// La distanza dal goal è calcolata con A* utilizzando la distanza di Manhattan
						// che tiene conto degli ostacoli
						if (distanceFromGoal(node, finalNode) < distanceFromGoal(best, finalNode)) {
							best = node;
						}
					} else {
						if (!currentPath.containsNode(node) && currentPath.containsNode(best))
							best = node;
						else {
							if (!currentPath.containsNode(node) && !currentPath.containsNode(best))
								if (distanceFromGoal(node, finalNode) > distanceFromGoal(best, finalNode))
									best = node;
						}

					}
				}
			}
		}

		unexploredSpots.remove(best);
		return best;
	}

	//per limitare i loop
	private boolean loop(AStarNode node, boolean unique) {
		if (unique && node.getFather()==null)
			return false;
		else {
			int count = 0;
			for (AStarNode n : currentPath.getPath()) {
				if (n.getCol() == node.getCol() && n.getRow()==node.getRow())
					if(node.getFather()==null || n.getFather()==null)
						return false;
					else
						if(n.getFather().getCol() == node.getFather().getCol() && n.getFather().getRow() == node.getFather().getRow())
							count++;
			}
			if (count > 2)
				return true;
			else
				return false;
		}
	}

	public int distanceFromGoal(AStarNode initialNode, AStarNode finalNode) {
		AStar astar = new AStar(rows, cols, initialNode, finalNode);
		List<Node> path = astar.findPath();
		return path.size();
	}

	public void setBlocks(List<int[]> blocks) {
		int count = 0;
		for (int[] block : blocks) {
			this.blocks[block[0]][block[1]] = true;
			count++;
		}
		cleaneableSpots = (rows * cols) - count;
	}

	private Path getPath(AStarPath path) {
		List<Node> res = new ArrayList<Node>();
		for (AStarNode node : path.getPath())
			res.add(node);
		return new Path(res);
	}
}
