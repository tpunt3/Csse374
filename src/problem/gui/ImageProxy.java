package problem.gui;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import problem.asm.DesignParser;

class ImageProxy implements Icon {
	ImageIcon imageIcon;
	String filePath;
	Thread retrievalThread;
	boolean retrieving = false;

	public ImageProxy(String filePath) {
		this.filePath = filePath;
	}

	public int getIconWidth() {
		if (imageIcon != null) {
			return imageIcon.getIconWidth();
		} else {
			return 1000;
		}
	}

	public void flushImageProxy() {
		if (imageIcon != null) {
			this.imageIcon.getImage().flush();
		}
	}

	public boolean isPicNull() {
		return (imageIcon == null);
	}

	public int getIconHeight() {
		if (imageIcon != null) {
			return imageIcon.getIconHeight();
		} else {
			return 600;
		}
	}

	public void paintIcon(final Component c, Graphics g, int x, int y) {
		if (imageIcon != null) {
			System.out.println("painting");
			imageIcon.paintIcon(c, g, x, y);
		} else {
			g.drawString("Loading picture, please wait...", x + 100, y + 20);
			System.out.println("Loading picture, please wait...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (!retrieving) {
				retrieving = true;

				retrievalThread = new Thread(new Runnable() {
					public void run() {
						try {
							imageIcon = new ImageIcon(filePath);

							c.revalidate();
							c.repaint();
							// c.paintAll(g);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				retrievalThread.start();
			}
		}
	}
}
