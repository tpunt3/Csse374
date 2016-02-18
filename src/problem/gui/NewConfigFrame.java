package problem.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewConfigFrame implements ActionListener{
	
	private JFrame frame;
	
	private JTextField inputClassesField;
	private JTextField outputDirField;
	private JTextField dotPathField;
	private JTextField phasesField;
	private JTextField adapterField;
	private JTextField decoratorField;
	private JTextField singletonField;
	private String files;
	
	public NewConfigFrame(){
	}
	
	public void createNewConfig(){
		frame = new JFrame("New Config");
		frame.setMinimumSize(new Dimension(500,500));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel inputClasses = new JLabel("Enter Fully Qualified Input Classes (separated by comma and no space): ");
		JLabel outputDir = new JLabel("Output Directory: ");
		JLabel dotPath = new JLabel("Dot Path: ");
		JLabel phases = new JLabel("Phases: ");
		JLabel adapter = new JLabel("Adapter-Method Delegation (integer): ");
		JLabel decorator = new JLabel("Decorator-Method Delegation (integer): ");
		JLabel singleton = new JLabel("Singleton-Require getInstance (true or false): ");
		
		inputClassesField = new JTextField();
		outputDirField = new JTextField();
		dotPathField = new JTextField();
		phasesField = new JTextField();
		adapterField = new JTextField();
		decoratorField = new JTextField();
		singletonField = new JTextField();
		
		
		JButton createConfig = new JButton("Create Config");
		createConfig.addActionListener(this);
		createConfig.setActionCommand("createNewConfig");
		
		JButton loadClasses = new JButton("Load in a folder of classes");
		loadClasses.addActionListener(this);
		loadClasses.setActionCommand("loadClasses");
		
		inputClassesField.setText("problem.sprites.AbstractSprite,problem.sprites.CircleSprite");
		outputDirField.setText("input_output/");
		dotPathField.setText("C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot");
		phasesField.setText("visit,composite,adapter,singleton,decorator,dot");
		adapterField.setText("1");
		decoratorField.setText("1");
		singletonField.setText("true");
		
		
		panel.add(inputClasses);
		panel.add(inputClassesField);
		panel.add(loadClasses);
		panel.add(outputDir);
		panel.add(outputDirField);
		panel.add(dotPath);
		panel.add(dotPathField);
		panel.add(phases);
		panel.add(phasesField);
		panel.add(adapter);
		panel.add(adapterField);
		panel.add(decorator);
		panel.add(decoratorField);
		panel.add(singleton);
		panel.add(singletonField);
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
			userConfig.setAdapterMethodDelegation(adapterField.getText());
			userConfig.setDecoratorMethodDelegation(decoratorField.getText());
			userConfig.setSingletonRequireGetInstance(singletonField.getText());
			
			userConfig.writeProperties();
			frame.dispose();
		}else if(cmd.equals("loadClasses")){
			files = "";
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("load Folder");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false);

		    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		    	File[] folder = chooser.getSelectedFile().listFiles();
		    	addFiles(folder,chooser.getSelectedFile(),"");
		    	inputClassesField.setText(this.files);
		    }
		}
	}
	
	private void addFiles(File[] folder, File oneAbove, String prefix){
		
		prefix += oneAbove.getName()+".";
				
		for(File file: folder){
			if(file.getName().endsWith(".java")){
				int dotIndex = file.getName().indexOf(".");
				String fileName = file.getName().substring(0, dotIndex);

				System.out.println(prefix+fileName+",");
				this.files+= prefix+fileName+",";
			}
			if(file.isDirectory()){
				addFiles(file.listFiles(),file,prefix);
			}
		}
	}

}
