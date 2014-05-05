package com.environment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class SelectionTool extends DrawableArea implements FrameContent {
	
	private DrawingSurface drawingSurface;	
	private BufferedImage selectionImage;
	private Frame parentFrame;
	private Coordinates startCoordinates;
	
	public SelectionTool(DrawingSurface drawingSurface){
		super();
		this.drawingSurface = drawingSurface;
	}
	
	@Override
	public void update(){
		updateCoordinates();		
		drawSelectionImageOnTopLayer();
	}
	
	@Override
	public void prepareForOperation(Frame parentFrame){
		this.parentFrame = parentFrame;
		updateCoordinates();
		startCoordinates = getCoordinatesCopy();
		
		selectionImage = new BufferedImage(
				getWidth(),
				getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics selectionGraphics = selectionImage.getGraphics();
		
		Image imageAreaSelected = drawingSurface.getSubimage(
				getX(),
				getY(),
				getWidth(),
				getHeight());
		
		selectionGraphics.drawImage(imageAreaSelected, 0, 0, null);
		
		selectionGraphics.dispose();
		
		Color lastColor = drawingSurface.getConfiguration().getColor();
		drawingSurface.getConfiguration().setColor(Color.white);
		drawingSurface.fillRect(
				getX1(),
				getY1(),
				getX2(),
				getY2());
		
		drawingSurface.getConfiguration().setColor(lastColor);
		
		drawSelectionImageOnTopLayer();
	}
	
	private void drawSelectionImageOnTopLayer(){
		drawingSurface.drawImageOnTopLayerScaled(
				selectionImage,
				getX(),
				getY(),
				getWidth(),
				getHeight());
	}
	
	private void updateCoordinates(){
		setX1(parentFrame.getX1());
		setY1(parentFrame.getY1());
		setX2(parentFrame.getX2());
		setY2(parentFrame.getY2());
	}
	
	@Override
	public ToolOperation commit(){		
		commitSelectionImageOnUserImage();		
		return createSelectionOperation();		
	}
	
	private void commitSelectionImageOnUserImage(){
		drawingSurface.drawImageScaled(
				selectionImage,
				getX(),
				getY(),
				getWidth(),
				getHeight());
		drawingSurface.repaint();
	}
	
	private SelectionOperation createSelectionOperation(){
		Coordinates endCoordinates = getCoordinatesCopy();
		SelectionOperation operation = new SelectionOperation(drawingSurface.getConfiguration().getMemento(),
				startCoordinates,
				endCoordinates);
		return operation;
	}
}