package com.environment;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

public class Aerograph {

	private DrawingSurface drawingSurface;
	private Random random;
	private int radius;
	private int delayMs;
	private int pointsPerSample;	
	private int x;
	private int y;
	private Object syncObj;
	private boolean enableDraw;
	
	public Aerograph(DrawingSurface drawingSurface){
		this.drawingSurface = drawingSurface;
		radius = 10;
		delayMs = 50;
		pointsPerSample = 20;
		random = new Random();
		syncObj = new Object();
	}
	
	public int getRadius(){
		synchronized(syncObj){
			return radius;
		}		
	}
	
	public void setRadius(int radius){
		synchronized(syncObj){
			this.radius = radius;
		}		
	}
	
	public int getDelay(){
		synchronized(syncObj){
			return delayMs;
		}		
	}
	
	public void setDelay(int delayMs){
		synchronized(syncObj){
			this.delayMs = delayMs;
		}		
	}
	
	public int getPointsPerSample(){
		synchronized(syncObj){
			return pointsPerSample;
		}
	}
	
	public void setPointsPerSample(int pointsPerSample){
		synchronized(syncObj){
			this.pointsPerSample = pointsPerSample;
		}		
	}
	
	public void beginDraw(int x, int y){
		synchronized(syncObj){
			enableDraw = true;
			this.x = x;
			this.y = y;
		}
		
		Thread drawingThread = new Thread(new Runnable(){
			@Override
			public void run() {				
				drawingThreadBody();
			}
		});
		
		drawingThread.setDaemon(true);
		drawingThread.start();
	}
	
	public void drawingThreadBody() {
		
		boolean enableDrawCopy = true;
		
		while(enableDrawCopy){
		
			synchronized(syncObj){
				enableDrawCopy = enableDraw;
			}
			
			SwingUtilities.invokeLater(new Runnable(){

				@Override
				public void run() {
					draw();			
				}
				
			});
			
			try{
				Thread.sleep(getDelay());
			}catch(Exception ex){}
			
		}

	}
	
	public void move(int x, int y){
		synchronized(syncObj){
			this.x = x;
			this.y = y;
		}
	}
	
	public void endDraw(){
		synchronized(syncObj){
			enableDraw = false;
		}
	}
	
	public void draw(){			
		List<Point> pointsToDraw;
		
		synchronized(syncObj){
			pointsToDraw = getPointsInArea(x, y);
		}			

		for(Point currentPoint: pointsToDraw){
			drawingSurface.drawRect(
			(int)currentPoint.getX(),
			(int)currentPoint.getY(),
			(int)currentPoint.getX() + 1,
			(int)currentPoint.getY() + 1);
		}
		
		drawingSurface.repaint();
	}
	
	private List<Point> getPointsInArea(int centerX, int centerY){
		List<Point> points = new ArrayList<Point>(pointsPerSample);
		Point randomPoint;		
		
		for(int i = 0; i < getPointsPerSample(); i++){
			randomPoint = getRandomCoordinateInRadius(centerX, centerY);
			points.add(randomPoint);
		}
		
		return points;
	}
	
	private Point getRandomCoordinateInRadius(int centerX, int centerY){
		//Obtenemos un punto aleatorio en el radio de la
		//circunferencia que es la anchura del aerógrafo.
		//Ecuación:
		//x = a + r cos(deltha)
		//y = b + r sin(deltha)
		
		int deltha = random.nextInt((int)(16 * Math.PI)); //El 16 serían radianes, pero me lo he inventado.
		
		int randomCoordinate = random.nextInt(getRadius());
		int randomX = centerX + (int)(randomCoordinate * Math.cos(deltha));
		int randomY = centerY + (int)(randomCoordinate * Math.sin(deltha));
		
		return new Point(randomX, randomY);
	}
}
