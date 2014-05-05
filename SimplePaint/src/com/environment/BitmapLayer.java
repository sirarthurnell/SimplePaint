package com.environment;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BitmapLayer extends Layer {
	
	private BufferedImage layerImage;
	private Graphics2D layerGraphics;
	private boolean activated;

	public BitmapLayer(DrawingSurface drawingSurface){
		super(drawingSurface);
		layerImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		activated = false;
	}
	
	protected Graphics2D getLayerGraphics(){
		if(!isActive()){
			throw new IllegalStateException("Debe activar primero la capa antes de poder dibujar en ella.");
		}
		
		return layerGraphics;
	}
	
	BufferedImage getImage(){
		return layerImage;
	}
	
	public void activate(){
		if((layerGraphics == null) && (!activated)){
			layerGraphics = (Graphics2D)layerImage.getGraphics();
			updateConfiguration();
			activated = true;
		}
	}
	
	public boolean isActive(){
		return activated;
	}
	
	public void dispose(){
		if((layerGraphics != null) && activated){
			layerGraphics.dispose();
			layerGraphics = null;
			activated = false;
		}
	}
	
	public void drawEllipse(int x1, int y1, int x2, int y2){
		drawEllipseWithGraphics(getLayerGraphics(), x1, y1, x2, y2);
	}
	
	public void fillRect(int x1, int y1, int x2, int y2){
		fillRectWithGraphics(getLayerGraphics(), x1, y1, x2, y2);
	}

	public void drawRect(int x1, int y1, int x2, int y2){
		drawRectWithGraphics(getLayerGraphics(), x1, y1, x2, y2);
	}
	
	public void drawLine(int x1, int y1, int x2, int y2){
		drawLineWithGraphics(getLayerGraphics(), x1, y1, x2, y2);
	}
	
	public void drawPolyline(Point[] points){		
		drawPolylineWithGraphics(getLayerGraphics(), points);
	}
	
	public void drawText(String text, int x, int y){
		drawTextWithGraphics(getLayerGraphics(), text, x, y);
	}
	
	public void drawImage(Image image, int x, int y){
		drawImageWithGraphics(getLayerGraphics(), image, x, y);
	}
	
	public void drawImageScaled(Image image, int x, int y, int width, int height){
		drawImageScaledWithGraphics(getLayerGraphics(), image, x, y, width, height);
	}
	
	public void drawLayer(BitmapLayer layerToDraw, int x, int y){
		drawImage(layerToDraw.layerImage, x, y);
	}
	
	public Image getSubimage(int x, int y, int width, int height){
		Image subImage = layerImage.getSubimage(
				applyZoom(x),
				applyZoom(y),
				applyZoom(width),
				applyZoom(height));
		
		return subImage;
	}

	public void erase(){
		Color currentColor = getDrawingSurface().getConfiguration().getColor();
		
		getLayerGraphics().setColor(Color.white);
		getLayerGraphics().fillRect(0, 0, getWidth(), getHeight());
		getLayerGraphics().setColor(currentColor);
	}
	
	public void updateConfiguration(){
		if(isActive()){
			DrawingSurfaceConfiguration configuration = getDrawingSurface().getConfiguration();
			
			getLayerGraphics().setColor(configuration.getColor());
			
			Stroke newStroke = new BasicStroke((float)applyZoom(configuration.getStrokeWidth()));
			getLayerGraphics().setStroke(newStroke);
			
			Font newFont = new Font(configuration.getFontName(), Font.PLAIN, applyZoom(configuration.getFontHeight()));
			getLayerGraphics().setFont(newFont);
		}
	}
	
	public Color getPixelColor(int x, int y){
		return new Color(layerImage.getRGB(
				applyZoom(x),
				applyZoom(y)));
	}
	
	public void setPixelColor(int x, int y){
		layerImage.setRGB(
				applyZoom(x),
				applyZoom(y), 
				getDrawingSurface().getConfiguration().getColor().getRGB());
	}
	
	public void paintLayerOn(Graphics g){
		g.drawImage(layerImage, 0, 0, getWidth(), getHeight(), null);
	}
}