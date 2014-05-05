package com.environment;

import java.awt.Color;

public class DrawingSurfaceConfiguration {

	private DrawingSurface drawingSurface;
	private Color color;
	private int strokeWidth;
	private String fontName;
	private int fontHeight;
	
	public DrawingSurfaceConfiguration(DrawingSurface drawingSurface){
		this(drawingSurface, Color.black, 1, "Arial", 12);
	}
	
	public DrawingSurfaceConfiguration(DrawingSurface drawingSurface, Color color, int strokeWidth, String fontName, int fontHeight){
		this.drawingSurface = drawingSurface;
		this.color = color;
		this.strokeWidth = strokeWidth;
		this.fontName = fontName;
		this.fontHeight = fontHeight;
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color color){
		if(!this.color.equals(color)){
			this.color = color;
			updateSurface();
		}		
	}

	public int getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(int strokeWidth) {
		if(this.strokeWidth != strokeWidth){
			this.strokeWidth = strokeWidth;
			updateSurface();
		}
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		if(this.fontName.compareTo(fontName) != 0){
			this.fontName = fontName;
			updateSurface();
		}		
	}

	public int getFontHeight() {
		return fontHeight;
	}

	public void setFontHeight(int fontHeight) {
		if(this.fontHeight != fontHeight){
			this.fontHeight = fontHeight;
			updateSurface();
		}
	}
	
	private void updateSurface(){
		drawingSurface.updateLayersConfiguration();
	}
	
	public DrawingSurfaceConfigurationMemento getMemento(){
		return new DrawingSurfaceConfigurationMemento(color, strokeWidth, fontName, fontHeight);
	}
}
