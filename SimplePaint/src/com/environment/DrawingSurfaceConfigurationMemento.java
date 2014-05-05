package com.environment;

import java.awt.Color;

public class DrawingSurfaceConfigurationMemento {

	private Color color;
	private int strokeWidth;
	private String fontName;
	private int fontHeight;	
	
	public DrawingSurfaceConfigurationMemento(Color color, int strokeWidth,
			String fontName, int fontHeight) {
		this.color = color;
		this.strokeWidth = strokeWidth;
		this.fontName = fontName;
		this.fontHeight = fontHeight;
	}


	public void restore(DrawingSurface drawingSurface) {
		DrawingSurfaceConfiguration configuration = drawingSurface.getConfiguration();
		configuration.setColor(color);
		configuration.setStrokeWidth(strokeWidth);
		configuration.setFontName(fontName);
		configuration.setFontHeight(fontHeight);
	}
	
}
