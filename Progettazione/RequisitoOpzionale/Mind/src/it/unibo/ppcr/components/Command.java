package it.unibo.ppcr.components;

public class Command 
{
	private String action;
	private String direction;
	
	public Command(String action, String direction) {
		super();
		this.action = action;
		this.direction = direction;
	}
	
	public String getDirection() {
		return direction;
	}	
	
	public String getAction() {
		return action;
	}
	
	@Override
	public String toString()
	{
		return action + " ("+direction+")";
	}

}
