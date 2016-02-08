package problem.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Gui{
	
	JFrame frame;
	JPanel panel;
	GuiActionListener listener;
	
	public Gui(){
		listener = GuiActionListener.getInstance();
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
		loadConfigButton.addActionListener(listener);
		JButton analyzeButton = new JButton("Analyze");
		
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
		saveItem.addActionListener(listener);
		fileMenu.add(saveItem);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}

	
	public void disableFrame(){
		System.out.println("disabling");
	}

}
