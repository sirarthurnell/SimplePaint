package com.environment;

import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class TextBox extends JTextArea implements TextEditor {

	private static final long serialVersionUID = 1L;
	
	private List<SizeChangedListener> sizeChangedListeners;
	private KeyListener keyListener;
	
	public TextBox(){
		super();		
		sizeChangedListeners = new ArrayList<SizeChangedListener>();
		setLineWrap(true);
		clearMargins();
		createEvents();
	}
	
	private void clearMargins(){
		getMargin().left = 0;
		getMargin().bottom = 0;
		getMargin().top = 0;
		getMargin().right = 0;
	}
	
	public int getLineHeight(){
		FontMetrics metrics = getFontMetrics(getFont());		
		int fontHeight = metrics.getHeight();
		return fontHeight;
	}
	
	private int getTextHeight(){
		int lineHeight = getLineHeight();
		
		String textContained = getText();
		int linesCount = 1;
		for(int i = 0; i < textContained.length(); i++){
			if(textContained.charAt(i) == '\n'){
				linesCount++;
			}
		}
		
		return linesCount * lineHeight;
	}
	
	private void createEvents(){
		if(keyListener == null){
			keyListener = new KeyAdapter(){
				
				@Override
				public void keyTyped(KeyEvent arg0) {
					
					for(SizeChangedListener l: sizeChangedListeners){
						Rectangle bounds = getBounds();
						
						l.sizeChanged(
								bounds.x,
								bounds.y,
								bounds.x + bounds.width,
								bounds.y + getTextHeight());
					}
					
				}
				
			};
		}
		
		addKeyListener(keyListener);
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		Coordinates normalized = new Coordinates(x1, y1, x2, y2);
		normalized = normalized.normalize();
		
		setBounds(
				normalized.getX1(),
				normalized.getY1(),
				normalized.getWidth(),
				normalized.getHeight());
	}

	@Override
	public void addSizeChangedListener(SizeChangedListener l) {
		sizeChangedListeners.add(l);
	}

	@Override
	public void removeSizeChangedListener(SizeChangedListener l) {
		sizeChangedListeners.remove(l);
		
	}

}
