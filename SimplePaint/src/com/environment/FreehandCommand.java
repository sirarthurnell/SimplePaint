package com.environment;

import java.awt.Point;
import java.awt.event.*;

public class FreehandCommand implements Command {
	
	private DrawingSurface drawingSurface;
	private MouseListener mouseListener;
	private MouseMotionListener mouseMotionListener;
	private PolylineTool freehand;
	
	public FreehandCommand(DrawingSurface canvas){
		this.drawingSurface = canvas;
	}
	
	public void enable()
	{
		createEvents();
	}

	private void createEvents(){
		
		if(mouseListener == null){
			mouseListener = new MouseAdapter(){
				
				@Override
				public void mousePressed(MouseEvent e) {	
					freehand = new PolylineTool(drawingSurface, e.getX(), e.getY());
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					freehand.commit();
				}
				
			};
		}
		
		drawingSurface.addMouseListener(mouseListener);
		
		if(mouseMotionListener == null){
			mouseMotionListener = new MouseMotionAdapter(){
			
				public void mouseDragged(MouseEvent e){
					freehand.drawTo(new Point(e.getX(), e.getY()));
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
