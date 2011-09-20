package ca.vaskor.terry.fractalmusic.ui;

import javax.swing.JApplet;

public class MainGuiApplet extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5123216541225122959L;

	public void init() {
		this.setContentPane(new MainPanel());
	}
/*
	public void paint(Graphics g) {
		this.setContentPane(new MainPanel());
	}
*/
}
