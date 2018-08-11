package it.unibo.hue.gui;

import javax.swing.*;

import it.unibo.hue.components.BluePlayer;
import it.unibo.hue.components.hueClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {

	private boolean party = true;

	public Gui() {
		int num = hueClient.getNumLights();

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		if (num != 1) {
			JLabel onLabel = new JLabel("ON");
			onLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			mainPanel.add(onLabel);
		}

		mainPanel.add(Box.createVerticalGlue());

		JPanel onPanel = new JPanel();
		for (int i = 1; i < num + 1; i++) {
			final int index = i;
			JButton on = num == 1 ? new JButton("ON") : new JButton("" + i);
			on.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hueClient.on(index);
				}
			});
			onPanel.add(on);
		}
		mainPanel.add(onPanel);

		mainPanel.add(Box.createVerticalGlue());

		if (num != 1) {
			JLabel offLabel = new JLabel("OFF");
			offLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			mainPanel.add(offLabel);
		}

		mainPanel.add(Box.createVerticalGlue());

		JPanel offPanel = new JPanel();
		for (int i = 1; i < num + 1; i++) {
			final int index = i;
			JButton off = num == 1 ? new JButton("OFF") : new JButton("" + i);
			off.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hueClient.off(index);
				}
			});
			offPanel.add(off);
		}
		mainPanel.add(offPanel);

		mainPanel.add(Box.createVerticalGlue());

		if (num != 1) {
			JLabel blinkLabel = new JLabel("BLINK");
			blinkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			mainPanel.add(blinkLabel);
		}

		mainPanel.add(Box.createVerticalGlue());

		JPanel blinkPanel = new JPanel();
		for (int i = 1; i < num + 1; i++) {
			final int index = i;
			JButton blink = num == 1 ? new JButton("BLINK") : new JButton("" + i);
			blink.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hueClient.blink(index);
				}
			});
			blinkPanel.add(blink);
		}
		mainPanel.add(blinkPanel);

		mainPanel.add(Box.createVerticalGlue());

		// EASTER EGG JUST FOR FUN: change party value to activate/deactivate
		if (party && num!=1) {
			JLabel partyMode = new JLabel("PARTY MODE!");
			partyMode.setAlignmentX(Component.CENTER_ALIGNMENT);
			mainPanel.add(partyMode);

			mainPanel.add(Box.createVerticalGlue());

			JPanel partyPanel = new JPanel();
			JButton start = new JButton("START");
			start.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BluePlayer.play();
					for (int i = 1; i < num + 1; i++) {
						try {
							Thread.sleep(250);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						hueClient.blink(i);
					}
				}
			});
			partyPanel.add(start);
			JButton stop = new JButton("STOP");
			stop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BluePlayer.stop();
					for (int i = 1; i < num + 1; i++) {
						hueClient.off(i);
					}
				}
			});
			partyPanel.add(stop);
			mainPanel.add(partyPanel);
		}

		mainPanel.add(Box.createVerticalGlue());

		JFrame window = new JFrame("TEST");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(mainPanel);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		if (num == 1)
			window.setSize(new Dimension(200, 200));
		else
			window.pack();
		window.setResizable(false);

	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Gui();
			}
		});
	}

}