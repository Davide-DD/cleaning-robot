package it.unibo.ppcr.components;

public class Spot {
	private boolean visited;
	private boolean once;
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public boolean isOnce() {
		return once;
	}
	public void setOnce(boolean once) {
		this.once = once;
	}
	public Spot(boolean visited, boolean once) {
		super();
		this.visited = visited;
		this.once = once;
	}
	
	@Override
	public String toString()
	{
		return "Visited: "+visited+", Once: "+once;
	}
	
	

}
