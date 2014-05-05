package com.environment;

public class TextTool extends DrawableArea implements FrameContent, SizeChangedListener {

	private DrawingSurface drawingSurface;
	private TextEditor textEditor;
	private Frame parentFrame;
	
	public TextTool(DrawingSurface drawingSurface){
		super();
		this.drawingSurface = drawingSurface;
		this.textEditor = this.drawingSurface.getTextEditor();
	}
	
	@Override
	public void sizeChanged(int x1, int y1, int x2, int y2) {
		parentFrame.resize(x1 - 1, y1 - 1, x2, y2);
	}
	
	@Override
	public void update() {
		updateCoordinatesAndFrame();
	}

	@Override
	public void prepareForOperation(Frame parentFrame) {
		this.parentFrame = parentFrame;
		updateCoordinatesAndFrame();
		textEditor.setText("");
		textEditor.setVisible(true);
		textEditor.addSizeChangedListener(this);
	}

	@Override
	public ToolOperation commit() {
		TextOperation operation = new TextOperation(drawingSurface.getConfiguration().getMemento(),
				textEditor.getText(),
				textEditor.getLineHeight(),
				getCoordinatesCopy());
		
		operation.execute(drawingSurface);
		textEditor.setVisible(false);
		
		return operation;
	}
	
	private void updateCoordinatesAndFrame(){
		updateCoordinates();		
		textEditor.resize(getX1() + 1, getY1() + 1, getX2(), getY2());
	}
	
	private void updateCoordinates(){
		setX1(parentFrame.getX1());
		setY1(parentFrame.getY1());
		setX2(parentFrame.getX2());
		setY2(parentFrame.getY2());
	}	

}
