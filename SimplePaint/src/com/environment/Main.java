package com.environment;

import javax.swing.*;

public class Main {
	
	/**
	 * Punto de entrada del programa.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			
	    } catch (Exception e) {
	    	System.out.println("Fallo al establecer el look and feel.");
	    }
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new MainWindow().setVisible(true);
			}
		});
	}
}
