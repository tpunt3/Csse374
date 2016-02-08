package problem.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConfigFrame implements ActionListener{
	
	private static ConfigFrame instance;
	private JFrame frame;
	
	public ConfigFrame(){
		frame = new JFrame("Config shit");
		createConfigFrame();
	}
	
	public static ConfigFrame getInstance(){
		if(instance == null){
			return new ConfigFrame();
		}
		return instance;
	}
	
	public void createConfigFrame(){
		JPanel panel = new JPanel();
		
		JButton existingButton = new JButton("Load existing config");
		existingButton.addActionListener(this);
		existingButton.setActionCommand("loadExistingConfig");
		JButton createButton = new JButton("Create new config");
		createButton.setActionCommand("createNewConfiguration");
		createButton.addActionListener(this);
		panel.add(existingButton);
		panel.add(createButton);
		
		frame.add(panel);
		
		frame.setMinimumSize(new Dimension(300, 200));
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		if(cmd.equals("createNewConfiguration")){
			NewConfigFrame newConfig = new NewConfigFrame();
			newConfig.createNewConfig();
			frame.dispose();
		}else if(cmd.equals("loadExistingConfig")){
			final JFileChooser fc = new JFileChooser();
			int retVal = fc.showDialog(frame, "Choose Config");
			
			
			if(retVal == 0){
				if(fc.getSelectedFile().getName().endsWith(".properties")){
					File propertiesFile = fc.getSelectedFile();
					
					JOptionPane.showMessageDialog(frame, "Config file chosen successfully!");
					frame.dispose();
				}else{
					JOptionPane.showMessageDialog(frame, "Please choose a file ending in .properties");
				}
			}
		}
	}
	
}
