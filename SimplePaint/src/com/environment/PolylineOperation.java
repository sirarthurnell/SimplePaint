package com.environment;

import java.awt.Point;

public class PolylineOperation extends ToolOperation {

	private Point[] points;
	
	public PolylineOperation(DrawingSurfaceConfigurationMemento configurationMemento, Point[] points) {
		super(configurationMemento);
		this.points = points;
	}

	@Override
	protected void draw(DrawingSurface drawingSurface) {
		drawingSurface.drawPolyline(points);		
	}

}
