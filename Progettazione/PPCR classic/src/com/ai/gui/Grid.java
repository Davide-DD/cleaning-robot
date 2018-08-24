package com.ai.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.ai.components.Node;
import com.ai.components.Path;

public class Grid {
	private Panel panel;
	private JButton start;
	private JFrame frame;
	private JLabel label;

	// TEST
	private final boolean testDraw = false;
	private JFrame testFrame;

	private List<int[]> blocks;
	private int count;
	private boolean optimal;

	public Grid(int row, int col, boolean optimal) {
		this.panel = new Panel(row, col, this);
		this.count = 0;
		this.optimal = optimal;
		initUi();
	}

	public JFrame getFrame() {
		return frame;
	}

	public JButton getStart() {
		return start;
	}

	public void initUi() {
		if (optimal)
			frame = new JFrame("PPCR App (optimal)");
		else
			frame = new JFrame("PPCR App");
		start = new JButton("START");
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeMouseListeners();
			}

		});
		JPanel up = new JPanel();
		up.add(start);

		JPanel center = new JPanel();
		center.add(panel);

		JPanel down = new JPanel();
		label = new JLabel("Moves: " + count);
		down.add(label);

		if (testDraw) {
			testFrame = new JFrame("TEST");
			JPanel upTest = new JPanel();

			JTextArea area = new JTextArea(30, 100);
			area.setLineWrap(true);
			upTest.add(area);

			JPanel downTest = new JPanel();
			JButton drawPath = new JButton("DRAW");
			drawPath.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					new Thread() {
						public void run() {
							String toDraw = area.getText();
							if (toDraw != null)
								drawPath(Path.getPathFromString(toDraw), true);
						}
					}.start();
				}

			});
			drawPath.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					panel.removeMouseListeners();
				}

			});
			downTest.add(drawPath);
			testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			testFrame.setLayout(new BorderLayout());
			testFrame.add(downTest, BorderLayout.SOUTH);
			testFrame.add(upTest, BorderLayout.NORTH);
			testFrame.pack();
			//testFrame.setBounds(x+jframe1.getWidth(), y, width2, height2);
			testFrame.setVisible(true);

		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(down, BorderLayout.SOUTH);
		frame.add(center, BorderLayout.CENTER);
		frame.add(up, BorderLayout.NORTH);
		if(testDraw)
		{
			frame.setBounds(50,200, 300,300);
			testFrame.setBounds(frame.getWidth(), 200, 300, 300);
			testFrame.pack();
		}
		else
			frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		
		// frame.setResizable(false);

		blocks = new ArrayList<int[]>();
	}

	public Panel getPanel() {
		return panel;
	}

	public void addBlock(int row, int col) {
		blocks.add(new int[] { row, col });
	}

	public List<int[]> getBlocks() {
		return blocks;
	}

	public void removeBlock(int row, int col) {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i)[0] == row && blocks.get(i)[1] == col) {
				blocks.remove(i);
				break;
			}
		}
	}

	public void drawPath(Path path, boolean ppcrApp) {
		List<Node> nodesList = path.getPath();
		for (int i = 0; i < nodesList.size(); i++) {
			if (i == 0)
				panel.setFirst(nodesList.get(i));
			if (i == nodesList.size() - 1)
				panel.setLast(nodesList.get(i));
			panel.colour(nodesList.get(i), i == nodesList.size() - 1);
			label.setText("Moves: " + count++);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!panel.allCleaned() && ppcrApp)
			JOptionPane.showMessageDialog(null, "Found an impassable obstacle: could not clean the room completely!",
					"ERROR", JOptionPane.INFORMATION_MESSAGE);

	}
}
