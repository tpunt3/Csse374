package problem.gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class NewConfigFrame {
	
	GuiActionListener listener;
	
	public NewConfigFrame(){
		listener = GuiActionListener.getInstance();
	}
	
	public void createNewConfig(){
		JFrame frame = new JFrame("New Config");
		frame.setMinimumSize(new Dimension(300,300));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	
		
		JLabel inputClasses = new JLabel("Input Classes: ");
		inputClasses.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel outputDir = new JLabel("Output Directory: ");
		JLabel dotPath = new JLabel("Dot Path: ");
		JLabel phases = new JLabel("Phases: ");
		
		JTextField inputClassesField = new JTextField();
		JTextField outputDirField = new JTextField();
		JTextField dotPathField = new JTextField();
		JTextField phasesField = new JTextField();
		
		JButton createConfig = new JButton("Create Config");
		createConfig.addActionListener(this.listener);
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

}
