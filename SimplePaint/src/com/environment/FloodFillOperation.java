package com.environment;

import java.awt.Color;
import java.util.Stack;

public class FloodFillOperation extends ToolOperation {

	private int x;
	private int y;
	
	public FloodFillOperation(DrawingSurfaceConfigurationMemento configurationMemento, int x, int y){
		super(configurationMemento);
		this.x = x;
		this.y = y;
	}
	
	@Override
	protected void draw(DrawingSurface drawingSurface) {
		fillArea(drawingSurface, x, y);
	}
	
	private void fillArea(DrawingSurface drawingSurface, int x, int y){
		Color colorToReplace = drawingSurface.getPixelColor(x, y);
		floodFill(drawingSurface, colorToReplace, x, y);
		drawingSurface.repaint();
	}
	
	private void floodFill(DrawingSurface drawingSurface, Color colorToReplace, int x, int y){
		
		Color currentPixelColor = drawingSurface.getPixelColor(x, y);
		Pixel currentPixel = new Pixel(x, y, currentPixelColor);
		Stack<Pixel> iterativeStack = new Stack<Pixel>();
		iterativeStack.push(currentPixel);
		
		while(!iterativeStack.empty()){
			currentPixel = iterativeStack.pop();
		
			if(currentPixel.getColor().equals(colorToReplace)){
				
				drawingSurface.setPixelColor(currentPixel.getX(), currentPixel.getY());
				
				int zoomFactor = (int)drawingSurface.getZoomFactor().getFactor();
				
				//Este
				pushNewPixel(drawingSurface, currentPixel, 1 * zoomFactor, 0, iterativeStack);
				
				//Oeste
				pushNewPixel(drawingSurface, currentPixel, -1 * zoomFactor, 0, iterativeStack);
				
				//Norte
				pushNewPixel(drawingSurface, currentPixel, 0, -1 * zoomFactor, iterativeStack);

				//Sur
				pushNewPixel(drawingSurface, currentPixel, 0, 1 * zoomFactor, iterativeStack);
			}
		}
		
		iterativeStack.clear();
	}
	
	private void pushNewPixel(DrawingSurface drawingSurface, Pixel lastPixel, int xIncrement, int yIncrement, Stack<Pixel> iterativeStack){
	
		int nextX = lastPixel.getX() + xIncrement;
		int nextY = lastPixel.getY() + yIncrement;
		
		boolean isOutImagePixel = ((nextX < 0) || (nextY < 0)) || (nextX >= drawingSurface.getWidth()) || (nextY >= drawingSurface.getHeight());
		if(!isOutImagePixel){
			Color nextPixelColor = drawingSurface.getPixelColor(nextX, nextY);
			Pixel nextPixel = new Pixel(nextX, nextY, nextPixelColor);
			iterativeStack.push(nextPixel);
		}
	}
}