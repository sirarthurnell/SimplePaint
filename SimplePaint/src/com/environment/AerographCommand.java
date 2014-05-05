package com.environment;

import java.awt.event.*;

public class AerographCommand implements Command {
	
	private DrawingSurface drawingSurface;
	private MouseListener mouseListener;
	private MouseMotionListener mouseMotionListener;
	private Aerograph aerograph;
	
	public AerographCommand(DrawingSurface canvas){
		this.drawingSurface = canvas;
	}
	
	public void enable()
	{
		init();
		createEvents();
	}
	
	public void init(){
		aerograph = new Aerograph(drawingSurface);
	}

	private void createEvents(){
		
		if(mouseListener == null){
			mouseListener = new MouseAdapter(){
				
				@Override
				public void mousePressed(MouseEvent e) {
					aerograph.beginDraw(e.getX(), e.getY());					
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					aerograph.endDraw();					
				}
				
			};
		}
		
		drawingSurface.addMouseListener(mouseListener);
	
		if(mouseMotionListener == null){
			mouseMotionListener = new MouseMotionAdapter(){
			
				public void mouseDragged(MouseEvent e){
					aerograph.move(e.getX(), e.getY());
				}
				
			};
		}		
		
		drawingSurface.addMouseMotionListener(mouseMotionListener);
		
	}
	
	public void disable(){
		drawingSurface.removeMouseListener(mouseListener);
		drawingSurface.removeMouseMotionListener(mouseMotionListener);
	}
}
