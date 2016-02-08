package problem.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConfigFrame {
	
	private static ConfigFrame instance;
	
	GuiActionListener listener;
	
	public ConfigFrame(){
		listener = GuiActionListener.getInstance();
	}
	
	public static ConfigFrame getInstance(){
		if(instance == null){
			return new ConfigFrame();
		}
		return instance;
	}
	
	public void createConfigFrame(){
		JFrame frame = new JFrame("Config shit");
		JPanel panel = new JPanel();
		
		JButton existingButton = new JButton("Load existing config");
		existingButton.addActionListener(listener);
		existingButton.setActionCommand("loadExistingConfig");
		JButton createButton = new JButton("Create new config");
		createButton.addActionListener(listener);
		panel.add(existingButton);
		panel.add(createButton);
		
		frame.add(panel);
		
		frame.setMinimumSize(new Dimension(300, 200));
		frame.setVisible(true);
		
		
	}
	
}
