package com.environment;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public abstract class Layer {

	private DrawingSurface drawingSurface;
	
	protected Layer(DrawingSurface drawingSurface){
		this.drawingSurface = drawingSurface;
	}
	
	protected void drawEllipseWithGraphics(Graphics g, int x1, int y1, int x2, int y2){
		Coordinates ovalCoordinates = Coordinates.createNormalized(x1, y1, x2, y2);
		
		int ovalX1 = ovalCoordinates.getX1();
		int ovalY1 = ovalCoordinates.getY1();
		int ovalWidth = ovalCoordinates.getWidth();
		int ovalHeight = ovalCoordinates.getHeight();
		
		g.drawOval(
				applyZoom(ovalX1),
				applyZoom(ovalY1),
				applyZoom(ovalWidth),
				applyZoom(ovalHeight));
	}
	
	protected void fillRectWithGraphics(Graphics g, int x1, int y1, int x2, int y2){
		Coordinates rectCoordinates = Coordinates.createNormalized(x1, y1, x2, y2);
		
		int rectX1 = rectCoordinates.getX1();
		int rectY1 = rectCoordinates.getY1();
		int rectWidth = rectCoordinates.getWidth();
		int rectHeight = rectCoordinates.getHeight();
		
		g.fillRect(
				applyZoom(rectX1),
				applyZoom(rectY1),
				applyZoom(rectWidth),
				applyZoom(rectHeight));
	}
	
	protected void drawRectWithGraphics(Graphics g, int x1, int y1, int x2, int y2){
		Coordinates rectCoordinates = Coordinates.createNormalized(x1, y1, x2, y2);
		
		int rectX1 = rectCoordinates.getX1();
		int rectY1 = rectCoordinates.getY1();
		int rectWidth = rectCoordinates.getWidth();
		int rectHeight = rectCoordinates.getHeight();
		
		g.drawRect(
				applyZoom(rectX1),
				applyZoom(rectY1),
				applyZoom(rectWidth),
				applyZoom(rectHeight));
	}
	
	protected void drawLineWithGraphics(Graphics g, int x1, int y1, int x2, int y2){
		g.drawLine(
				applyZoom(x1),
				applyZoom(y1),
				applyZoom(x2),
				applyZoom(y2));
	}
	
	protected void drawPolylineWithGraphics(Graphics g, Point[] points){		
		
		int pointsCount = points.length;
		int[] zoomedX = new int[pointsCount];
		int[] zoomedY = new int[pointsCount];
		int i = 0;
		
		for(i = 0; i < pointsCount; i++){
			zoomedX[i] = applyZoom(points[i].x);
			zoomedY[i] = applyZoom(points[i].y);
		}
			
		g.drawPolyline(zoomedX, zoomedY, pointsCount);
		
	}
	
	protected void drawTextWithGraphics(Graphics g, String text, int x, int y){
		Font previousFont = g.getFont();
		float zoomedFontSize = getZoom().getFactor() * previousFont.getSize();
		Font zoomedFont = previousFont.deriveFont(zoomedFontSize);
		
		g.setFont(zoomedFont);
		g.drawString(
				text,
				applyZoom(x),
				applyZoom(y));
		
		g.setFont(previousFont);
	}
	
	protected void drawImageWithGraphics(Graphics g, Image image, int x, int y){
		g.drawImage(
				image,
				applyZoom(x),
				applyZoom(y),
				null);
	}
	
	protected void drawImageScaledWithGraphics(Graphics g, Image image, int x, int y, int width, int height){
		g.drawImage(
				image,
				applyZoom(x),
				applyZoom(y),
				width,
				height,
				null);
	}
	
	protected int applyZoom(int value){
		return getZoom().applyZoom(value);
	}
	
	protected ZoomFactor getZoom(){
		return getDrawingSurface().getZoomFactor();
	}
	
	protected int getWidth(){
		return getDrawingSurface().getWidth();
	}
	
	protected int getHeight(){
		return getDrawingSurface().getHeight();
	}
	
	protected DrawingSurface getDrawingSurface(){
		return drawingSurface;
	}
}
