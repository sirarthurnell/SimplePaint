package com.environment;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DrawableCanvas extends Canvas {
	
	//Serial version.
	private static final long serialVersionUID = 1L;
	private DrawingSurface drawingSurface;
	private TextEditor textEditor;
	
	public DrawableCanvas(TextEditor textEditor){
		super();
		this.textEditor = textEditor;
	}
	
	public TextEditor getTextEditor(){
		return textEditor;
	}
	
	public DrawingSurface getDrawingSurface(){
		if(drawingSurface == null){
			drawingSurface = new DrawingSurface(this);
		}
		
		return drawingSurface;
	}
	
	@Override public void paint(Graphics g){
		if(drawingSurface != null){
			drawingSurface.paintImage((Graphics2D) g);
		}
	}
}
