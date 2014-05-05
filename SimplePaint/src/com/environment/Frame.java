package com.environment;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class Frame extends DrawableArea implements Tool {
	
	private final static int ANCHOR_SIZE = 10;
	private final static int MINIMUM_SIZE = 10;
	private final static int MINIMUM_START_SIZE = 10;
	
	private DrawingSurface drawingSurface;
	private FrameContentFactory contentFactory;
	private FrameContent content;
	private List<MouseListener> mouseListeners;
	private List<MouseMotionListener> mouseMotionListeners;
	private int delayXForMove;
	private int delayYForMove;
	private FrameState state;
	private List<Anchor> anchors;
	private boolean contentCommited;
	private ToolOperation frameOperation;

	public Frame(DrawingSurface drawingSurface, int x, int y, FrameContentFactory contentFactory){
		this.drawingSurface = drawingSurface;
		setX1(x);
		setY1(y);
		this.contentFactory = contentFactory;		
		mouseListeners = new ArrayList<MouseListener>();
		mouseMotionListeners = new ArrayList<MouseMotionListener>();
		anchors = new ArrayList<Anchor>(8);
		contentCommited = false;
		state = new FrameStateBeforeDrawing();
		state.setup();
	}
	
	public class FrameStateBeforeDrawing implements FrameState {

		private MouseListener ownMouseListener;
		private MouseMotionListener ownMouseMotionListener;
		
		@Override
		public void next(FrameState state)  {
			disableEvents();
			state.setup();
		}
		
		public void disableEvents(){
			drawingSurface.removeMouseListener(ownMouseListener);
			drawingSurface.removeMouseMotionListener(ownMouseMotionListener);
		}

		@Override
		public void setup() {
			enableEvents();
		}
		
		public void enableEvents(){
			
			if(ownMouseListener == null){
				ownMouseListener = new MouseAdapter(){			
					
					@Override
					public void mouseReleased(MouseEvent e) {
												
						Coordinates normalized = getNormalizedCoordinates();
						
						content = contentFactory.createContent(drawingSurface);
						
						//Si es demasiado pequeño el frame creado por el usuario,
						//le damos un tamaño mínimo.
						boolean isCreatedTooSmall = (getX2() < MINIMUM_START_SIZE) || (getY2() < MINIMUM_START_SIZE);
						if(isCreatedTooSmall){
							updateCoordinatesToMinimumSize();
						}else{
							//Normalizamos el frame.
							updateCoordinates(
								normalized.getX1(),
								normalized.getY1(),
								normalized.getX2(),
								normalized.getY2());
						}
						
						drawingSurface.clearTopLayerAndGuides();
			
						prepareContentForOperation();						
						createAnchors();						
						refresh();
								
						next(new FrameStateDrawed());
					}
				};
			}
			
			drawingSurface.addMouseListener(ownMouseListener);
			
			if(ownMouseMotionListener == null){
				ownMouseMotionListener = new MouseMotionAdapter(){

					@Override
					public void mouseDragged(MouseEvent e) {
							
						//El dibujado del frame se hace fuera del frame
						//(aún no existe a efectos de coordenadas en el
						//drawingSurface).
						drawFrameAndRefresh(e.getX(), e.getY());					
					}
					
				};
			}
			
			drawingSurface.addMouseMotionListener(ownMouseMotionListener);
		}
	}
	
	public class FrameStateDrawed implements FrameState {

		private MouseListener ownMouseListener;
		private MouseMotionListener ownMouseMotionListener;
		
		@Override
		public void next(FrameState state) {
			disableEvents();
			state.setup();
		}
		
		public void disableEvents(){
			drawingSurface.removeMouseListener(ownMouseListener);
			drawingSurface.removeMouseMotionListener(ownMouseMotionListener);
		}

		@Override
		public void setup() {
			enableEvents();		
		}

		public void enableEvents(){
			
			if(ownMouseListener == null){
				ownMouseListener = new MouseAdapter(){
					
					@Override
					public void mousePressed(MouseEvent e) {
						
						for(MouseListener l: mouseListeners){
							l.mousePressed(e);
						}
						
						if(!e.isConsumed()){
							
							if(areEventCoordinatesInside(e)){
								
								delayXForMove = e.getX() - getX();
								delayYForMove = e.getY() - getY();							
								
							}else{
								//Clicked outside frame.							
								next(new FrameStateCommited());						
							}
							
						}
						
					}
					
					@Override
					public void mouseReleased(MouseEvent e) {
						
						//Aunque parece no usarse aquí, se han de propagar
						//los eventos para que los anclajes se enteren.
						for(MouseListener l: mouseListeners){
							l.mouseReleased(e);
						}
						
					}
				};
			}
			
			drawingSurface.addMouseListener(ownMouseListener);
			
			if(ownMouseMotionListener == null){
				ownMouseMotionListener = new MouseMotionAdapter(){

					@Override
					public void mouseDragged(MouseEvent e) {
						
						for(MouseMotionListener l: mouseMotionListeners){
							l.mouseDragged(e);
						}
						
						if(!e.isConsumed()){
							
							if(areEventCoordinatesInside(e)){
								
								int correctedX = e.getX() - delayXForMove;
								int correctedY = e.getY() - delayYForMove;
								
								move(correctedX, correctedY);
								
							}
							
						}
						
					}
				};
			}
			
			drawingSurface.addMouseMotionListener(ownMouseMotionListener);
		}
	}
	
	public class FrameStateCommited implements FrameState {

		@Override
		public void next(FrameState state) {
			throw new UnsupportedOperationException("Éste es un estado final de Frame.");
		}

		@Override
		public void setup() {
			disableEvents();
			applyContent();
		}
		
		public void disableEvents(){
			for(Anchor currentAnchor: anchors){
				currentAnchor.disableEvents();
			}
		}
	}
	
	private boolean areEventCoordinatesInside(MouseEvent e){
		Coordinates normalized = new Coordinates(
				getX1() - ANCHOR_SIZE,
				getY1() - ANCHOR_SIZE,
				getX2() + ANCHOR_SIZE,
				getY2() + ANCHOR_SIZE);
		normalized = normalized.normalize();
		
		boolean areCoordinatesInsideSelectionXCoordinates = 
				(normalized.getX1() <= e.getX()) && (e.getX() <= normalized.getX2()); 
		
		boolean areCoordinatesInsideSelectionYCoordinates = 
				(normalized.getY1() <= e.getY()) && (e.getY() <= normalized.getY2());
		
		boolean coordinatesInsideSelection = 
				areCoordinatesInsideSelectionXCoordinates && areCoordinatesInsideSelectionYCoordinates;
		
		return coordinatesInsideSelection;
	}
	
	private void createAnchors(){
		anchors.add(new Anchor(drawingSurface, this, AnchorPlace.NORTH, ANCHOR_SIZE));
		anchors.add(new Anchor(drawingSurface, this, AnchorPlace.SOUTH, ANCHOR_SIZE));
		anchors.add(new Anchor(drawingSurface, this, AnchorPlace.EAST, ANCHOR_SIZE));
		anchors.add(new Anchor(drawingSurface, this, AnchorPlace.WEST, ANCHOR_SIZE));
		anchors.add(new Anchor(drawingSurface, this, AnchorPlace.NORTHEAST, ANCHOR_SIZE));
		anchors.add(new Anchor(drawingSurface, this, AnchorPlace.NORTHWEST, ANCHOR_SIZE));
		anchors.add(new Anchor(drawingSurface, this, AnchorPlace.SOUTHEAST, ANCHOR_SIZE));
		anchors.add(new Anchor(drawingSurface, this, AnchorPlace.SOUTHWEST, ANCHOR_SIZE));
	}
	
	private void addMouseListener(MouseListener l){
		mouseListeners.add(l);
	}
	
	private void removeMouseListener(MouseListener l){
		mouseListeners.remove(l);
	}
	
	private void addMouseMotionListener(MouseMotionListener l){
		mouseMotionListeners.add(l);
	}
	
	private void removeMouseMotionListener(MouseMotionListener l){
		mouseMotionListeners.remove(l);
	}
	
	public boolean isContentCommited(){
		return contentCommited;
	}
	
	public void prepareContentForOperation(){
		content.prepareForOperation(this);
	}
	
	public ToolOperation commit(){
		state.next(new FrameStateCommited());
		return frameOperation;
	}
	
	private void applyContent(){
		frameOperation = content.commit();
		contentCommited = true;
		drawingSurface.clearTopLayerAndGuides();
		drawingSurface.repaint();
	}
	
	public void resize(int x1, int y1, int x2, int y2){
		
		//Permitimos cambiar el tamaño hasta un
		//tamaño mínimo.
		boolean isNewWidthGreatherThanMinimum = (x2 - x1) > MINIMUM_SIZE;
		boolean isNewHeightGreatherThanMinimum = (y2 - y1) > MINIMUM_SIZE;
		if(isNewWidthGreatherThanMinimum && isNewHeightGreatherThanMinimum){
			
			drawingSurface.clearTopLayerAndGuides();
			
			updateCoordinates(
					x1,
					y1,
					x2,
					y2);
			content.update();		
			
			refresh();
			
		}
	}
	
	public void move(int x, int y){
		drawingSurface.clearTopLayerAndGuides();
		
		moveTo(x, y);
		content.update();		
		
		refresh();
	}
	
	private void moveTo(int x, int y){
		int newLastX = x + getWidth();
		int newLastY = y + getHeight();
		
		updateCoordinates(x, y, newLastX, newLastY);
	}
	
	public void updateCoordinatesToMinimumSize(){
		updateCoordinates(
				getX1(),
				getY1(),
				getX1() + MINIMUM_START_SIZE,
				getY1() + MINIMUM_START_SIZE);
	}
	
	public void updateCoordinates(int x1, int y1, int x2, int y2){
		setX1(x1);
		setY1(y1);
		setX2(x2);
		setY2(y2);
	}
	
	public void drawFrameAndRefresh(int endX, int endY){
		updateCoordinates(getX1(), getY1(), endX, endY);
		
		drawingSurface.clearTopLayerAndGuides();
		refresh();
	}
	
	public void refresh(){
		drawFrame();						
		drawingSurface.repaint();
	}
	
	private void drawFrame(){
				
		drawingSurface.drawRectGuide(getX1(), getY1(), getX2(), getY2());
		
		for(Anchor currentAnchor: anchors){
			currentAnchor.drawAnchor();
		}
	}
	
	private enum AnchorPlace {
		NORTH,
		SOUTH,
		EAST,
		WEST,
		NORTHEAST,
		NORTHWEST,
		SOUTHEAST,
		SOUTHWEST
	}
	
	private class Anchor extends DrawableArea {

		private Frame frame;
		private AnchorPlace place;
		private int anchorSize;
		private int anchorMiddleSize;
		private DrawingSurface drawingSurface;
		private MouseListener mouseListener;
		private MouseMotionListener mouseMotionListener;
		
		private Anchor(DrawingSurface drawingSurface, Frame frame, AnchorPlace place, int anchorSize){
			this.drawingSurface = drawingSurface;
			this.frame = frame;
			this.place = place;
			this.anchorSize = anchorSize;
			this.anchorMiddleSize = (int)(anchorSize / 2.0);
			createEvents();
		}
		
		private void drawAnchor(){
			determinePositionCoordinates();

			drawingSurface.drawRectGuide(getX1(), getY1(), getX2(), getY2());
		}
		
		private void determinePositionCoordinates(){
			switch(place){
			
			case NORTH:
				setX1(frame.getX() + (int) (frame.getWidth() / 2.0) - anchorMiddleSize);
				setY1(frame.getY() - anchorSize);
				break;
				
			case SOUTH:
				setX1(frame.getX() + (int) (frame.getWidth() / 2.0) - anchorMiddleSize);
				setY1(frame.getY() + frame.getHeight());
				break;
				
			case EAST:
				setX1(frame.getX() + frame.getWidth());
				setY1(frame.getY() + (int) (frame.getHeight() / 2.0) - anchorMiddleSize);
				break;
				
			case WEST:
				setX1(frame.getX() - anchorSize);
				setY1(frame.getY() + (int) (frame.getHeight() / 2.0) - anchorMiddleSize);
				break;
				
			case NORTHEAST:
				setX1(frame.getX() + frame.getWidth());
				setY1(frame.getY() - anchorSize);
				break;
				
			case NORTHWEST:
				setX1(frame.getX() - anchorSize);
				setY1(frame.getY() - anchorSize);
				break;
				
			case SOUTHEAST:
				setX1(frame.getX() + frame.getWidth());
				setY1(frame.getY() + frame.getHeight());
				break;
				
			case SOUTHWEST:
				setX1(frame.getX() - anchorSize);
				setY1(frame.getY() + frame.getHeight());
				break;
			}
			
			setX2(getX1() + anchorSize);
			setY2(getY1() + anchorSize);
		}
		
		private void resizeFrame(int x1, int y1, int x2, int y2){
			frame.resize(x1, y1, x2, y2);
		}
		
		private boolean areEventCoordinatesInside(MouseEvent e){
			return areCoordinatesInside(e.getX(), e.getY());
		}
	
		private void createEvents(){
			if(mouseListener == null){
				mouseListener = new MouseAdapter(){

					@Override
					public void mousePressed(MouseEvent e) {
						
						if(areEventCoordinatesInside(e)){
							//Sólo uno de los anclajes será capaz de
							//cambiar el tamaño del marco. Aquel que
							//sea capaz, registrará un evento de
							//dragging para ello.
							startStrechingFrame();
							e.consume();
						}

					}

					@Override
					public void mouseReleased(MouseEvent e) {						
						endStrechingFrame();
						e.consume();						
					}
				};
				
				frame.addMouseListener(mouseListener);
			}			
		}
		
		private void startStrechingFrame(){
			if(mouseMotionListener == null){
				mouseMotionListener = new MouseMotionAdapter(){

					@Override
					public void mouseDragged(MouseEvent e) {
						
						switch(place){
						
						case NORTH:
							resizeFrame(frame.getX1(), e.getY() + anchorMiddleSize, frame.getX2(), frame.getY2());
							break;
							
						case SOUTH:
							resizeFrame(frame.getX1(), frame.getY1(), frame.getX2(), e.getY() - anchorMiddleSize);
							break;
							
						case EAST:
							resizeFrame(frame.getX1(), frame.getY1(), e.getX() - anchorMiddleSize, frame.getY2());
							break;
							
						case WEST:
							resizeFrame(e.getX() + anchorMiddleSize, frame.getY1(), frame.getX2(), frame.getY2());
							break;
							
						case NORTHEAST:
							resizeFrame(frame.getX1(), e.getY() + anchorMiddleSize, e.getX() - anchorMiddleSize, frame.getY2());
							break;
							
						case NORTHWEST:
							resizeFrame(e.getX() +  + anchorMiddleSize, e.getY() + anchorMiddleSize, frame.getX2(), frame.getY2());
							break;
							
						case SOUTHEAST:
							resizeFrame(frame.getX1(), frame.getY1(), e.getX() - anchorMiddleSize, e.getY() - anchorMiddleSize);
							break;
							
						case SOUTHWEST:
							resizeFrame(e.getX() + anchorMiddleSize, frame.getY1(), frame.getX2(), e.getY() - anchorMiddleSize);
							break;
						
						}
						
						e.consume();
					}
				};
			}
			
			frame.addMouseMotionListener(mouseMotionListener);			
		}
		
		private void endStrechingFrame(){
			if(mouseMotionListener != null){
				frame.removeMouseMotionListener(mouseMotionListener);
			}
		}
		
		private void disableEvents(){
			frame.removeMouseListener(mouseListener);
			endStrechingFrame();
		}
	}
}