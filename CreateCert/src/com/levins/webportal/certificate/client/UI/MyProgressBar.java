package com.levins.webportal.certificate.client.UI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

class MyProgressBar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar pbar;
	private int minimum = 0;
	private int maximum = 100;

	public MyProgressBar(int min, int max) {
		this.minimum = min;
		this.maximum = max;

		JFrame frame = new JFrame("Progress Bar");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		frame.setVisible(true);
		// initialize Progress Bar
		pbar = new JProgressBar();
		pbar.setMinimum(minimum);
		pbar.setMaximum(maximum);
		// add to JPanel
		add(pbar);
		frame.setTitle("Status");
		frame.setBounds(50, 50, 100, 100);
		frame.pack();

	}

	public void updateBar(int newValue) {
		pbar.setValue(newValue);
	}

	public static void main(String args[]) {

		final MyProgressBar it = new MyProgressBar(0, 100);

		// run a loop to demonstrate raising
		for (int i = 0; i <= 100; i++) {
			final int percent = i;
			try {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						it.updateBar(percent);
					}
				});
				java.lang.Thread.sleep(10);
			} catch (InterruptedException e) {
				;
			}
		}
	}
}