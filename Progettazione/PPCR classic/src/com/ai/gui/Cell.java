package com.ai.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class Cell extends JPanel 
{  	
	private static final long serialVersionUID = -8797755026300687959L;
	
	private Color defaultBackground;
	private int row;
	private int col;
	private int passed;

	private boolean last;
	private boolean first;

	public Cell(int row, int col, Grid grid) 
	{
		this.row=row;
		this.col=col;
		this.passed=0;
		
		addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	if (!getBackground().equals(Color.BLACK))
            		setBackground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	if (!getBackground().equals(Color.BLACK))
            		setBackground(defaultBackground);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
            	if(e.getButton() == MouseEvent.BUTTON1) { //left click
            		setBackground(Color.BLACK);
                    grid.addBlock(row, col);
                  }
                  if(e.getButton() == MouseEvent.BUTTON3) { //right click
                	  setBackground(defaultBackground);
                      grid.removeBlock(row, col);
                  }
                
            }
        });
    }

	public void setPassed(int passed) {
		this.passed = passed;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
    public Dimension getPreferredSize() {
       return new Dimension(25, 25);
    }

	public void setLast() {
		this.last=true;		
	}

	public void setFirst() {
		this.first=true;
		
	}

	public boolean isLast() {
		return last;
	}

	public boolean isFirst() {
		return first;
	}

	public int getPassed() {
		return this.passed;
	}
	
}