package problem.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class ImageComponent extends JComponent {
	
	

	@Override
	public Dimension getSize() {
		return new Dimension(icon.getIconWidth(), icon.getIconHeight());
	}

	private static final long serialVersionUID = -842221943124881051L;

	private Icon icon;

	public ImageComponent(Icon icon) {
		this.icon = icon;
	}

	public void setIcon(Icon icon) {
		//this should be in a method in ImageProxy
		if(this.icon instanceof ImageProxy) {
			ImageProxy proxy = (ImageProxy)this.icon;
			proxy.flushImageProxy();
		}
		this.icon = icon;
	}
	
	public Icon getIcon(){
		return this.icon;
	}

	public void paintComponent(Graphics g) {
		int w = icon.getIconWidth();
		int h = icon.getIconHeight();
		int x = 0;
		int y = 0;
		icon.paintIcon(this, g, x, y);
	}
}
