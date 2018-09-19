package it.unibo.ppcr.components;

public class Node {

    protected int x;
    protected int y;

    public Node(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public String toString()
    {
		return "Node [row="+x+", col="+y+"]";
    	
    }
    
    @Override
    public boolean equals(Object o)
    {
    	if (o == this) {
            return true;
        }    	
    	if (!(o instanceof Node)) {
            return false;
        }    	
    	Node n = (Node) o;
        return x==n.getX() && y==n.getY();
    }
}
