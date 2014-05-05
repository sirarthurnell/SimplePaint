package com.environment;

public abstract class ShapeOperation extends ToolOperation {
	
	private int beginX;
	private int beginY;
	private int endX;
	private int endY;
	
	public ShapeOperation(DrawingSurfaceConfigurationMemento configurationMemento, int beginX, int beginY, int endX, int endY){
		super(configurationMemento);
		this.beginX = beginX;
		this.beginY = beginY;
		this.endX = endX;
		this.endY = endY;
	}

	public int getBeginX() {
		return beginX;
	}

	public int getBeginY() {
		return beginY;
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}
}
