package it.unibo.ppcr.gui;
import java.awt.Dimension;
import javax.swing.JPanel;

public class Cell extends JPanel 
{  	
	private static final long serialVersionUID = -8797755026300687959L;

	@Override
    public Dimension getPreferredSize() {
       return new Dimension(25, 25);
    }
}