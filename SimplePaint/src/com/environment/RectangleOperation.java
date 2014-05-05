package com.environment;

public class RectangleOperation extends ShapeOperation {	

	public RectangleOperation(DrawingSurfaceConfigurationMemento configurationMemento, int beginX, int beginY, int endX, int endY){
		super(configurationMemento, beginX, beginY, endX, endY);
	}
	
	@Override
	protected void draw(DrawingSurface drawingSurface){		
		drawingSurface.drawRect(getBeginX(), getBeginY(), getEndX(), getEndY());
	}
	
}