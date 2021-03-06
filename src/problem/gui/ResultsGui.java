package problem.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import problem.asm.DesignParser;
import problem.model.decorators.AdapteeDecorator;
import problem.model.decorators.AdapterDecorator;
import problem.model.decorators.ComponentDecorator;
import problem.model.decorators.CompositeComponentDecorator;
import problem.model.decorators.CompositeDecorator;
import problem.model.decorators.DecoratorDecorator;
import problem.model.decorators.LeafDecorator;
import problem.model.decorators.SingletonDecorator;
import problem.model.decorators.TargetDecorator;
import problem.models.api.IClass;
import problem.models.impl.Model;

import static java.nio.file.StandardCopyOption.*;

public class ResultsGui implements ActionListener {

	JFrame frame;
	JPanel panel;
	JPanel checkBoxPanel;
	JScrollPane pane;
	JSplitPane splitPane;
	String outputDir;
	ArrayList<String> parserPhases;
	ArrayList<String> classesInPatterns;
	JLabel imageLabel;
	DesignParser dp;
	Model model;
	Map<String, ArrayList<Class>> patternToClasses;
	ArrayList<JCheckBox> patternBoxes;

	public ResultsGui(DesignParser parser) throws IOException {
		this.model = Model.getInstance();
		model.setClassesToVisit(new HashSet<String>());

		this.dp = parser;

		this.patternToClasses = new HashMap<String, ArrayList<Class>>();
		this.classesInPatterns = new ArrayList<String>();

		this.outputDir = dp.getOutputDir();

		this.frame = new JFrame("UMLLAMA Results");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(1050, 750));

		// create menu bar
		JMenuBar menuBar = addMenu();

		this.panel = new JPanel();
		this.panel.setPreferredSize(new Dimension(750, 750));
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));

		// add panel to frame
		frame.setVisible(true);
		frame.setJMenuBar(menuBar);

		pane = new JScrollPane(imageLabel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.setPreferredSize(new Dimension(750, 750));
		pane.setVisible(true);

		patternBoxes = new ArrayList<JCheckBox>();

		checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		this.parserPhases = dp.getPhases();

		for (int i = 1; i < parserPhases.size() - 1; i++) {
			JCheckBox p = new JCheckBox(parserPhases.get(i) + " pattern");
			p.setName(parserPhases.get(i));
			p.addActionListener(this);
			p.setActionCommand(parserPhases.get(i) + "_patternAction");
			patternBoxes.add(p);
			checkBoxPanel.add(p);
			createNodes(parserPhases.get(i));
		}

		addPatternsToMap();

		// add in the classes that are not part of a pattern
		JCheckBox p = new JCheckBox("no pattern");
		p.setName("none");
		p.addActionListener(this);
		p.setActionCommand("none_patternAction");
		patternBoxes.add(p);
		checkBoxPanel.add(p);
		createNodes("none");

		checkBoxPanel.setVisible(true);

		JScrollPane listScrollPane = new JScrollPane(checkBoxPanel);
		listScrollPane.setPreferredSize(new Dimension(150, 150));

		Icon icon = new ImageProxy(dp.getOutputDir() + "graph1.png");
		imageLabel = new JLabel(icon);
		this.pane.add(imageLabel);
		this.pane.setVisible(true);

		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, this.pane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(180);

		frame.add(this.splitPane);

		listScrollPane.setMinimumSize(new Dimension(170, 50));

		pane.setViewportView(imageLabel);

		frame.pack();
		frame.repaint();
	}

	private void addPatternsToMap() {
		ArrayList<Class> adapterClasses = new ArrayList<Class>();
		adapterClasses.add(AdapterDecorator.class);
		adapterClasses.add(AdapteeDecorator.class);
		adapterClasses.add(TargetDecorator.class);
		this.patternToClasses.put("adapter", adapterClasses);

		ArrayList<Class> compositeClasses = new ArrayList<Class>();
		compositeClasses.add(LeafDecorator.class);
		compositeClasses.add(CompositeComponentDecorator.class);
		compositeClasses.add(CompositeDecorator.class);
		this.patternToClasses.put("composite", compositeClasses);

		ArrayList<Class> singletonClasses = new ArrayList<Class>();
		singletonClasses.add(SingletonDecorator.class);
		this.patternToClasses.put("singleton", singletonClasses);

		ArrayList<Class> decoratorClasses = new ArrayList<Class>();
		decoratorClasses.add(DecoratorDecorator.class);
		decoratorClasses.add(ComponentDecorator.class);
		this.patternToClasses.put("decorator", decoratorClasses);

		ArrayList<Class> patternlessClasses = new ArrayList<Class>();
		for (IClass c : model.getClasses()) {
			if (!this.classesInPatterns.contains(c.getName())) {
				patternlessClasses.add(c.getClass());
			}
		}
		this.patternToClasses.put("none", patternlessClasses);

	}

	private void createNodes(String root) {

		if (root.equals("adapter")) {
			for (IClass c : model.getClasses()) {
				if (c instanceof AdapterDecorator || c instanceof AdapteeDecorator || c instanceof TargetDecorator) {
					JCheckBox pClass = new JCheckBox("- " + c.getName());
					pClass.setName(c.getName());
					pClass.setForeground(Color.red);
					pClass.addActionListener(this);
					pClass.setActionCommand(c.getName());
					pClass.setIconTextGap(20);
					this.checkBoxPanel.add(pClass);
					this.patternBoxes.add(pClass);
					this.classesInPatterns.add(c.getName());
				}
			}
		}
		if (root.equals("decorator")) {
			for (IClass c : model.getClasses()) {
				if (c instanceof DecoratorDecorator || c instanceof ComponentDecorator) {
					JCheckBox pClass = new JCheckBox("- " + c.getName());
					pClass.setName(c.getName());
					pClass.setForeground(Color.green);
					pClass.addActionListener(this);
					pClass.setActionCommand(c.getName());
					pClass.setIconTextGap(20);
					this.checkBoxPanel.add(pClass);
					this.patternBoxes.add(pClass);
					this.classesInPatterns.add(c.getName());
				}
			}
		}
		if (root.equals("composite")) {
			for (IClass c : model.getClasses()) {
				if (c instanceof CompositeComponentDecorator || c instanceof CompositeDecorator
						|| c instanceof LeafDecorator) {
					JCheckBox pClass = new JCheckBox("- " + c.getName());
					pClass.setName(c.getName());
					pClass.setForeground(Color.yellow);
					pClass.addActionListener(this);
					pClass.setActionCommand(c.getName());
					pClass.setIconTextGap(20);
					this.checkBoxPanel.add(pClass);
					this.patternBoxes.add(pClass);
					this.classesInPatterns.add(c.getName());
				}
			}
		}
		if (root.equals("singleton")) {
			for (IClass c : model.getClasses()) {
				if (c instanceof SingletonDecorator) {
					JCheckBox pClass = new JCheckBox("- " + c.getName());
					pClass.setName(c.getName());
					pClass.setForeground(Color.blue);
					pClass.addActionListener(this);
					pClass.setActionCommand(c.getName());
					pClass.setIconTextGap(20);
					this.checkBoxPanel.add(pClass);
					this.patternBoxes.add(pClass);
					this.classesInPatterns.add(c.getName());
				}
			}
		}
		if (root.equals("none")) {
			for (IClass c : model.getClasses()) {
				if (!this.classesInPatterns.contains(c.getName())) {
					JCheckBox pClass = new JCheckBox("- " + c.getName());
					pClass.setName(c.getName());
					pClass.setForeground(Color.cyan);
					pClass.addActionListener(this);
					pClass.setActionCommand(c.getName());
					pClass.setIconTextGap(20);
					this.checkBoxPanel.add(pClass);
					this.patternBoxes.add(pClass);
					this.classesInPatterns.add(c.getName());
				}
			}
		}
		this.checkBoxPanel.revalidate();
	}

	private JMenuBar addMenu() {
		// create MenuBar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.getAccessibleContext().setAccessibleDescription("Use this menu to get more information or help");

		JMenuItem helpItem = new JMenuItem();
		helpItem.setMnemonic(KeyEvent.VK_S);
		helpItem.setText("Help");
		helpItem.setActionCommand("help");
		helpItem.addActionListener(this);
		helpMenu.add(helpItem);

		JMenuItem aboutItem = new JMenuItem();
		aboutItem.setMnemonic(KeyEvent.VK_S);
		aboutItem.setText("About");
		aboutItem.setActionCommand("about");
		aboutItem.addActionListener(this);
		helpMenu.add(aboutItem);

		JMenuItem exportItem = new JMenuItem();
		exportItem.setMnemonic(KeyEvent.VK_E);
		exportItem.setText("Export");
		exportItem.setActionCommand("export");
		exportItem.addActionListener(this);
		fileMenu.add(exportItem);

		JMenuItem restartItem = new JMenuItem();
		restartItem.setMnemonic(KeyEvent.VK_R);
		restartItem.setText("Restart");
		restartItem.setActionCommand("restart");
		restartItem.addActionListener(this);
		fileMenu.add(restartItem);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();
		if (cmd.contains("patternAction")) {
			JCheckBox source = (JCheckBox) e.getSource();
			int index = e.getActionCommand().indexOf("_");
			if (source.isSelected()) {
				addPatternClasses(e.getActionCommand().substring(0, index));
			} else {
				removePatternClasses(e.getActionCommand().substring(0, index));
			}
			regenerate();
		} else if (cmd.equals("help")) {
			showHelp();
		} else if (cmd.equals("about")) {
			showAbout();
		} else if (cmd.equals("export")) {
			export();
		} else if (cmd.equals("restart")) {
			restart();
		} else {
			JCheckBox source = (JCheckBox) e.getSource();
			if (source.isSelected()) {
				model.addClassToVisit(e.getActionCommand());
			} else {
				model.removeClassToVisit(e.getActionCommand());
			}
			regenerate();
		}

	}

	public void regenerate() {
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

				if (imageLabel.getIcon() instanceof ImageProxy) {
					ImageProxy proxy = (ImageProxy) imageLabel.getIcon();
					proxy.flushImageProxy();
				}
				imageLabel.setIcon(new ImageProxy(dp.getOutputDir() + "graph1.png"));
				frame.pack();
				frame.repaint();
			}
		};
		runner.start();
	}

	public void addPatternClasses(String patternName) {
		for (IClass c : model.getClasses()) {
			for (int i = 0; i < this.patternToClasses.get(patternName).size(); i++) {
				if (c.getClass().equals(this.patternToClasses.get(patternName).get(i))) {
					model.addClassToVisit(c.getName());
					for (JCheckBox j : this.patternBoxes) {
						if (j.getName().equals(c.getName())) {
							j.setSelected(true);
						}
					}
				}
			}
		}
	}

	public void removePatternClasses(String patternName) {
		for (IClass c : model.getClasses()) {
			for (int i = 0; i < this.patternToClasses.get(patternName).size(); i++) {
				if (c.getClass().equals(this.patternToClasses.get(patternName).get(i))) {
					model.removeClassToVisit(c.getName());
					for (JCheckBox j : this.patternBoxes) {
						if (j.getName().equals(c.getName())) {
							j.setSelected(false);
						}
					}
				}
			}
		}
	}

	private void restart() {
		if (imageLabel.getIcon() instanceof ImageProxy) {
			ImageProxy proxy = (ImageProxy) imageLabel.getIcon();
			proxy.flushImageProxy();
		}
		model.clearModel();
		frame.dispose();
		MainGui main = new MainGui();
		main.createLandingScreen();
	}

	private void export() {
		File source = new File(outputDir + "/graph1.png");
		File target = null;
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose Location");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			target = chooser.getSelectedFile();
		}
		if (target != null) {
			String fileName = JOptionPane.showInputDialog("Enter your new file name ending in .png");
			if (!fileName.endsWith("png")) {
				fileName = fileName + ".png";
			}
			File newFile = new File(target.getPath() + "\\" + fileName);
			try {
				System.out.println(source.toPath().toString() + "  " + newFile.toString().toString());
				Files.copy(source.toPath(), newFile.toPath(), REPLACE_EXISTING);
			} catch (IOException e) {
				System.out.println("IO Exception exporting file");
			}
		}

	}

	private void showAbout() {
		ImageIcon img = new ImageIcon("resources/alpacaLogo.jpg");
		JOptionPane.showMessageDialog(null,
				"This product, UMLLAMA(TM), was developed by Katie Lee and Trent Punt as a service "
						+ " to help users \n generate documentation for code and help understanding and visualization of design patterns. Copyright 2016.",
				"UMLLAMA About", JOptionPane.INFORMATION_MESSAGE, img);
	}

	private void showHelp() {
		JOptionPane.showMessageDialog(null,
				"Clicking boxes on the left side will add those classes to the uml displayed\n "
						+ "on the right side. Clicking patterns on the left will add all the classes in that\n"
						+ "pattern to the uml on the right side. Export the png to your computer by selecting\n "
						+ "File->Export",
				"UMLLAMA Help", JOptionPane.QUESTION_MESSAGE);

	}
}
