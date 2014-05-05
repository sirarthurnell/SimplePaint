package com.environment;

public class Coordinates {
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	public Coordinates(){
		this(0, 0, 0, 0);
	}
	
	public Coordinates(Coordinates copyFrom){
		this(
			copyFrom.getX1(),
			copyFrom.getY1(),
			copyFrom.getX2(),
			copyFrom.getY2());
	}
	
	public Coordinates(int x1, int y1, int x2, int y2){
		setX1(x1);
		setY1(y1);
		setX2(x2);
		setY2(y2);
	}
	
	public static Coordinates createNormalized(int x1, int y1, int x2, int y2){
		return new Coordinates(
				Math.min(x1, x2),
				Math.min(y1, y2),
				Math.max(x1, x2),
				Math.max(y1, y2)
				);
	}
	
	public Coordinates normalize(){
		//Lo siguiente puede resultar algo confuso: Lo hago para que
		//las coordenadas de la esquina superior izquierda siempre sean
		//el origen de la selección, y que las coordenadas de fin sean
		//las de la esquina inferior derecha. Lo hago porque el usuario
		//puede dibujar la selección lo mismo hacia adelante que hacia
		//atrás (haciendo click en la esquina superior derecha y dibujando
		//hasta la esquina inferior izquierda), por lo que las coordenadas
		//de origen y fin no coincidirían para posteriores cálculos.
		
		return createNormalized(
				getX1(),
				getY1(),
				getX2(),
				getY2()
				);
	}
	
	public int getWidth(){
		return Math.abs(getX2() - getX1());
	}
	
	public int getHeight(){
		return Math.abs(getY2() - getY1());
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}
}
