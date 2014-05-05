package com.environment;

public class EllipseTool extends ShapeTool{

	public EllipseTool(DrawingSurface drawingSurface, int x, int y){
		super(drawingSurface, x, y);
	}	

	@Override
	protected void drawShapeOnTopLayer(DrawingSurface drawingSurface,
			int beginX, int beginY, int endX, int endY) {
		drawingSurface.drawEllipseOnTopLayer(beginX, beginY, endX, endY);		
	}

	@Override
	protected ToolOperation commitShape(DrawingSurface drawingSurface, int beginX,
			int beginY, int endX, int endY) {
		
		EllipseOperation operation = new EllipseOperation((DrawingSurfaceConfigurationMemento)drawingSurface.getConfiguration().getMemento(),
				beginX,
				beginY,
				endX,
				endY);
		
		operation.execute(drawingSurface);
		return operation;
	}
}