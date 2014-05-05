package com.environment;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

public class EllipseCommand implements Command{

	private DrawingSurface drawingSurface;
	private MouseListener mouseListener;
	private MouseMotionListener mouseMotionListener;
	private EllipseTool ellipse;
	
	public EllipseCommand(DrawingSurface drawingSurface){
		this.drawingSurface = drawingSurface;
	}
	
	@Override
	public void enable() {
		createEvents();
	}
	
	private void createEvents(){
		if(mouseListener == null){
			mouseListener = new MouseAdapter(){
				
				@Override
				public void mousePressed(MouseEvent e) {
					ellipse = new EllipseTool(drawingSurface, e.getX(), e.getY());					
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					ellipse.commit();
				}
				
			};
		}
		
		drawingSurface.addMouseListener(mouseListener);
		
		if(mouseMotionListener == null){
			mouseMotionListener = new MouseMotionAdapter(){
				
				@Override
				public void mouseDragged(MouseEvent e) {
					ellipse.drawToEnd(e.getX(), e.getY());
				}
				
			};
		}
		
		drawingSurface.addMouseMotionListener(mouseMotionListener);
	}

	@Override
	public void disable() {
		drawingSurface.removeMouseListener(mouseListener);
		drawingSurface.removeMouseMotionListener(mouseMotionListener);
	}	
}
