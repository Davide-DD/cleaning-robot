package it.unibo.ppcr.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class Panel extends JPanel 
{
	private static final long serialVersionUID = -3829123262794230982L;
	
	private Cell[][] cells;
	private int row;
	private int col;
	
    public Panel(int r, int c, Grid grid) 
    {
    	this.cells = new Cell[r][c];
    	this.row=r;
    	this.col=c;
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

                Cell cell = new Cell();
                cell.setBackground(Color.DARK_GRAY);
                cell.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
                add(cell, gbc);
                cells[row][col] = cell;
            }
        }
	}

	public void setCleaned(int row, int col) {
		cells[row][col].setBackground(Color.WHITE);		
	}

	public void setObstacle(int row, int col) {
		cells[row][col].setBackground(Color.BLACK);			
	}
}