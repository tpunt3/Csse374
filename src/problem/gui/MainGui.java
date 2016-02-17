package problem.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import problem.asm.DesignParser;
import problem.models.api.IModel;

public class MainGui implements ActionListener, PropertyChangeListener {

	JFrame frame;
	JPanel panel;
	JProgressBar jpb;
	String inputClasses;
	String[] classes;
	String dotPath;
	String sdPath;
	String outputDir;
	String phases;
	private ArrayList<String> parserPhases;
	DesignParser dp;
	JButton moveOn;

	public MainGui() {
	}

	public void createLandingScreen() {

		Path graphPath = FileSystems.getDefault().getPath("input_output/graph1.png");
		Path configPath = FileSystems.getDefault().getPath("resources/config.properties");

		// try {
		// Files.deleteIfExists(graphPath);
		// Files.deleteIfExists(configPath);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// create frame
		this.frame = new JFrame("UMLLAMA");
		ImageIcon img = new ImageIcon("resources/alpacaLogo.jpg");
		frame.setBackground(Color.white);
		frame.setForeground(Color.black);
		frame.setIconImage(img.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(300, 200));

		// create menu bar
		JMenuBar menuBar = addMenu();

		// create content for panel
		JButton loadConfigButton = new JButton("Load Config");
		loadConfigButton.setActionCommand("loadConfig");
		loadConfigButton.addActionListener(this);
		JButton analyzeButton = new JButton("Analyze");
		analyzeButton.setActionCommand("analyze");
		analyzeButton.addActionListener(this);

		jpb = new JProgressBar();
		jpb.setForeground(Color.green);
		jpb.setMinimumSize(new Dimension(150, 50));
		jpb.setStringPainted(true);
		jpb.setBorderPainted(true);

		moveOn = new JButton("Go to results");
		moveOn.setActionCommand("results");
		moveOn.addActionListener(this);

		// create panel and add content
		SpringLayout layout = new SpringLayout();
		this.panel = new JPanel(layout);
		panel.add(loadConfigButton);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loadConfigButton, -loadConfigButton.getWidth() - 50,
				SpringLayout.HORIZONTAL_CENTER, this.panel);
		layout.putConstraint(SpringLayout.NORTH, loadConfigButton, 5, SpringLayout.NORTH, this.panel);
		panel.add(analyzeButton);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, analyzeButton, analyzeButton.getWidth() + 50,
				SpringLayout.HORIZONTAL_CENTER, this.panel);
		layout.putConstraint(SpringLayout.NORTH, analyzeButton, 5, SpringLayout.NORTH, this.panel);
		panel.add(jpb);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, jpb, 5, SpringLayout.HORIZONTAL_CENTER, this.panel);
		layout.putConstraint(SpringLayout.NORTH, jpb, 75, SpringLayout.NORTH, this.panel);
		panel.add(moveOn);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, moveOn, 5, SpringLayout.HORIZONTAL_CENTER, this.panel);
		layout.putConstraint(SpringLayout.NORTH, moveOn, 100, SpringLayout.NORTH, this.panel);

		moveOn.setVisible(false);

		// add panel to frame
		panel.setVisible(true);
		frame.add(panel);
		frame.setJMenuBar(menuBar);
		frame.pack();
	}

	public JMenuBar addMenu() {
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

	public void disableFrame() {
		System.out.println("disabling");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("analyze")) {
			analyzeClicked();
		} else if (cmd.equals("loadConfig")) {
			ConfigFrame config = ConfigFrame.getInstance();
		} else if (cmd.equals("results")) {
			startResults();
		} else if (cmd.equals("help")) {
			showHelp();
		} else if (cmd.equals("about")) {
			showAbout();
		}
	}

	private void showAbout() {
		ImageIcon img = new ImageIcon("resources/alpacaLogo.jpg");
		JOptionPane.showMessageDialog(null,
				"This product, UMLLAMA(TM), was developed by Katie Lee and Trent Punt as a service "
						+ " to help users \n generate documentation for code and help understanding and visualization of design patterns. Copyright 2016.", "UMLLAMA About", JOptionPane.INFORMATION_MESSAGE,img);
	}

	private void showHelp() {
		JOptionPane.showMessageDialog(null,
				"Load config: clicking this button brings up a new window where you can load an existing configuration \n"
				+ " for UMLLAMA or create a new configuration. If you choose to load an existing configuration, please choose a \n"
				+ "file that ends in .properties. To create a new configuration, enter fully qualified classnames of every class,\n"
				+ " a valid output directory ending in a slash(/), a fully qualified path to your dot.exe, and a list of phases that\n"
				+ " you would like the product to complete (valid phases are: visit,composite,adapter,singleton,decorator,dot)","UMLLAMA Help", JOptionPane.QUESTION_MESSAGE);

	}

	private void startResults() {
		Thread resultGui = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ResultsGui results = new ResultsGui(dp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		resultGui.start();
		this.frame.dispose();

	}

	public void analyzeClicked() {

		try {
			readProperties();
		} catch (IOException e1) {
			System.out.println("make sure there is a config.properties in the input_output folder");
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		System.out.println(classes.length + 1);
		jpb.setMaximum(classes.length + 1);
		jpb.setMinimum(0);
		jpb.setVisible(true);
		dp = new DesignParser(this.dotPath, "", jpb);
		dp.addPropertyChangeListener(this);
		dp.setPhases(this.parserPhases);
		dp.setOutputDir(this.outputDir);

		jpb.setValue(dp.getProgress());
		dp.setClasses(classes);

		dp.execute();
	}

	private void readProperties() throws IOException {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					File file = new File("resources/config.properties");
					FileInputStream fInput = new FileInputStream(file);
					Properties p = new Properties();

					if (fInput != null) {
						p.load(fInput);
						fInput.close();
					}

					dotPath = p.getProperty("Dot-Path");
					inputClasses = p.getProperty("Input-Classes");
					outputDir = p.getProperty("Output-Directory");
					phases = p.getProperty("Phases");
					splitPhases();

					classes = inputClasses.split(",");
					for (String s : classes) {
						s = s.trim();
					}

				} catch (Exception e) {
					System.out.println("Exception: " + e);
				}

			}
		});
		t.start();

	}

	private void splitPhases() {
		parserPhases = new ArrayList<String>();
		String[] phase = this.phases.split(",");
		for (String s : phase) {
			s = s.trim();
			this.parserPhases.add(s);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			System.out.println("updating progress " + jpb.getValue());
			int progress = (int) evt.getNewValue();
			jpb.setValue(progress);
			jpb.setString(dp.getCurrentPhase());
		} else if ("done" == evt.getPropertyName()) {
			moveOn.setVisible(true);
		}
	}

}
