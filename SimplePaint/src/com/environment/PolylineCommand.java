package com.environment;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PolylineCommand implements Command{

	private DrawingSurface drawingSurface;
	private MouseListener mouseListener;
	private PolylineTool polyline;
	
	public PolylineCommand(DrawingSurface drawingSurface){
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
					if(polyline == null){
						polyline = new PolylineTool(drawingSurface, e.getX(), e.getY());
					}
					polyline.drawTo(new Point(e.getX(), e.getY()));
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					polyline.commit();
				}
				
			};
		}
		
		drawingSurface.addMouseListener(mouseListener);
		
	}

	@Override
	public void disable() {
		drawingSurface.removeMouseListener(mouseListener);
	}
}
