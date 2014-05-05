package com.environment;

import java.awt.event.*;

public class FloodFillCommand implements Command {

	private DrawingSurface drawingSurface;
	private MouseListener mouseListener;
	
	public FloodFillCommand(DrawingSurface drawingSurface){
		this.drawingSurface = drawingSurface;
	}

	public void enable(){
		createEvents();
	}
	
	private void createEvents(){
		if(mouseListener == null){
			
			mouseListener = new MouseAdapter(){
				public void mousePressed(MouseEvent e) {
					int x = e.getX();
					int y = e.getY();

					FloodFillTool fillTool = new FloodFillTool(drawingSurface, x, y);
					fillTool.commit();
				}
			};
		}
		
		drawingSurface.addMouseListener(mouseListener);
	}
	
	public void disable(){
		drawingSurface.removeMouseListener(mouseListener);
	}
}
