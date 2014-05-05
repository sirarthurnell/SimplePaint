package com.environment;

import java.awt.Font;

public interface TextEditor {

	public void resize(int x1, int y1, int x2, int y2);
	
	public int getLineHeight();
	
	public String getText();
	
	public void setText(String text);
	
	public void setVisible(boolean visible);
	
	public void setFont(Font font);
	
	public void addSizeChangedListener(SizeChangedListener l);
	
	public void removeSizeChangedListener(SizeChangedListener l);
	
}
