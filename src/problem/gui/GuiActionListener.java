package problem.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiActionListener implements ActionListener {
	
	private static GuiActionListener listener;
	
	private GuiActionListener(){
		
	}
	
	public static GuiActionListener getInstance(){
		if(listener == null){
			return new GuiActionListener();
		}
		return listener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		if(cmd.equals("save")){
		}else if(cmd.equals("loadConfig")){
			ConfigFrame config = ConfigFrame.getInstance();
			config.createConfigFrame();
		}else if(cmd.equals("loadExistingConfig")){
			NewConfigFrame newConfig = new NewConfigFrame();
			newConfig.createNewConfig();
		}
		else if(cmd.equals("loadExistingConfig")){
			newConfig.createNewConfig();
		}
		
	}

}
