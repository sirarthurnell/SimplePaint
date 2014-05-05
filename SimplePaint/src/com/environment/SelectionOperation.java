package com.environment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class SelectionOperation extends ToolOperation {

	private Coordinates selectionStart;
	private Coordinates selectionEnd;
	
	public SelectionOperation(
			DrawingSurfaceConfigurationMemento configurationMemento,
			Coordinates startCoordinates,
			Coordinates endCoordinates) {
		super(configurationMemento);
		
		this.selectionStart = startCoordinates;
		this.selectionEnd = endCoordinates;
	}

	@Override
	protected void draw(DrawingSurface drawingSurface) {
		transformAndApplySelection(drawingSurface);
	}
	
	private void transformAndApplySelection(DrawingSurface drawingSurface){
				BufferedImage selectionImage = new BufferedImage(
				selectionStart.getWidth(),
				selectionStart.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics selectionGraphics = selectionImage.getGraphics();
		
		Image imageAreaSelected = drawingSurface.getSubimage(
				selectionStart.getX1(),
				selectionStart.getY1(),
				selectionStart.getWidth(),
				selectionStart.getHeight());
		
		selectionGraphics.drawImage(imageAreaSelected, 0, 0, null);
		
		selectionGraphics.dispose();
		
		Color lastColor = drawingSurface.getConfiguration().getColor();
		drawingSurface.getConfiguration().setColor(Color.white);
		drawingSurface.fillRect(
				selectionStart.getX1(),
				selectionStart.getY1(),
				selectionStart.getX2(),
				selectionStart.getY2());
		
		drawingSurface.getConfiguration().setColor(lastColor);
		
		drawingSurface.drawImageScaled(
				selectionImage,
				selectionEnd.getX1(),
				selectionEnd.getY1(),
				selectionEnd.getWidth(),
				selectionEnd.getHeight());
	}
}
