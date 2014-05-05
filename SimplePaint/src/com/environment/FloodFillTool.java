package com.environment;

public class FloodFillTool implements Tool {

	private DrawingSurface drawingSurface;
	private int x;
	private int y;
	
	public FloodFillTool(DrawingSurface drawingSurface, int x, int y){
		this.drawingSurface = drawingSurface;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public ToolOperation commit() {		
		FloodFillOperation operation = new FloodFillOperation(drawingSurface.getConfiguration().getMemento(),
				x,
				y);
		
		operation.execute(drawingSurface);
		return operation;
	}
}
