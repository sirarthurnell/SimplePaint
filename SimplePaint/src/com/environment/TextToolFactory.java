package com.environment;

public class TextToolFactory implements FrameContentFactory {

	@Override
	public FrameContent createContent(DrawingSurface drawingSurface) {	
		
		return new TextTool(drawingSurface);
		
	}

}
