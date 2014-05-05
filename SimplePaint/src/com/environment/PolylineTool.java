package com.environment;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PolylineTool implements Tool {

	private DrawingSurface drawingSurface;
	private List<Point> points;
	private Point beginPoint;
	private Point lastPoint;
	
	public PolylineTool(DrawingSurface drawingSurface, int x, int y){
		this.drawingSurface = drawingSurface;
		beginPoint = new Point(x, y);
		points = new ArrayList<Point>();
	}
	
	public void drawTo(Point newPoint){
		if(lastPoint == null){
			lastPoint = beginPoint;
		}
		
		points.add(newPoint);
		drawingSurface.drawLine(lastPoint.x, lastPoint.y, newPoint.x, newPoint.y);
		drawingSurface.repaint();
		lastPoint = newPoint;
	}
	
	public void close(){
		drawTo(beginPoint);
		drawingSurface.repaint();
	}
	
	@Override
	public ToolOperation commit() {
		Point[] pointsAsArray = (Point[])points.toArray(new Point[points.size()]);
		return new PolylineOperation(drawingSurface.getConfiguration().getMemento(), pointsAsArray);
	}

}
