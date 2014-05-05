package com.environment;

import java.awt.Rectangle;

public abstract class DrawableArea {

	private Coordinates limits;
	
	public DrawableArea(){
		limits = new Coordinates();
	}
	
	public boolean areCoordinatesInside(int x, int y){
		Coordinates normalizedLimits = limits.normalize();
		
		boolean areCoordinatesInsideSelectionXCoordinates = 
				(normalizedLimits.getX1() <= x) && (x <= normalizedLimits.getX2()); 
		
		boolean areCoordinatesInsideSelectionYCoordinates = 
				(normalizedLimits.getY1() <= y) && (y <= normalizedLimits.getY2());
		
		boolean coordinatesInsideSelection = 
				areCoordinatesInsideSelectionXCoordinates && areCoordinatesInsideSelectionYCoordinates;
		
		return coordinatesInsideSelection;
	}
	
	public Coordinates getNormalizedCoordinates(){	
		return limits.normalize();
	}
	
	public int getWidth(){
		return limits.getWidth();
	}
	
	public int getHeight(){
		return limits.getHeight();
	}
	
	public Rectangle getRect(){
		return new Rectangle(getX1(), getY1(), getWidth(), getHeight());
	}
	
	public int getX(){
		return getX1();
	}
	
	public int getY(){
		return getY1();
	}
	
	protected int getX1() {
		return limits.getX1();
	}
	
	protected void setX1(int x1) {
		limits.setX1(x1);
	}
	
	protected int getY1() {
		return limits.getY1();
	}
	
	protected void setY1(int y1) {
		limits.setY1(y1);
	}
	
	protected int getX2() {
		return limits.getX2();
	}
	
	protected void setX2(int x2) {
		limits.setX2(x2);
	}
	
	protected int getY2() {
		return limits.getY2();
	}
	
	protected void setY2(int y2) {
		limits.setY2(y2);
	}
	
	protected Coordinates getCoordinatesCopy(){
		return new Coordinates(
				getX1(),
				getY1(),
				getX2(),
				getY2());
	}
}
