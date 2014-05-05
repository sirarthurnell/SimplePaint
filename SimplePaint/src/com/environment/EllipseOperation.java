package com.environment;

public class EllipseOperation extends ShapeOperation {

	public EllipseOperation(DrawingSurfaceConfigurationMemento configurationMemento, int beginX, int beginY, int endX, int endY){
		super(configurationMemento, beginX, beginY, endX, endY);
	}
	
	@Override
	protected void draw(DrawingSurface drawingSurface){
		drawingSurface.drawEllipse(getBeginX(), getBeginY(), getEndX(), getEndY());
	}
	
}