package com.environment;

public class ZoomFactor {
	
	private float factor;
	
	public ZoomFactor(float factor){
		if(factor < 1){
			throw new IllegalArgumentException("El factor de zoom no puede ser igual o menor que 1");
		}
		
		this.factor = factor;
	}
	
	public float getFactor() {
		return factor;
	}

	public int applyZoom(int value){
		return (int)(value / getFactor());
	}
	
}
