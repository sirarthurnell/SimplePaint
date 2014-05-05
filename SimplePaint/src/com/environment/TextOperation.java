package com.environment;

public class TextOperation extends ToolOperation {

	private String textToDraw;
	private int lineHeight;
	private Coordinates textPlace;
	
	public TextOperation(
			DrawingSurfaceConfigurationMemento configurationMemento,
			String textToDraw,
			int lineHeight,
			Coordinates textPlace) {
		super(configurationMemento);
		
		this.textToDraw = textToDraw;
		this.textPlace = textPlace;
		this.lineHeight = lineHeight;
	}

	@Override
	protected void draw(DrawingSurface drawingSurface) {
		String[] linesToDraw = textToDraw.split("\n");
		
		for(int i = 1; i <= linesToDraw.length; i++){
			drawingSurface.drawText(linesToDraw[i - 1],
					textPlace.getX1(),
					textPlace.getY1() + lineHeight * i);
		}		
	}
}
