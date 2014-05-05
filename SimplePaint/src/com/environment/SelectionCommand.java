package com.environment;

import java.awt.event.*;

public class SelectionCommand implements Command {

	private DrawingSurface drawingSurface;
	private MouseListener mouseListener;
	private Frame selectionFrame;
	
	public SelectionCommand(DrawingSurface canvas){
		this.drawingSurface = canvas;
	}
	
	public void enable(){
		createEvents();		
	}
	
	private void createEvents(){
		if(mouseListener == null){
			mouseListener = new MouseAdapter(){
			
				public void mousePressed(MouseEvent e) {
					//O se va a crear la primera selecci�n,
					//o se va a crear una nueva selecci�n.
					if((selectionFrame == null) || (selectionFrame.isContentCommited())){
						selectionFrame = new Frame(drawingSurface, e.getX(), e.getY(), new SelectionToolFactory());
					}
				}
				
			};
		}
		
		drawingSurface.addMouseListener(mouseListener);
	}
	
	public void disable(){		
		if(selectionFrame != null){
			selectionFrame.commit();
		}
		
		drawingSurface.removeMouseListener(mouseListener);	
	}	
}