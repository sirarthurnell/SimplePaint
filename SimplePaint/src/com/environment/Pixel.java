package com.environment;

import java.awt.Color;

public class Pixel {
	
	private int x;
	private int y;
	private Color color;
	
	public Pixel(){
		this(0,0);
	}
	
	public Pixel(int x, int y){
		this.setX(x);
		this.setY(y);
		this.setColor(Color.white);
	}
	
	public Pixel(int x, int y, Color color){
		this.setX(x);
		this.setY(y);
		this.setColor(color);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		if(x < 0){
			throw new IllegalArgumentException("La coordenada X del píxel no puede ser negativa.");
		}
		
		this.x = x;
	}

	public int getY() {		
		return y;
	}

	public void setY(int y) {
		if(y < 0){
			throw new IllegalArgumentException("La coordenada Y del píxel no puede ser negativa.");
		}
		
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		if(color == null){
			throw new IllegalArgumentException("El color del píxel no puede ser nulo.");
		}
		
		this.color = color;
	}
	
	@Override public String toString(){
		return "Pixel at (" + getX() + "," + getY() + ") of color: " + getColor().toString();
	}
}
