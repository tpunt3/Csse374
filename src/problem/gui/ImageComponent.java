package problem.gui;

import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JComponent;

public class ImageComponent extends JComponent {
	private static final long serialVersionUID = -842221943124881051L;

	private Icon icon;

	public ImageComponent(Icon icon) {
		this.icon = icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public void paintComponent(Graphics g) {
		int w = icon.getIconWidth();
		int h = icon.getIconHeight();
		int x = 0;
		int y = 0;
		icon.paintIcon(this, g, x, y);
		System.out.println("paintComponent");
	}
}
