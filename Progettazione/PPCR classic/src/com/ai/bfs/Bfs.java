package com.ai.bfs;

import java.util.ArrayList;
import java.util.List;
import com.ai.astar.AStarNode;
import com.ai.components.Node;

public class Bfs 
{	
	private boolean[][] canMoveHere; //Area da visitare definita dal no di colonne e di righe dato in ingresso al costruttore
	private boolean[][] visitedArea; //Area visitata: è true se la breadth first ha esplorato il nodo associato
	private boolean[][] checked; //Matrice di nodi controllati dalla bfs: utile per non ricontrollare gli stessi nodi (abbiamo un grafo)!
	private int rows;
	private int cols;
	
	public Bfs(int rows, int cols)
	{
		this.canMoveHere = new boolean[rows][cols];
		this.visitedArea= new boolean[rows][cols];
		this.rows=rows;
		this.cols=cols;
		
		//Inizializzo i nodi della canMoveHere
		setNodes();
	}
	
	public void setBlocks(List<int []> blocks) {
    	for (int[] block : blocks)
    		setBlock(block[0],block[1]);
    }
	
	private void setNodes() {
        for (int i = 0; i < canMoveHere.length; i++) {
            for (int j = 0; j < canMoveHere[0].length; j++) {
                this.canMoveHere[i][j] = true;
            }
        }
    }
	
	private void setBlock(int row, int col) {
        this.canMoveHere[row][col]=false;
    }
	
	public List<BfsNode> find_nearest_unvisited_node(Node currentNode, int currentDirection) 
	{
		List<BfsNode> pathNodes;
		this.checked= new boolean[rows][cols];
		List<BfsNode> queue = new ArrayList<BfsNode>(); //Coda dei nodi da visitare
		
		boolean finished;		
		
		//Aggiungo il nodo alla coda
		queue.add(new BfsNode(currentNode.getRow(), currentNode.getCol(), -1, null));
		
		
		while(!queue.isEmpty())
		{
			BfsNode currentBFSNode = queue.remove(0); //Tolgo il primo nodo. Se non ha una direzione, inserisco la direzione attuale
			if(currentBFSNode.getDirection()!=-1)
				currentDirection=currentBFSNode.getDirection();
			finished=nodeUnvisited(currentBFSNode); //Se il nodo considerato non è visitato, ho trovato il nodo da visitare!
			if(finished)
			{
				//Dico che ho visitato il nodo e restituisco il path per arrivare a questo nodo
				visitedArea[currentBFSNode.getRow()][currentBFSNode.getCol()]=true;
				
				pathNodes= new ArrayList<BfsNode>();
				while(currentBFSNode.getParent()!=null) //Il get parent restituisce il genitore del nodo attuale
				{
					pathNodes.add(0,currentBFSNode);
					currentBFSNode=currentBFSNode.getParent();
				}
				return pathNodes;			
			}
			//Se non ho finito, controllo i nodi adiacenti e ne scelgo uno
			for(BfsNode node: adjacentNodes(currentBFSNode, currentDirection))
			{
				//Il nodo adiacente trovato potrebbe essere non corretto ovvero:			
				if(node.getRow()<this.rows && node.getCol()<this.cols && node.getRow()>=0 && node.getCol()>=0) //a) trovarsi fuori dalla matrice
				{
					if(!checked[node.getRow()][node.getCol()] && adjacentMovable(node)) ////b) coincidere con un ostacolo (in checked tengo nota dei nodi già controllati)
					{
						//Se il nodo adiacente trovato va bene, lo inserisco in quelli controllati e lo aggiungo alla lista
						checked[node.getRow()][node.getCol()]=true;
						queue.add(node);
					}
				}									
			}
		}
		return null;
	}
	
	public void setVisitedNode(Node node)
	{
		this.visitedArea[node.getRow()][node.getCol()]=true;
	}
	
	public List<BfsNode> adjacentNodes(BfsNode currentNode, int direction)
	{
		List<BfsNode> adjacentNodes = new ArrayList<BfsNode>();
		//Movimento a spirale: ottimizzazione dell'algoritmo del 10-15% (commentare lo switch per attivare algoritmo "normale")
		switch(direction) {
		case 0:
			adjacentNodes.add(new BfsNode(currentNode.getRow(),currentNode.getCol()-1,1,currentNode));
			adjacentNodes.add(new BfsNode(currentNode.getRow()+1,currentNode.getCol(),0,currentNode));
			adjacentNodes.add(new BfsNode(currentNode.getRow(),currentNode.getCol()+1,3,currentNode));
			adjacentNodes.add(new BfsNode(currentNode.getRow()-1,currentNode.getCol(),2,currentNode));
			break;
		case 1:
			adjacentNodes.add(new BfsNode(currentNode.getRow()-1,currentNode.getCol(),2,currentNode));
			adjacentNodes.add(new BfsNode(currentNode.getRow(),currentNode.getCol()-1,1,currentNode));
			adjacentNodes.add(new BfsNode(currentNode.getRow()+1,currentNode.getCol(),0,currentNode));
			adjacentNodes.add(new BfsNode(currentNode.getRow(),currentNode.getCol()+1,3,currentNode));			
			break;
		case 2:
			adjacentNodes.add(new BfsNode(currentNode.getRow(),currentNode.getCol()+1,3,currentNode));
			adjacentNodes.add(new BfsNode(currentNode.getRow()-1,currentNode.getCol(),2,currentNode));
			adjacentNodes.add(new BfsNode(currentNode.getRow(),currentNode.getCol()-1,1,currentNode));
			adjacentNodes.add(new BfsNode(currentNode.getRow()+1,currentNode.getCol(),0,currentNode));
			break;		
		case 3:
			adjacentNodes.add(new BfsNode(currentNode.getRow()+1,currentNode.getCol(),0,currentNode));
			adjacentNodes.add(new BfsNode(currentNode.getRow(),currentNode.getCol()+1,3,currentNode));
			adjacentNodes.add(new BfsNode(currentNode.getRow()-1,currentNode.getCol(),2,currentNode));
			adjacentNodes.add(new BfsNode(currentNode.getRow(),currentNode.getCol()-1,0,currentNode));
			break;
		}		
		/*//Movimento normale	e case 3 del movimento a spirale
		adjacentNodes.add(new BfsNode(currentNode.getRow()+1,currentNode.getCol(),0,currentNode));
		adjacentNodes.add(new BfsNode(currentNode.getRow(),currentNode.getCol()+1,3,currentNode));
		adjacentNodes.add(new BfsNode(currentNode.getRow()-1,currentNode.getCol(),2,currentNode));
		adjacentNodes.add(new BfsNode(currentNode.getRow(),currentNode.getCol()-1,0,currentNode));*/
		return adjacentNodes;	
	}
	
	public boolean nodeUnvisited(AStarNode node)
	{
		return !visitedArea[node.getRow()][node.getCol()];
	}
	
	public boolean adjacentMovable(AStarNode node)
	{
		return canMoveHere[node.getRow()][node.getCol()];
	}	
	

}

						