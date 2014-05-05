package com.environment;

import java.awt.event.*;

public class TextCommand implements Command {

	private DrawingSurface drawingSurface;
	private MouseListener mouseListener;
	private Frame textFrame;
	
	public TextCommand(DrawingSurface canvas){
		this.drawingSurface = canvas;
	}
	
	public void enable(){
		createEvents();		
	}
	
	private void createEvents(){
		if(mouseListener == null){
			mouseListener = new MouseAdapter(){
			
				public void mousePressed(MouseEvent e) {
					//O se va a crear el primer texto,
					//o se va a crear un texto nuevo.
					if((textFrame == null) || (textFrame.isContentCommited())){
						textFrame = new Frame(drawingSurface, e.getX(), e.getY(), new TextToolFactory());
					}
				}
				
			};
		}
		
		drawingSurface.addMouseListener(mouseListener);
	}
	
	public void disable(){		
		if(textFrame != null){
			textFrame.commit();
		}
		
		drawingSurface.removeMouseListener(mouseListener);	
	}	
}