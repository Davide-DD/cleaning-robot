package com.ai.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.ai.components.Node;

public class Panel extends JPanel 
{
	private static final long serialVersionUID = -3829123262794230982L;
	
	private Cell[][] cells;
	private int row;
	private int col;
	private Grid grid;
	
    public Panel(int r, int c, Grid grid) 
    {
    	this.cells = new Cell[r][c];
    	this.row=r;
    	this.col=c;
    	this.grid=grid;
    	init();        
    }

	public void init() 
	{
		setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        for (int row = 0; row < this.row; row++) 
        {
            for (int col = 0; col < this.col; col++) 
            {
                gbc.gridx = col;
                gbc.gridy = row;

                Cell cell = new Cell(row, col, grid);
                cell.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
                add(cell, gbc);
                cells[row][col] = cell;
            }
        }
	}
	
	
	public void removeMouseListeners()
	{
		for(int i=0; i<row; i++)
		{
			for(Cell c: cells[i])
			{
				for (MouseListener ml : c.getMouseListeners())
					c.removeMouseListener(ml);
			}
		}		
	}

	public void colour(Node node, boolean last) 
	{
		Cell toColour = cells[node.getRow()][node.getCol()];
		int passed = toColour.getPassed();
		if((toColour.isLast() && last) || toColour.isFirst() )
		{
			toColour.setBackground(Color.GRAY);
			toColour.setPassed(toColour.getPassed()+1);
			return;
		}			
		switch (passed) {
		case 0:  
			toColour.setBackground(Color.GREEN);
			break;
		case 1:  
			toColour.setBackground(Color.YELLOW);
			break;
		case 2:  
			toColour.setBackground(Color.RED);
			break;
		default:
			toColour.setBackground(Color.CYAN);
			break;	
		}
		toColour.setPassed(toColour.getPassed()+1);
	}
	
	public boolean allCleaned()
	{
		for (int row = 0; row < this.row; row++) 
		{
			for (int col = 0; col < this.col; col++) 
			{
				if(cells[row][col].getPassed()==0 && cells[row][col].getBackground()!=Color.BLACK)
					return false;
			}
		}
		return true;
	}
	
	public void setFirst(Node node)
	{
		cells[node.getRow()][node.getCol()].setFirst();
	}
	
	public void setLast(Node node)
	{
		cells[node.getRow()][node.getCol()].setLast();
	}
	
	
}