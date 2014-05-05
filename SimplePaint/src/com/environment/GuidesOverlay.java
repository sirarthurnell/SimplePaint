package com.environment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class GuidesOverlay extends CommandLayer {

	private final ZoomFactor entityZoom;
	
	public GuidesOverlay(DrawingSurface drawingSurface){
		super(drawingSurface);
		entityZoom = new ZoomFactor(1);
	}
	
	@Override
	protected Graphics2D getConfiguredGraphicsCopy(Graphics2D originalGraphics){
		//Trabajamos sobre una copia de graphics.
		Graphics2D graphicsToPaint = (Graphics2D)originalGraphics.create();
		
		graphicsToPaint.setColor(Color.red);		

		Stroke newStroke = new BasicStroke(1);
		graphicsToPaint.setStroke(newStroke);
		
		return graphicsToPaint;
	}
	
	@Override
	protected ZoomFactor getZoom(){
		return entityZoom;
	}
}
