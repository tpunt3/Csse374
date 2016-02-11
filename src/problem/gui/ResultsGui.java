package problem.gui;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import problem.asm.DesignParser;
import problem.asm.DocType;

public class ResultsGui implements ActionListener {
	
	JFrame frame;
	JPanel panel;
	JScrollPane pane;
	String inputClasses;
	String[] classes;
	String dotPath;
	String sdPath;
	String outputDir;
	String phases;
	ArrayList<String> parserPhases;
	ImageComponent imageComponent;
	
	public ResultsGui() throws IOException{
		this.frame = new JFrame("RESULTSTIME");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(1000, 1000));
		
		//create menu bar
		JMenuBar menuBar = addMenu();
		
		this.panel = new JPanel();
		this.panel.setPreferredSize(new Dimension(500,500));
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
		pane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.setPreferredSize(new Dimension(1000,1000));
		pane.setVisible(true);
		
		//add panel to frame
		panel.setVisible(true);		
		frame.add(pane);
		frame.setJMenuBar(menuBar);
		frame.pack();
		
		
		try {
			readProperties();
		} catch (IOException e1) {
			System.out.println("make sure there is a config.properties in the input_output folder");
		}

		DesignParser dp = new DesignParser(this.dotPath,"");
		dp.setOutputDir(outputDir);
		dp.setPhases(this.parserPhases);
		
		
		JLabel progressLabel = new JLabel("Doing stuff");
		JProgressBar jpb = new JProgressBar();
		jpb.setMaximum(this.parserPhases.size());
		jpb.setMinimum(0);
		jpb.setLocation(20, 20);
		System.out.println(this.parserPhases.size());
		panel.add(progressLabel);
		panel.add(jpb);
		panel.repaint();
		
		Icon icon = new ImageProxy(this.outputDir+"graph1.png");
		imageComponent = new ImageComponent(icon);
		imageComponent.setIcon(new ImageProxy(this.outputDir+"graph1.png"));
		panel.add(imageComponent);
		panel.repaint();
		
		frame.pack();
		try {
			dp.generateDocuments(DocType.uml, "problem.asm.DesignParser,DesignParser,generateSD,Model; ISubMethod; int", 2, classes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		panel.repaint();
		System.out.println("second repaint");
		
//		
//		Thread generateThread = new Thread(new Runnable(){
//			@Override
//			public void run() {
//				try {
//					dp.generateDocuments(DocType.uml, "problem.asm.DesignParser,DesignParser,generateSD,Model; ISubMethod; int", 2, classes);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		generateThread.start();
//		while(dp.getProgress()<this.parserPhases.size()){
//			progressLabel.setText(dp.getCurrentPhase());
//			System.out.println(dp.getProgress() + " out of " + jpb.getMaximum());
//			jpb.setValue(dp.getProgress()/this.parserPhases.size());
//			panel.repaint();
//			if(dp.getCurrentPhase().equals("finished")){
//				break;
//			}
//		}
		
		//bullshit
//		dp.generateDocuments(DocType.uml, "problem.asm.DesignParser,DesignParser,generateSD,Model; ISubMethod; int", 2, classes);
//		System.out.println("making image now");
//		BufferedImage myPic = ImageIO.read(new File(outputDir + "graph1.png"));
//		JLabel picLabel = new JLabel(new ImageIcon(myPic));
//		this.panel.add(picLabel);
//		this.panel.repaint();
//		this.panel.add(new JLabel("this project sucks ass"));
//		this.panel.repaint();
				
		frame.pack();
	}
	
	private JMenuBar addMenu(){
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

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e);
		
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
