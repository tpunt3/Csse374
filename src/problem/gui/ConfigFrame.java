package problem.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConfigFrame implements ActionListener{
	
	private static ConfigFrame instance;
	private JFrame frame;
	private String dotPath;
	private String inputClasses;
	private String outputDir;
	private String phases;
	private String[] classes;
	private ArrayList<String> parserPhases;
	
	public ConfigFrame(){
		frame = new JFrame("Configurations");
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
					
					try {
						copyToOurConfig(propertiesFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(frame, "Config file chosen successfully!");
					frame.dispose();
				}else{
					JOptionPane.showMessageDialog(frame, "Please choose a file ending in .properties");
				}
			}
		}
	}
	
	public void copyToOurConfig(File f) throws IOException{
		readProperties(f);
		writeProperties();
	}
	
	private void writeProperties() {
		OurConfig oc = new OurConfig();
		oc.setInputClasses(inputClasses);
		oc.setOutputDir(outputDir);
		oc.setDotPath(dotPath);
		oc.setPhases(phases);
		oc.writeProperties();
	}

	private void readProperties(File f) throws IOException{
		try{
			FileInputStream fInput = new FileInputStream(f);
			Properties p = new Properties();
			
			if(fInput != null){
				p.load(fInput);
				fInput.close();
			}
			
			this.dotPath = p.getProperty("Dot-Path");
			this.inputClasses = p.getProperty("Input-Classes");
			this.outputDir = p.getProperty("Output-Directory");
			this.phases = p.getProperty("Phases");
			
			this.classes = this.inputClasses.split(",");
			for(String s: this.classes){
				s = s.trim();
			}
			
			
		} catch(Exception e){
			System.out.println("Exception: " + e);
		} 
	}
	
}
