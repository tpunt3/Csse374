package problem.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import problem.asm.DesignParser;

public class ResultsGui implements ActionListener {

	JFrame frame;
	JPanel panel;
	JScrollPane pane;
	JSplitPane splitPane;
	String inputClasses;
	String[] classes;
	String dotPath;
	String sdPath;
	String outputDir;
	String phases;
	ArrayList<String> parserPhases;
	ImageComponent imageComponent;
	// JLabel imageComponent;
	DesignParser dp;

	ArrayList<JCheckBox> patternBoxes;

	public ResultsGui(DesignParser parser) throws IOException {
		this.dp = parser;
		this.frame = new JFrame("RESULTSTIME");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(750, 750));

		// create menu bar
		JMenuBar menuBar = addMenu();

		this.panel = new JPanel();
		this.panel.setPreferredSize(new Dimension(500, 500));
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));

		// add panel to frame
		frame.setVisible(true);
		frame.setJMenuBar(menuBar);

		pane = new JScrollPane(imageComponent, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.setPreferredSize(new Dimension(750, 750));
		pane.setVisible(true);
		// frame.add(pane);

		// try {
		// readProperties();
		// } catch (IOException e1) {
		// System.out.println("make sure there is a config.properties in the
		// input_output folder");
		// }

		patternBoxes = new ArrayList<JCheckBox>();

		JPanel checkBoxPanel = new JPanel(new GridLayout(0, 1));
		this.parserPhases = dp.getPhases();

		for (int i = 1; i < parserPhases.size() - 1; i++) {
			JCheckBox p = new JCheckBox(parserPhases.get(i) + " pattern");
			p.addActionListener(this);
			p.setActionCommand(parserPhases.get(i));
			patternBoxes.add(p);
			checkBoxPanel.add(p);
		}

		checkBoxPanel.setVisible(true);
		checkBoxPanel.setPreferredSize(new Dimension(140, 150));

		JScrollPane listScrollPane = new JScrollPane(checkBoxPanel);

		Icon icon = new ImageProxy(dp.getOutputDir() + "graph1.png");
		imageComponent = new ImageComponent(icon);
		// imageComponent = new JLabel(icon);
		this.pane.add(imageComponent);
		this.pane.add(new JButton("Test"));
		this.pane.setVisible(true);

		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, this.pane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);

		Component xyz = this.splitPane.getRightComponent();
		xyz.setVisible(true);

		frame.add(this.splitPane);

		// Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		listScrollPane.setMinimumSize(minimumSize);

		// System.out.println("images dimensions: " + imageComponent.getSize());
		pane.setViewportView(imageComponent);
		//
		// System.out.println(pane.getViewport().getView().toString());

		pane.revalidate();
		pane.repaint();
		frame.repaint();
		frame.pack();

		// try {
		// dp.generateDocuments(DocType.uml,
		// "problem.asm.DesignParser,DesignParser,generateSD,Model; ISubMethod;
		// int",
		// 2, classes);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		frame.repaint();

		//
		// Thread generateThread = new Thread(new Runnable(){
		// @Override
		// public void run() {
		// try {
		// dp.generateDocuments(DocType.uml,
		// "problem.asm.DesignParser,DesignParser,generateSD,Model; ISubMethod;
		// int", 2, classes);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// });
		// generateThread.start();
		// while(dp.getProgress()<this.parserPhases.size()){
		// progressLabel.setText(dp.getCurrentPhase());
		// System.out.println(dp.getProgress() + " out of " + jpb.getMaximum());
		// jpb.setValue(dp.getProgress()/this.parserPhases.size());
		// panel.repaint();
		// if(dp.getCurrentPhase().equals("finished")){
		// break;
		// }
		// }

		// dp.generateDocuments(DocType.uml,
		// "problem.asm.DesignParser,DesignParser,generateSD,Model; ISubMethod;
		// int", 2, classes);
		// System.out.println("making image now: "+dp.getOutputDir());
		// BufferedImage myPic = ImageIO.read(new
		// File(dp.getOutputDir()+"test.png"));
		// JLabel picLabel = new JLabel(new ImageIcon(myPic));
		// this.panel.add(picLabel);
		// this.panel.repaint();
		// this.panel.repaint();

		frame.pack();
	}

	private JMenuBar addMenu() {
		// create MenuBar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.getAccessibleContext().setAccessibleDescription("Use this menu to get more information or help");

		JMenuItem saveItem = new JMenuItem();
		saveItem.setMnemonic(KeyEvent.VK_S);
		saveItem.setText("Save");
		saveItem.setActionCommand("save");
		saveItem.addActionListener(this);
		fileMenu.add(saveItem);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JCheckBox source = (JCheckBox) e.getSource();

		imageComponent.setIcon(new ImageProxy(dp.getOutputDir() + "graph1.png"));
		if (source.isSelected()) {
			dp.addPattern(e.getActionCommand());
			// add check boxes for the selected classes?
		} else {
			dp.removePattern(e.getActionCommand());
			// remove check boxes for the selected classes?
		}

		Thread runner = new Thread() {
			public void run() {
				Path graphPath = FileSystems.getDefault().getPath("input_output/graph1.png");

				try {
					Files.deleteIfExists(graphPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				frame.pack();
				frame.repaint();
				try {
					dp.regenerateModel();
				} catch (IOException e1) {
					System.out.println("IO Exception while regenerating the model");
				}
				imageComponent.setIcon(new ImageProxy(dp.getOutputDir() + "graph1.png"));
				frame.pack();
				frame.repaint();
			}
		};
		runner.start();
	}
}
