package com.environment;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

public class CommandLayer extends Layer {
	
	private List<LayerCommand> drawingCommands;
	
	public CommandLayer(DrawingSurface drawingSurface){
		super(drawingSurface);
		this.drawingCommands = new ArrayList<LayerCommand>();
	}
	
	public void drawLine(int x1, int y1, int x2, int y2){
		LayerCommand drawLineCommand = new LineLayerCommand(x1, y1, x2, y2);
		drawingCommands.add(drawLineCommand);
	}
	
	public void drawRect(int x1, int y1, int x2, int y2){		
		LayerCommand drawRectCommand = new RectLayerCommand(x1, y1, x2, y2);
		drawingCommands.add(drawRectCommand);
	}
	
	public void drawEllipse(int x1, int y1, int x2, int y2){
		LayerCommand drawEllipseCommand = new EllipseLayerCommand(x1, y1, x2, y2);
		drawingCommands.add(drawEllipseCommand);
	}
	
	public void fillRect(int x1, int y1, int x2, int y2){		
		LayerCommand fillRectCommand = new FillRectLayerCommand(x1, y1, x2, y2);
		drawingCommands.add(fillRectCommand);
	}
	
	public void drawPolyline(Point[] points){
		LayerCommand drawPolylineCommand = new PolylineLayerCommand(points);
		drawingCommands.add(drawPolylineCommand);
	}
	
	public void drawText(String text, int x, int y){
		LayerCommand drawTextCommand = new TextLayerCommand(text, x, y);
		drawingCommands.add(drawTextCommand);
	}
	
	public void drawImage(Image image, int x, int y){
		LayerCommand drawImageCommand = new ImageLayerCommand(image, x, y);
		drawingCommands.add(drawImageCommand);
	}
	
	public void drawImageScaled(Image image, int x, int y, int width, int height){
		LayerCommand drawImageScaledCommand = new ImageScaledLayerCommand(image, x, y, width, height);
		drawingCommands.add(drawImageScaledCommand);
	}
	
	public void processDrawings(Graphics2D graphicsToPaint){
		Graphics2D graphicsForGuides = getConfiguredGraphicsCopy(graphicsToPaint);
		
		for(LayerCommand currentCommand : drawingCommands){
			currentCommand.execute(graphicsForGuides);
		}
		
		graphicsForGuides.dispose();
	}
	
	public void clearDrawings(){
		drawingCommands.clear();
	}
	
	protected Graphics2D getConfiguredGraphicsCopy(Graphics2D originalGraphics){
		DrawingSurfaceConfiguration configuration = getDrawingSurface().getConfiguration();		
		
		//We work over a copy of graphics.
		Graphics2D graphicsToPaint = (Graphics2D)originalGraphics.create();
		
		graphicsToPaint.setColor(configuration.getColor());
		
		ZoomFactor zoom = getZoom();
		Stroke newStroke = new BasicStroke((float)zoom.applyZoom(configuration.getStrokeWidth()));
		graphicsToPaint.setStroke(newStroke);
		
		Font newFont = new Font(configuration.getFontName(), Font.PLAIN, zoom.applyZoom(configuration.getFontHeight()));
		graphicsToPaint.setFont(newFont);
		
		return graphicsToPaint;
	}
	
	private abstract class ShapeLayerCommand implements LayerCommand{
		
		private int x1;
		private int y1;
		private int x2;
		private int y2;
		
		protected ShapeLayerCommand(int x1, int y1, int x2, int y2){
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
			
		public int getX1() {
			return x1;
		}

		public int getY1() {
			return y1;
		}

		public int getX2() {
			return x2;
		}

		public int getY2() {
			return y2;
		}
		
		public abstract void execute(Graphics2D graphicsToPaint);
		
	}
	
	private class LineLayerCommand extends ShapeLayerCommand{

		protected LineLayerCommand(int x1, int y1, int x2, int y2) {
			super(x1, y1, x2, y2);
		}

		@Override
		public void execute(Graphics2D graphicsToPaint) {
			drawLineWithGraphics(
					graphicsToPaint,
					getX1(),
					getY1(),
					getX2(),
					getY2());
		}
	}
	
	private class RectLayerCommand extends ShapeLayerCommand{

		protected RectLayerCommand(int x1, int y1, int x2, int y2) {
			super(x1, y1, x2, y2);
		}

		@Override
		public void execute(Graphics2D graphicsToPaint) {		
			drawRectWithGraphics(
					graphicsToPaint,
					getX1(),
					getY1(),
					getX2(),
					getY2());
		}	
	}
	
	private class EllipseLayerCommand extends ShapeLayerCommand{

		protected EllipseLayerCommand(int x1, int y1, int x2, int y2) {
			super(x1, y1, x2, y2);
		}

		@Override
		public void execute(Graphics2D graphicsToPaint) {		
			drawEllipseWithGraphics(
					graphicsToPaint,
					getX1(),
					getY1(),
					getX2(),
					getY2());
		}	
	}
	
	private class FillRectLayerCommand extends ShapeLayerCommand{

		protected FillRectLayerCommand(int x1, int y1, int x2, int y2) {
			super(x1, y1, x2, y2);
		}

		@Override
		public void execute(Graphics2D graphicsToPaint) {		
			fillRectWithGraphics(
					graphicsToPaint,
					getX1(),
					getY1(),
					getX2(),
					getY2());
		}	
	}
	
	private class PolylineLayerCommand implements LayerCommand{

		private Point[] points;
		
		public PolylineLayerCommand(Point[] points) {
			this.points = points;
		}

		@Override
		public void execute(Graphics2D graphicsToPaint) {		
			drawPolylineWithGraphics(graphicsToPaint, points);
		}	
	}
	
	private class TextLayerCommand implements LayerCommand{

		private String text;
		private int x;
		private int y;
		
		public TextLayerCommand(String text, int x, int y) {
			this.text = text;
			this.x = x;
			this.y = y;
		}

		@Override
		public void execute(Graphics2D graphicsToPaint) {			
			drawTextWithGraphics(graphicsToPaint, text, x, y);			
		}	
	}
	
	private class ImageLayerCommand implements LayerCommand{

		private Image image;
		private int x;
		private int y;
		
		public ImageLayerCommand(Image image, int x, int y) {
			this.image = image;
			this.x = x;
			this.y = y;
		}
		
		protected Image getImage(){
			return image;
		}
		
		protected int getX(){
			return x;
		}
		
		protected int getY(){
			return y;
		}

		@Override
		public void execute(Graphics2D graphicsToPaint) {		
			drawImageWithGraphics(graphicsToPaint, getImage(), getX(), getY());
		}	
	}
	
	private class ImageScaledLayerCommand extends ImageLayerCommand{

		private int width;
		private int height;
		
		public ImageScaledLayerCommand(Image image, int x, int y, int width, int height) {
			super(image, x, y);
			this.width = width;
			this.height = height;
		}

		@Override
		public void execute(Graphics2D graphicsToPaint) {		
			drawImageScaledWithGraphics(graphicsToPaint, getImage(), getX(), getY(), width, height);
		}	
	}
}
