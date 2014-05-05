package com.environment;

public abstract class ShapeTool implements Tool {

	private DrawingSurface drawingSurface;
	private int beginX;
	private int beginY;
	private int endX;
	private int endY;
	
	protected ShapeTool(DrawingSurface drawingSurface, int x, int y){
		this.drawingSurface = drawingSurface;
		this.beginX = x;
		this.beginY = y;
	}
	
	public void drawToEnd(int endX, int endY){
		this.endX = endX;
		this.endY = endY;
		drawingSurface.clearTopLayer();
		drawShapeOnTopLayer(drawingSurface, beginX, beginY, this.endX, this.endY);
		drawingSurface.repaint();
	}
	
	protected abstract void drawShapeOnTopLayer(DrawingSurface drawingSurface, int beginX, int beginY, int endX, int endY);
	
	public ToolOperation commit(){		
		ToolOperation memento = commitShape(drawingSurface, beginX, beginY, endX, endY);
		drawingSurface.repaint();
		return memento;
	}
	
	protected abstract ToolOperation commitShape(DrawingSurface drawingSurface, int beginX, int beginY, int endX, int endY);
}
