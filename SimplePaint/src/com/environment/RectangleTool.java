package com.environment;

public class RectangleTool extends ShapeTool {

	public RectangleTool(DrawingSurface drawingSurface, int x, int y){
		super(drawingSurface, x, y);
	}	

	@Override
	protected void drawShapeOnTopLayer(DrawingSurface drawingSurface,
			int beginX, int beginY, int endX, int endY) {
		drawingSurface.drawRectOnTopLayer(beginX, beginY, endX, endY);
	}

	@Override
	protected ToolOperation commitShape(DrawingSurface drawingSurface, int beginX,
			int beginY, int endX, int endY) {
		
		RectangleOperation operation = new RectangleOperation((DrawingSurfaceConfigurationMemento)drawingSurface.getConfiguration().getMemento(),
				beginX,
				beginY,
				endX,
				endY);
		
		operation.execute(drawingSurface);
		return operation;
	}
}
