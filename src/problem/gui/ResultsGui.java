package problem.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;

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
import problem.model.detectors.AdapterDetector;
import problem.models.api.IClass;
import problem.models.impl.Model;

public class ResultsGui implements ActionListener {

	JFrame frame;
	JPanel panel;
	JPanel checkBoxPanel;
	JScrollPane pane;
	JSplitPane splitPane;
	String inputClasses;
	String dotPath;
	String sdPath;
	String outputDir;
	String phases;
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

		

		this.frame = new JFrame("UMLLAMA Results");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(750, 750));

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

		// checkBoxPanel = new JPanel(new GridLayout(0, 1));
		checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		// checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
		
		//add in the classes that are not part of a pattern
		JCheckBox p = new JCheckBox("no pattern");
		p.setName("none");
		p.addActionListener(this);
		p.setActionCommand("none_patternAction");
		patternBoxes.add(p);
		checkBoxPanel.add(p);
		createNodes("none");

		// checkBoxPanel.add(patternTree);
		checkBoxPanel.setVisible(true);
		// checkBoxPanel.setPreferredSize(new Dimension(170, 50));

		JScrollPane listScrollPane = new JScrollPane(checkBoxPanel);
		listScrollPane.setPreferredSize(new Dimension(150, 150));

		Icon icon = new ImageProxy(dp.getOutputDir() + "graph1.png");
		imageLabel = new JLabel(icon);
		this.pane.add(imageLabel);
		this.pane.setVisible(true);

		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, this.pane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(180);

		Component xyz = this.splitPane.getRightComponent();
		xyz.setVisible(true);

		frame.add(this.splitPane);

		// Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(170, 50);
		listScrollPane.setMinimumSize(minimumSize);

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
		for(IClass c : model.getClasses()){
			if(!this.classesInPatterns.contains(c.getName())){
				patternlessClasses.add(c.getClass());
			}
		}
		this.patternToClasses.put("none", patternlessClasses);
		
	}

	private void createNodes(String root) {
		Model model = Model.getInstance();

		if (root.equals("adapter")) {
			for (IClass c : model.getClasses()) {
				if (c instanceof AdapterDecorator || c instanceof AdapteeDecorator || c instanceof TargetDecorator) {
					JCheckBox pClass = new JCheckBox("- " + c.getName());
					pClass.setName(c.getName());
					pClass.addActionListener(this);
					pClass.setActionCommand(c.getName());
					pClass.setIconTextGap(20);
					this.checkBoxPanel.add(pClass);
					this.patternBoxes.add(pClass);
					this.classesInPatterns.add(c.getName());
					this.checkBoxPanel.revalidate();
				}
			}
		}
		if (root.equals("decorator")) {
			for (IClass c : model.getClasses()) {
				if (c instanceof DecoratorDecorator || c instanceof ComponentDecorator) {
					JCheckBox pClass = new JCheckBox("- " + c.getName());
					pClass.setName(c.getName());
					pClass.addActionListener(this);
					pClass.setActionCommand(c.getName());
					pClass.setIconTextGap(20);
					this.checkBoxPanel.add(pClass);
					this.patternBoxes.add(pClass);
					this.classesInPatterns.add(c.getName());
					this.checkBoxPanel.revalidate();
				}
			}
		}
		if (root.equals("composite")) {
			for (IClass c : model.getClasses()) {
				if (c instanceof CompositeComponentDecorator || c instanceof CompositeDecorator
						|| c instanceof LeafDecorator) {
					JCheckBox pClass = new JCheckBox("- " + c.getName());
					pClass.setName(c.getName());
					pClass.addActionListener(this);
					pClass.setActionCommand(c.getName());
					pClass.setIconTextGap(20);
					this.checkBoxPanel.add(pClass);
					this.patternBoxes.add(pClass);
					this.classesInPatterns.add(c.getName());
					this.checkBoxPanel.revalidate();
				}
			}
		}
		if (root.equals("singleton")) {
			for (IClass c : model.getClasses()) {
				if (c instanceof SingletonDecorator) {
					JCheckBox pClass = new JCheckBox("- " + c.getName());
					pClass.setName(c.getName());
					pClass.addActionListener(this);
					pClass.setActionCommand(c.getName());
					pClass.setIconTextGap(20);
					this.checkBoxPanel.add(pClass);
					this.patternBoxes.add(pClass);
					this.classesInPatterns.add(c.getName());
					this.checkBoxPanel.revalidate();
				}
			}
		}
		if (root.equals("none")) {
			for (IClass c : model.getClasses()) {
				if (!this.classesInPatterns.contains(c.getName())) {
					JCheckBox pClass = new JCheckBox("- " + c.getName());
					pClass.setName(c.getName());
					pClass.addActionListener(this);
					pClass.setActionCommand(c.getName());
					pClass.setIconTextGap(20);
					this.checkBoxPanel.add(pClass);
					this.patternBoxes.add(pClass);
					this.classesInPatterns.add(c.getName());
					this.checkBoxPanel.revalidate();
				}
			}
		}
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
		if (e.getActionCommand().contains("patternAction")) {
			int index = e.getActionCommand().indexOf("_");
			if (source.isSelected()) {
				addPatternClasses(e.getActionCommand().substring(0, index));
			} else {
				removePatternClasses(e.getActionCommand().substring(0, index));
			}
		} else {
			if (source.isSelected()) {
				model.addClassToVisit(e.getActionCommand());
			} else {
				model.removeClassToVisit(e.getActionCommand());
			}
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
}
