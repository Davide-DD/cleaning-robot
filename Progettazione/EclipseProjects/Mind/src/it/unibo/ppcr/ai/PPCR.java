package it.unibo.ppcr.ai;

import java.util.ArrayList;
import java.util.List;

import it.unibo.ppcr.components.BFSNode;
import it.unibo.ppcr.components.Command;
import it.unibo.ppcr.components.Node;
import it.unibo.ppcr.gui.Grid;

public class PPCR {
	private static int rows;
	private static int cols;
	private static Node currentNode;
	private static Node initialNode;
	private static Node finalNode;
	private static int currentDirection; // direzione del robot: 0=giu, 1=sinistra, 2=su, 3=destra
	private static int timeToPause;

	private static int timesToCheck;
	private static int timesChecked;

	private static List<BFSNode> partialPath;
	private static Node savedNode;
	private static boolean firstPart;
	private static boolean needToWait;
	private static int clearSequenceReceived;

	private static BFS bfs;
	private static Node obstacleNode;
	private static List<Node> toFinalPosition;
	private static Grid grid;

	public static void init() {
		rows = 10;
		cols = rows + 1;
		currentDirection = 2;
		currentNode = new Node(0, 0);
		initialNode = currentNode;
		finalNode = new Node(rows - 1, cols - 1);

		bfs = new BFS(rows, cols);
		bfs.setVisited(initialNode);
		partialPath = new ArrayList<BFSNode>();

		timeToPause = 600;
		timesToCheck = 0;
		timesChecked = 0;
		needToWait = false;
		clearSequenceReceived = 0;
		firstPart = true;
		grid = new Grid(rows, cols);
	}

	public static void initForTests(int nrows, int ncols) {
		rows = nrows;
		cols = ncols;
		currentDirection = 0;
		currentNode = new Node(0, 0);
		initialNode = currentNode;
		finalNode = new Node(rows - 1, cols - 1);

		bfs = new BFS(rows, cols);
		bfs.setVisited(initialNode);
		partialPath = new ArrayList<BFSNode>();

		timeToPause = 1000;
		timesToCheck = 1;
		timesChecked = 0;
		needToWait = false;
		clearSequenceReceived = 0;
		firstPart = true;
		grid = new Grid(rows, cols);
	}

	private static Command moveBackward(Node currentNode, Node previousNode) {
		// Si muove in lunghezza (da colonna a colonna)
		if (currentNode.getX() == previousNode.getX())
			return new Command("s", "width");
		// Si muove in altezza (da riga a riga)
		else
			return new Command("s", "height");
	}

	private static Command moveForward(Node currentNode, Node previousNode) {
		// Si muove in lunghezza (da colonna a colonna)
		if (currentNode.getX() == previousNode.getX())
			return new Command("w", "width");
		// Si muove in altezza (da riga a riga)
		else
			return new Command("w", "height");
	}

	private static Command getCommand(Node currentNode, Node nextNode, int startingDirection) {
		// Movimento verso destra
		// direzione del robot: 0=giu, 1=sinistra, 2=su, 3=destra
		if (nextNode.getY() > currentNode.getY()) {
			switch (startingDirection) {
			case 3:
				return new Command("w", "width");
			case 1:
				currentDirection = 2;
				return new Command("d", "width");
			case 2:
				currentDirection = 3;
				return new Command("d", "width");
			case 0:
				currentDirection = 3;
				return new Command("a", "width");
			}
		}
		// Movimento verso sinistra
		// direzione del robot: 0=giu, 1=sinistra, 2=su, 3=destra
		if (nextNode.getY() < currentNode.getY()) {
			switch (startingDirection) {
			case 3:
				currentDirection = 2;
				return new Command("a", "width");
			case 1:
				return new Command("w", "width");
			case 2:
				currentDirection = 1;
				return new Command("a", "width");
			case 0:
				currentDirection = 1;
				return new Command("d", "width");
			}
		}
		// Movimento verso il basso
		// direzione del robot: 0=giu, 1=sinistra, 2=su, 3=destra
		if (nextNode.getX() > currentNode.getX()) {
			switch (startingDirection) {
			case 3:
				currentDirection = 0;
				return new Command("d", "height");
			case 1:
				currentDirection = 0;
				return new Command("a", "height");
			case 2:
				currentDirection = 1;
				return new Command("a", "height");
			case 0:
				return new Command("w", "height");
			}
		}
		// Movimento verso l'alto 
		// direzione del robot: 0=giu, 1=sinistra, 2=su, 3=destra
		if (nextNode.getX() < currentNode.getX()) {
			switch (startingDirection) {
			case 3:
				currentDirection = 2;
				return new Command("a", "height");
			case 1:
				currentDirection = 2;
				return new Command("d", "height");
			case 2:
				return new Command("w", "height");
			case 0:
				currentDirection = 1;
				return new Command("d", "height");
			}
		}
		return null;
	}

	public static String getNextMove(String found) {

		Command command;
		System.out.println("\nI am in " + currentNode.getX() + "," + currentNode.getY());
		grid.setCleaned(currentNode.getX(), currentNode.getY());
		switch (found) {
		case "obstacle":
			if (firstPart) {

				clearSequenceReceived = 0;

				// Elimina il path parziale trovato (è sbagliato visto che un pezzo di strada è
				// bloccato)
				partialPath = new ArrayList<BFSNode>();
				// Se c'è un ostacolo nel primo spazio dove viene messe il robot: errore!
				if (currentNode.equals(initialNode)) {
					System.out.println("ERROR: there can't be an obstacole where the robot is positioned at first!");
					return "error";
				}

				// Come valuto se ostacolo è fisso o mobile? La prima volta che ci vado a
				// sbattere, torno indietro e aspetto un tempo arbitrario.
				// Poi ritorno sulla posizione dell'ostacolo. Se non c'è niente significa che
				// era un ostacolo mobile e che quindi ora si è spostato
				// e ho pulito lo spazio lasciato da esso. Altrimenti è un ostacolo fisso e devo
				// marcarlo come tale.
				// E' possibile anche dire al robot di controllare andando avanti e indietro
				// verso l'ostacolo più di una volta. Cambiare il parametro timesToCheck di
				// conseguenza!

				if (timesChecked < timesToCheck) {
					timesChecked++;
					// Al prossimo clear (quello che il robo invierà dopo essere tornato nella
					// posizione precedente), devo aspettare pauseTime secondi per tornare nella
					// posizione dove è stato rilevato l'ostacolo.
					System.out.println("Need to check if " + currentNode.getX() + "," + currentNode.getY()
							+ " is a moving obstacle!");
					needToWait = true;
				} else {
					// Ho controllato timesToCheck volte e l'ostacolo è ancora là, ovvero si tratta
					// di un ostacolo fisso!
					// Segna la posizione attuale come ostacolo (fisso)
					System.out.println("Marked " + currentNode.getX() + "," + currentNode.getY() + " as obstacle.");
					bfs.setObstacle(currentNode);
					grid.setObstacle(currentNode.getX(), currentNode.getY());
					// Al prossimo clear ricevuto non aspetto più
					needToWait = false;
					// Resetto il contatore per il prossimo ostacolo che incontrerò
					timesChecked = 0;
				}
				// In ogni caso, muoviti all'indietro per tornare nella posizione salva
				command = moveBackward(currentNode, savedNode);
				// Riparte l'algoritmo: current node diventa savedPosition
				// System.out.println("Saved node: " + savedNode);
				// Segno la posizione attuale come posizione in cui c'è un ostacolo e dico che
				// il nodo corrente è ora il nodo salvato (ovvero senza ostacoli).
				obstacleNode = currentNode;
				currentNode = savedNode;
				return translateTime(command);
			} else {
				// Seconda parte: astar
				new ArrayList<Command>();
				// Devo tornare indietro
				command = moveBackward(currentNode, savedNode);

				System.out.println(
						currentNode.getX() + "," + currentNode.getY() + " is a moving obstacle! Moving backward...");
				obstacleNode = currentNode;
				currentNode = savedNode;
				needToWait = true;
				clearSequenceReceived = 0;
				return (translateTime(command));

			}
		case "clear":
			if (firstPart) {
				// Clear ricevuti in sequenza
				clearSequenceReceived++;
				// System.out.println(partialPath);
				// Mi salvo la posizione attuale: è libera (ha detto il robot).
				// Me la salvo in modo che se con il prossimo comando il robot troverà un
				// ostacolo, posso dirgli di tornare indietro.
				savedNode = currentNode;
				// System.out.println("Saved node: " + savedNode.getX() + "," +
				// savedNode.getY());

				// Se sto controllando un ostacolo a mia arrivano due clear in sequenza
				// significa che un eventuale ostacolo prima presente, ora si è scansato!
				if (timesChecked != 0 && clearSequenceReceived > 1) {
					// Al prossimo clear ricevuto non aspetto più
					needToWait = false;
					// Resetto il contatore per il prossimo ostacolo che incontrerò
					timesChecked = 0;
				}
				// Se needToWait è true significa che ho appena incontrato un ostacolo e ora
				// devo aspettare timeToPause secondi prima di tornare avanti e controllare di
				// nuovo!
				if (needToWait) {
					System.out.println("Waiting " + timeToPause + "ms before checking again!");
					try {
						Thread.sleep(timeToPause);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Done! Checking now!");
					// Devo andare avanti
					command = moveForward(currentNode, obstacleNode);
					System.out
							.println("Node to reach: {x: " + obstacleNode.getX() + ", y: " + obstacleNode.getY() + "}");
					currentNode = obstacleNode;
					return translateTime(command);

				} else {
					// Tutto regolare! Vado avanti normalmente: no ostacoli!

					if (partialPath.size() == 0) { // Il percorso parziale trovato da bfs non ha più nodi?
						// Devo far rigirare bfs
						partialPath = bfs.findNearestUnvisitedNode(currentNode, currentDirection);
						// Se bfs restituisce null, significa che ha "terminato". Poi sarà verificato
						// "come" (e cioè se è stato trovato un ostacolo invalicabile o meno)!
						if (partialPath == null) {
							firstPart = false;
							System.out.println("First part (BFS): terminated!");

							// Passo intermedio:
							// 1) Occorre trovare inizialemnte eventuali spazi lasciati non puliti ma che
							// non possono essere raggiunti (all'interno di perimetri delimitati dal robot)
							for (Node newObs : bfs.findUnreacheableSpots())
								grid.setObstacle(newObs.getX(), newObs.getY());
							// 2) Check ostacoli invalicabili
							if (bfs.impassableObstacle()) {
								System.out.println("Found an impassable obstacle! Stopping..");
								grid.impassableObstacle();
								return "impassableObstacle";
							}

							// Trovo il path per arrivare al nodo finale (considerando gli ostacoli fissi)
							AStar astar = new AStar(currentNode, finalNode, bfs.getObstaclesMap());
							toFinalPosition = astar.findPath();
							if(toFinalPosition.size()==0)
							{
								System.out.println("Could not find a path to reach final position! Stopping..");
								grid.obstructedFinalPosition();
								return "obstructedFinalPosition";								
							}
								
							toFinalPosition.remove(0);
							return "secondPart, 0";
						}
					}
					// Prendo il primo nodo della lista del percorso parziale trovato
					// e calcolo quali sono i comandi da dare:
					command = getCommand(currentNode, partialPath.get(0), currentDirection);

					System.out.println("Node to reach: {x: " + partialPath.get(0).getX() + ", y: "
							+ partialPath.get(0).getY() + "}");
					if (command.getAction() == "w") {
						currentNode = partialPath.remove(0);
					}
					// Restituisco il primo comando
					return translateTime(command);
				}

			} else {
				// Seconda parte: astar
				savedNode = currentNode;
				clearSequenceReceived++;
				if (clearSequenceReceived > 1) {
					needToWait = false;
				}
				if (needToWait) {
					System.out.println("Waiting " + timeToPause + "ms before checking again!");
					try {
						Thread.sleep(timeToPause);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Done! Checking now!");
					// Devo andare avanti
					command = moveForward(currentNode, obstacleNode);
					System.out
							.println("Node to reach: {x: " + obstacleNode.getX() + ", y: " + obstacleNode.getY() + "}");
					currentNode = obstacleNode;
					return translateTime(command);
				} else {
					if (toFinalPosition.size() == 0) {
						System.out.println("Second Part (AStar): terminated!");
						grid.finished();
						return "finished";
					} else {
						command = getCommand(currentNode, toFinalPosition.get(0), currentDirection);
						System.out.println("Node to reach: {x: " + toFinalPosition.get(0).getX() + ", y: "
								+ toFinalPosition.get(0).getY() + "}");
						if (command.getAction() == "w") {
							currentNode = toFinalPosition.remove(0);
						}
						// Restituisco il comando
						return translateTime(command);
					}

				}

			}
		case "reset":
			System.out.println("\nResetting... ");
			init();
			return "resetCompleted";
		default:
			System.out.println("Received an unidentified keyword: " + found);
			return "error";
		}
	}

	private static String translateTime(Command command) {
		int colTime = 165 * 9 / cols;
		int rowTime = 165 * 8 / rows;

		int time = command.getDirection().equals("height") ? rowTime : colTime;
		return command.getAction() + ", " + time;
	}
}
