package it.unibo.ppcr.gui;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.ppcr.ai.ppcr;

public class Grid 
{
	private Panel panel;
	private JFrame frame;
	public Grid(int row, int col)
    {
    	this.panel = new Panel(row,col, this);
    	initUi();
    }
    
    public void setCleaned(int row, int col)
    {
    	panel.setCleaned(row, col);    	
    }
    
    public void setObstacle(int row, int col)
    {
    	panel.setObstacle(row, col);  
    }
    
    public void impassableObstacle()
    {
    	JOptionPane.showMessageDialog(null, "Found an impassable obstacle: could not clean the room completely!","ERROR", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void finished()
    {
    	JOptionPane.showMessageDialog(null, "Cleaning complete!","FINISHED", JOptionPane.INFORMATION_MESSAGE);
    }
    

	public void obstructedFinalPosition() {
		JOptionPane.showMessageDialog(null, "Could not find a path to reach final position! Stopping..","ERROR", JOptionPane.INFORMATION_MESSAGE);		
	}

    
    public JFrame getFrame() {
		return frame;
	}
	
	public void initUi()
    {
		frame = new JFrame("PPCR Application");
    	
    	JPanel center = new JPanel();
    	center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
    	center.add(panel);    
    	center.add(Box.createVerticalGlue());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(center, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    
    public Panel getPanel() {
		return panel;
	}
    
    public void restart()
    {
    	ppcr.init(ppcr.getGrid());
    	frame.dispose();
    }
	
}   