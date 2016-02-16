package problem.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import problem.asm.DesignParser;

public class MainGui implements ActionListener{
	
	JFrame frame;
	JPanel panel;
	String inputClasses;
	String[] classes;
	String dotPath;
	String sdPath;
	String outputDir;
	String phases;
	private ArrayList<String> parserPhases;
	
	
	public MainGui(){
	}
	
	public void createLandingScreen(){
		
			Path graphPath = FileSystems.getDefault().getPath("input_output/graph1.png");
			Path configPath = FileSystems.getDefault().getPath("resources/config.properties");
			
//			try {
//				Files.deleteIfExists(graphPath);
//				Files.deleteIfExists(configPath);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		
		//create frame
		this.frame = new JFrame("Team Alpaca's Design Parser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(500, 500));
		
		//create menu bar
		JMenuBar menuBar = addMenu();
		
		//create content for panel
		JButton loadConfigButton =  new JButton("Load Config");
		loadConfigButton.setActionCommand("loadConfig");
		loadConfigButton.addActionListener(this);
		JButton analyzeButton = new JButton("Analyze");
		analyzeButton.setActionCommand("analyze");
		analyzeButton.addActionListener(this);
		
		//create panel and add content
		this.panel = new JPanel();
		panel.add(loadConfigButton);
		panel.add(analyzeButton);
		
		//add panel to frame
		panel.setVisible(true);		
		frame.add(panel);
		frame.setJMenuBar(menuBar);
		frame.pack();
	}
	
	public JMenuBar addMenu(){
		//create MenuBar
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

	
	public void disableFrame(){
		System.out.println("disabling");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("analyze")){
			analyzeClicked();			
		}else if(cmd.equals("loadConfig")){
			ConfigFrame config = ConfigFrame.getInstance();
		}else{
			System.out.println("Katie is a nerd");
		}
	}
	
	public void analyzeClicked(){
		
		try {
			readProperties();
		} catch (IOException e1) {
			System.out.println("make sure there is a config.properties in the input_output folder");
		}
		
		DesignParser dp = new DesignParser(this.dotPath, "");
		dp.setPhases(this.parserPhases);
		dp.setOutputDir(this.outputDir);
		
		Thread resultGui = new Thread(new Runnable(){
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
	
	private void readProperties() throws IOException{
		try{
			File file = new File("resources/config.properties");
			FileInputStream fInput = new FileInputStream(file);
			Properties p = new Properties();
			
			if(fInput != null){
				p.load(fInput);
				fInput.close();
			}
			
			this.dotPath = p.getProperty("Dot-Path");
			this.inputClasses = p.getProperty("Input-Classes");
			this.outputDir = p.getProperty("Output-Directory");
			this.phases = p.getProperty("Phases");
			splitPhases();
			
			this.classes = this.inputClasses.split(",");
			for(String s: this.classes){
				s = s.trim();
			}
			
			
		} catch(Exception e){
			System.out.println("Exception: " + e);
		} 
	}
	
	private void splitPhases(){
		parserPhases = new ArrayList<String>();
		String[] phase = this.phases.split(",");
		for(String s: phase){
			s=s.trim();
			this.parserPhases.add(s);
		}
	}

}
