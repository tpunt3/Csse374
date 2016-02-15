package problem.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import problem.asm.DesignParser;
import problem.asm.DocType;

public class ResultsGui implements ActionListener {

	JFrame frame;
	JPanel panel;
	JList list;
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

	ArrayList<JCheckBox> patternBoxes;

	public ResultsGui() throws IOException {
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

		pane = new JScrollPane(imageComponent, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setPreferredSize(new Dimension(750, 750));
		pane.setVisible(true);
		// frame.add(pane);

//		try {
//			readProperties();
//		} catch (IOException e1) {
//			System.out.println("make sure there is a config.properties in the input_output folder");
//		}

		patternBoxes = new ArrayList<JCheckBox>();

		JPanel checkBoxPanel = new JPanel(new GridLayout(0, 1));

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

		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, this.panel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);

		frame.add(this.splitPane);

		Icon icon = new ImageProxy(this.outputDir + "graph1.png");
		imageComponent = new ImageComponent(icon);
		imageComponent.setIcon(new ImageProxy(this.outputDir + "graph1.png"));

		pane.revalidate();
		pane.repaint();
		frame.repaint();
		frame.pack();

		// Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		listScrollPane.setMinimumSize(minimumSize);
		this.panel.setMinimumSize(minimumSize);

		DesignParser dp = new DesignParser(this.dotPath, "");
		dp.setOutputDir(outputDir);
		dp.setPhases(this.parserPhases);
		System.out.println(this.parserPhases.size());

		System.out.println("images dimensions: " + imageComponent.getSize());
		pane.setViewportView(imageComponent);

		System.out.println(pane.getViewport().getView().toString());

		pane.revalidate();
		pane.repaint();
		frame.repaint();
		frame.pack();

		try {
			dp.generateDocuments(DocType.uml, "problem.asm.DesignParser,DesignParser,generateSD,Model; ISubMethod; int",
					2, classes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.repaint();
		System.out.println("second repaint");

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
		// System.out.println("making image now");
		// BufferedImage myPic = ImageIO.read(new File(outputDir +
		// "graph1.png"));
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
		if (e.getActionCommand().equals("singleton")) {
			JCheckBox source = (JCheckBox) e.getSource();
			if (source.isSelected()) {
				//recreate pic with 
			} else {

			}

		} else if (e.getActionCommand().equals("adapter")) {
			JCheckBox source = (JCheckBox) e.getSource();
			if (source.isSelected()) {

			} else {

			}
		} else if (e.getActionCommand().equals("composite")) {
			JCheckBox source = (JCheckBox) e.getSource();
			if (source.isSelected()) {

			} else {

			}
		} else if (e.getActionCommand().equals("decorator")) {
			JCheckBox source = (JCheckBox) e.getSource();
			if (source.isSelected()) {

			} else {

			}
		}

	}

//	private void readProperties() throws IOException {
//		try {
//			File file = new File("resources/config.properties");
//			FileInputStream fInput = new FileInputStream(file);
//			Properties p = new Properties();
//
//			if (fInput != null) {
//				p.load(fInput);
//				fInput.close();
//			}
//
//			this.dotPath = p.getProperty("Dot-Path");
//			this.inputClasses = p.getProperty("Input-Classes");
//			this.outputDir = p.getProperty("Output-Directory");
//			this.phases = p.getProperty("Phases");
//			splitPhases();
//
//			this.classes = this.inputClasses.split(",");
//			for (String s : this.classes) {
//				s = s.trim();
//			}
//
//		} catch (Exception e) {
//			System.out.println("Exception: " + e);
//		}
//	}
//
//	private void splitPhases() {
//		parserPhases = new ArrayList<String>();
//		String[] phase = this.phases.split(",");
//		for (String s : phase) {
//			s = s.trim();
//			this.parserPhases.add(s);
//		}
//	}

}
