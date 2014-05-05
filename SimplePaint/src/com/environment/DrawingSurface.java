package com.environment;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class DrawingSurface {
	
	private DrawingSurfaceConfiguration configuration;
	private BitmapLayer userLayer;
	private CommandLayer topLayer;
	private BufferedImage layerFusionImage;
	private GuidesOverlay guides;
	private DrawableCanvas canvas;
	private ZoomFactor zoom;
	private int width;
	private int height;

	public DrawingSurface(DrawableCanvas canvas){
		this.canvas = canvas;
		width = canvas.getWidth();
		height = canvas.getHeight();
		zoom = new ZoomFactor(2);
		topLayer = new CommandLayer(this);
		userLayer = new BitmapLayer(this);
		userLayer.activate();
		guides = new GuidesOverlay(this);
		configuration = new DrawingSurfaceConfiguration(this);
		updateLayersConfiguration();
	}

	public DrawingSurfaceConfiguration getConfiguration(){
		return configuration;
	}
	
	public ZoomFactor getZoomFactor(){
		return zoom;
	}
	
	public void setZoomFactor(ZoomFactor zoom){
		this.zoom = zoom;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void drawEllipseOnTopLayer(int x1, int y1, int x2, int y2){
		topLayer.drawEllipse(x1, y1, x2, y2);
	}
	
	public void drawEllipse(int x1, int y1, int x2, int y2){
		userLayer.drawEllipse(x1, y1, x2, y2);
	}
	
	public void fillRectOnTopLayer(int x1, int y1, int x2, int y2){
		topLayer.fillRect(x1, y1, x2, y2);
	}
	
	public void fillRect(int x1, int y1, int x2, int y2){
		userLayer.fillRect(x1, y1, x2, y2);
	}
	
	public void drawRectGuide(int x1, int y1, int x2, int y2){
		guides.drawRect(x1, y1, x2, y2);
	}
	
	public void drawRectOnTopLayer(int x1, int y1, int x2, int y2){
		topLayer.drawRect(x1, y1, x2, y2);
	}

	public void drawRect(int x1, int y1, int x2, int y2){
		userLayer.drawRect(x1, y1, x2, y2);
	}
	
	public void drawLineGuide(int x1, int y1, int x2, int y2){
		guides.drawLine(x1, y1, x2, y2);
	}
	
	public void drawLineOnTopLayer(int x1, int y1, int x2, int y2){
		topLayer.drawLine(x1, y1, x2, y2);
	}
	
	public void drawLine(int x1, int y1, int x2, int y2){
		userLayer.drawLine(x1, y1, x2, y2);
	}
	
	public void drawPolylineOnTopLayer(Point[] points){
		topLayer.drawPolyline(points);
	}
	
	public void drawPolyline(Point[] points){
		userLayer.drawPolyline(points);
	}
	
	public void drawTextOnTopLayer(String text, int x, int y){
		topLayer.drawText(
				text,
				x,
				y);		
	}
	
	public void drawText(String text, int x, int y){
		userLayer.drawText(
				text,
				x,
				y);
	}
	
	public void drawImageOnTopLayer(Image image, int x, int y){
		topLayer.drawImage(
				image,
				x,
				y);
	}
	
	public void drawImage(Image image, int x, int y){
		userLayer.drawImage(
				image,
				x,
				y);
	}
	
	public void drawImageOnTopLayerScaled(Image image, int x, int y, int width, int height){
		topLayer.drawImageScaled(
				image,
				x,
				y,
				width,
				height);
	}
	
	public void drawImageScaled(Image image, int x, int y, int width, int height){
		userLayer.drawImageScaled(
				image,
				x,
				y,
				width,
				height);
	}
	
	public TextEditor getTextEditor(){
		TextEditor editor = canvas.getTextEditor();
		
		Font zoomedFont = new Font(configuration.getFontName(),
				Font.PLAIN, (int)(zoom.getFactor() * configuration.getFontHeight()));
		editor.setFont(zoomedFont);
		
		return editor;
	}
	
	public Image getSubimage(int x, int y, int width, int height){
		return userLayer.getSubimage(
				x,
				y,
				width,
				height);
	}
	
	public void erase(){
		userLayer.erase();		
		repaint();
	}
	
	public Color getPixelColor(int x, int y){
		return userLayer.getPixelColor(x, y);
	}
	
	public void setPixelColor(int x, int y){
		userLayer.setPixelColor(x, y);
	}

	public void updateLayersConfiguration(){
		userLayer.updateConfiguration();
	}
	
	public void addMouseListener(MouseListener l){
		canvas.addMouseListener(l);
	}
	
	public void addMouseMotionListener(MouseMotionListener l){
		canvas.addMouseMotionListener(l);
	}
	
	public void addKeyListener(KeyListener l){
		canvas.addKeyListener(l);
	}
	
	public void removeMouseListener(MouseListener l){
		canvas.removeMouseListener(l);
	}
	
	public void removeMouseMotionListener(MouseMotionListener l){
		canvas.removeMouseMotionListener(l);
	}
	
	public void removeKeyListener(KeyListener l){
		canvas.removeKeyListener(l);
	}
	
	public void save(String path) throws IOException{
		File outputfile = new File(path);
		ImageIO.write(userLayer.getImage(), "png", outputfile);
	}
	
	public void repaint(){
		canvas.repaint();
	}
	
	public void clearTopLayer(){
		topLayer.clearDrawings();
	}
	
	public void clearGuides(){
		guides.clearDrawings();
	}
	
	public void clearTopLayerAndGuides(){
		clearTopLayer();
		clearGuides();
	}
	
	public void paintImage(Graphics g){
		flatternLayers(g);
		paintImageWithZoom(g);
	}
	
	private void flatternLayers(Graphics g){
		layerFusionImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D fusionGraphics = (Graphics2D)layerFusionImage.getGraphics();
		
		userLayer.paintLayerOn(fusionGraphics);
		topLayer.processDrawings((Graphics2D) fusionGraphics);
		
		fusionGraphics.dispose();
	}
	
	private void paintImageWithZoom(Graphics g){
		BufferedImage layerFusionImageWithGuides = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D fusionWithGuidesGraphics = (Graphics2D)layerFusionImageWithGuides.getGraphics();
		
		float zoomFactor = getZoomFactor().getFactor();
		fusionWithGuidesGraphics.drawImage(layerFusionImage, 0, 0, (int)(width * zoomFactor), (int)(height * zoomFactor), null);
		
		//Dibujamos las guías que hubiera.
		guides.processDrawings((Graphics2D) fusionWithGuidesGraphics);
		fusionWithGuidesGraphics.dispose();
		
		g.drawImage(layerFusionImageWithGuides, 0, 0, null);
	}	
}