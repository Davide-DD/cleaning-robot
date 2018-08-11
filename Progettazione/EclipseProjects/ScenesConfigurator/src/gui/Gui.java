package gui;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Gui {

	private String scenesFolder = "C:\\Users\\Federico Livi\\Desktop\\SoffrittiServer\\WebGLScene\\tests";
	private Path mainScene = Paths
			.get("C:\\Users\\Federico Livi\\Desktop\\SoffrittiServer\\WebGLScene\\sceneConfig.js");

	public void initUi() {
		JFrame frame = new JFrame("");

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel title = new JLabel("Seleziona scena da inserire:");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(title);

		panel.add(Box.createVerticalGlue());

		final File folder = new File(scenesFolder);
		for (final File fileEntry : folder.listFiles()) {
			String fileName = fileEntry.getName();
			if (!fileEntry.isDirectory() && fileName.endsWith(".js")) {
				String noExtension=fileName.substring(0, fileName.lastIndexOf('.'));

				JButton button = new JButton(noExtension);
				button.setAlignmentX(Component.CENTER_ALIGNMENT);
				panel.add(button);
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Path sourceDirectory = Paths.get(scenesFolder + "\\" + fileName);
						try {
							Files.copy(sourceDirectory, mainScene, StandardCopyOption.REPLACE_EXISTING);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, fileName + " copiato con successo!", "FFFFATTO",
								JOptionPane.INFORMATION_MESSAGE);
					}
				});

				panel.add(Box.createVerticalGlue());
			}
		}

		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}

}
