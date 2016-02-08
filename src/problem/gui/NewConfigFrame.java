package problem.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class NewConfigFrame implements ActionListener{
	
	private JFrame frame;
	
	private JTextField inputClassesField;
	private JTextField outputDirField;
	private JTextField dotPathField;
	private JTextField phasesField;
	
	
	public NewConfigFrame(){
	}
	
	public void createNewConfig(){
		frame = new JFrame("New Config");
		frame.setMinimumSize(new Dimension(300,300));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel inputClasses = new JLabel("Input Classes: ");
		inputClasses.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel outputDir = new JLabel("Output Directory: ");
		JLabel dotPath = new JLabel("Dot Path: ");
		JLabel phases = new JLabel("Phases: ");
		
		inputClassesField = new JTextField();
		outputDirField = new JTextField();
		dotPathField = new JTextField();
		phasesField = new JTextField();
		
		JButton createConfig = new JButton("Create Config");
		createConfig.addActionListener(this);
		createConfig.setActionCommand("createNewConfig");
		
		panel.add(inputClasses);
		panel.add(inputClassesField);
		panel.add(outputDir);
		panel.add(outputDirField);
		panel.add(dotPath);
		panel.add(dotPathField);
		panel.add(phases);
		panel.add(phasesField);
		panel.add(createConfig);
		
		frame.setVisible(true);
		frame.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();
		
		if(cmd.equals("createNewConfig")){
			
			OurConfig userConfig = new OurConfig();
			userConfig.setInputClasses(inputClassesField.getText());
			userConfig.setOutputDir(outputDirField.getText());
			userConfig.setDotPath(dotPathField.getText());
			userConfig.setPhases(phasesField.getText());
			
			userConfig.writeProperties();
			frame.dispose();
		}
	}

}
