package it.unibo.ppcr.ai;

import java.util.ArrayList;
import java.util.List;

import it.unibo.ppcr.components.BFSNode;
import it.unibo.ppcr.components.Node;
import it.unibo.ppcr.components.Spot;

public class BFS {
	private boolean[][] obstaclesMap; // Area da visitare definita dal no di colonne e di righe dato in ingresso al
										// costruttore
	private Spot[][] visitedArea; // Area visitata: è true se la breadth first ha esplorato il nodo associato
	private boolean[][] checked; // Matrice di nodi controllati dalla bfs: utile per non ricontrollare gli stessi
									// nodi (abbiamo un grafo)!
	private int rows;
	private int cols;

	private int nObstacles = 0;
	private int nSpotsCleaned = 1;

	public BFS(int rows, int cols) {
		this.obstaclesMap = new boolean[rows][cols];
		this.visitedArea = new Spot[rows][cols];
		this.rows = rows;
		this.cols = cols;

		for (int i = 0; i < visitedArea.length; i++) {
			for (int j = 0; j < visitedArea[0].length; j++) {
				this.visitedArea[i][j] = new Spot(false, false);
			}
		}
	}

	public void printMaps() {
		for (int i = 0; i < visitedArea.length; i++) {
			for (int j = 0; j < visitedArea[0].length; j++) {
				System.out.println("[" + i + "," + j + "]: " + this.visitedArea[i][j]);
			}
		}
		for (int i = 0; i < obstaclesMap.length; i++) {
			for (int j = 0; j < obstaclesMap[0].length; j++) {
				System.out.println("[" + i + "," + j + "]: " + this.obstaclesMap[i][j]);
			}
		}
	}

	public boolean[][] getObstaclesMap() {
		return obstaclesMap;
	}

	public void setObstacle(Node node) {
		this.obstaclesMap[node.getX()][node.getY()] = true;
	}

	public void setVisited(Node node) {
		this.visitedArea[node.getX()][node.getY()].setVisited(true);
	}

	public List<Node> findUnreacheableSpots() {
		// Prima di tutto occorre delineare come ostacoli gli spazi al centro di
		// eventuali perimetri delineati durante l'esplorazione.
		// Ad esempio occorre che avendo un quadrato grande come ostacolo trovato dal
		// robot, esso consideri come ostacolo il perimetro, ma anche l'area!
		// Perciò per ogni spazio lasciato non pulito, alla fine di bfs occorre
		// verificare che ci sia un ostacolo su almeno due direzioni (in tal caso sarà
		// segnato anch'esso come ostacolo). Se, invece, per uno spazio pulito esiste un
		// ostacolo solo su una direzione, significa che esso è
		// irraggiungibile poichè c'è un "ostacolo inevitabile" nella stanza.

		List<Node> res = new ArrayList<Node>();

		for (int i = 0; i < visitedArea.length; i++) {
			for (int j = 0; j < visitedArea[0].length; j++) {
				{
					if (!visitedArea[i][j].isVisited()) {
						int obsFound = 0;
						// Controllo ostacolo in basso:
						for (int k = i + 1; k < rows + 1; k++) {
							if(k==rows)
								break;
							if (obstaclesMap[k][j]) {
								obsFound++;
								break;
							}

						}
						// Controllo ostacolo in alto:
						for (int k = 0; k < i; k++) {
							if (obstaclesMap[k][j]) {
								obsFound++;
								break;
							}

						}
						// Controllo ostacolo a destra:
						for (int k = j + 1; k < cols + 1; k++) {
							if(k==cols)
								break;
							if (obstaclesMap[i][k]) {
								obsFound++;
								break;
							}

						}
						// Controllo ostacolo a sinistra:
						for (int k = 0; k < j; k++) {
							if (obstaclesMap[i][k]) {
								obsFound++;
								break;
							}

						}
						if (obsFound > 1) {
							obstaclesMap[i][j] = true;
							this.nObstacles++;
							res.add(new Node(i, j));
						} else
							return new ArrayList<Node>();

					}
				}

			}
		}
		return res;
	}

	public boolean impassableObstacle() {
		// System.out.println(this.rows * this.cols + "==" + this.nSpotsCleaned);
		return !(this.rows * this.cols == this.nSpotsCleaned + this.nObstacles);
	}

	public List<BFSNode> findNearestUnvisitedNode(Node currentNode, int currentDirection) {
		// printMaps();
		List<BFSNode> pathNodes;
		this.checked = new boolean[rows][cols];
		List<BFSNode> queue = new ArrayList<BFSNode>(); // Coda dei nodi da visitare

		boolean finished;

		// Aggiungo il nodo alla coda
		queue.add(new BFSNode(currentNode.getX(), currentNode.getY(), -1, null));

		while (!queue.isEmpty()) {
			BFSNode currentBFSNode = queue.remove(0); // Tolgo il primo nodo. Se non ha una direzione, inserisco la
														// direzione attuale
			if (currentBFSNode.getDirection() != -1)
				currentDirection = currentBFSNode.getDirection();
			finished = nodeUnvisited(currentBFSNode); // Se il nodo considerato non è visitato, ho trovato il nodo da
														// visitare!
			if (finished) {
				// Dico che ho visitato il nodo e restituisco il path per arrivare a questo nodo
				visitedArea[currentBFSNode.getX()][currentBFSNode.getY()].setVisited(true);
				if (!visitedArea[currentBFSNode.getX()][currentBFSNode.getY()].isOnce()) {
					visitedArea[currentBFSNode.getX()][currentBFSNode.getY()].setOnce(true);
					nSpotsCleaned++;
					// System.out.println(nSpotsCleaned);
				}
				pathNodes = new ArrayList<BFSNode>();
				while (currentBFSNode.getParent() != null) // Il get parent restituisce il genitore del nodo attuale
				{
					pathNodes.add(0, currentBFSNode);
					currentBFSNode = currentBFSNode.getParent();
				}
				return pathNodes;
			}
			// Se non ho finito, controllo i nodi adiacenti e ne scelgo uno
			for (BFSNode node : findAdjacentNodes(currentBFSNode, currentDirection)) {
				// Il nodo adiacente trovato potrebbe essere non corretto ovvero:
				if (node.getX() < this.rows && node.getY() < this.cols && node.getX() >= 0 && node.getY() >= 0) // a)
																												// trovarsi
																												// fuori
																												// dalla
																												// matrice
				{
					if (!checked[node.getX()][node.getY()] && !isObstacle(node)) //// b) coincidere con un ostacolo (in
																					//// checked tengo nota dei nodi già
																					//// controllati)
					{
						// Se il nodo adiacente trovato va bene, lo inserisco in quelli controllati e lo
						// aggiungo alla lista
						checked[node.getX()][node.getY()] = true;
						queue.add(node);
					}
				}
			}
		}
		return null;
	}

	public List<BFSNode> findAdjacentNodes(BFSNode currentNode, int direction) {
		List<BFSNode> adjacentNodes = new ArrayList<BFSNode>();
		// Movimento a spirale: ottimizzazione dell'algoritmo del 10-15% (commentare lo
		// switch per attivare algoritmo "normale")
		switch (direction) {
		case 0:
			adjacentNodes.add(new BFSNode(currentNode.getX(), currentNode.getY() - 1, 1, currentNode));
			adjacentNodes.add(new BFSNode(currentNode.getX() + 1, currentNode.getY(), 0, currentNode));
			adjacentNodes.add(new BFSNode(currentNode.getX(), currentNode.getY() + 1, 3, currentNode));
			adjacentNodes.add(new BFSNode(currentNode.getX() - 1, currentNode.getY(), 2, currentNode));
			break;
		case 1:
			adjacentNodes.add(new BFSNode(currentNode.getX() - 1, currentNode.getY(), 2, currentNode));
			adjacentNodes.add(new BFSNode(currentNode.getX(), currentNode.getY() - 1, 1, currentNode));
			adjacentNodes.add(new BFSNode(currentNode.getX() + 1, currentNode.getY(), 0, currentNode));
			adjacentNodes.add(new BFSNode(currentNode.getX(), currentNode.getY() + 1, 3, currentNode));
			break;
		case 2:
			adjacentNodes.add(new BFSNode(currentNode.getX(), currentNode.getY() + 1, 3, currentNode));
			adjacentNodes.add(new BFSNode(currentNode.getX() - 1, currentNode.getY(), 2, currentNode));
			adjacentNodes.add(new BFSNode(currentNode.getX(), currentNode.getY() - 1, 1, currentNode));
			adjacentNodes.add(new BFSNode(currentNode.getX() + 1, currentNode.getY(), 0, currentNode));
			break;
		case 3:
			adjacentNodes.add(new BFSNode(currentNode.getX() + 1, currentNode.getY(), 0, currentNode));
			adjacentNodes.add(new BFSNode(currentNode.getX(), currentNode.getY() + 1, 3, currentNode));
			adjacentNodes.add(new BFSNode(currentNode.getX() - 1, currentNode.getY(), 2, currentNode));
			adjacentNodes.add(new BFSNode(currentNode.getX(), currentNode.getY() - 1, 0, currentNode));
			break;
		}
		/*
		 * //Movimento normale e case 3 del movimento a spirale adjacentNodes.add(new
		 * BfsNode(currentNode.getX()+1,currentNode.getY(),0,currentNode));
		 * adjacentNodes.add(new
		 * BfsNode(currentNode.getX(),currentNode.getY()+1,3,currentNode));
		 * adjacentNodes.add(new
		 * BfsNode(currentNode.getX()-1,currentNode.getY(),2,currentNode));
		 * adjacentNodes.add(new
		 * BfsNode(currentNode.getX(),currentNode.getY()-1,0,currentNode));
		 */
		return adjacentNodes;
	}

	private boolean nodeUnvisited(Node node) {
		return !visitedArea[node.getX()][node.getY()].isVisited();
	}

	private boolean isObstacle(Node node) {
		return obstaclesMap[node.getX()][node.getY()];
	}
}
