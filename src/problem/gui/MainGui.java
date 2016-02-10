package problem.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
	
	
	public MainGui(){
	}
	
	public void createLandingScreen(){
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
			Thread resultGui = new Thread(new Runnable(){
				@Override
				public void run() {
					ResultsGui results = new ResultsGui();
				}
			});
			resultGui.start();
			this.frame.dispose();
			
		}else if(cmd.equals("loadConfig")){
			ConfigFrame config = ConfigFrame.getInstance();
		}else{
			System.out.println("Katie is a nerd");
		}
	}

}
