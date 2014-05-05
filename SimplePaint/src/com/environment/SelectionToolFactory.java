package com.environment;

public class SelectionToolFactory implements FrameContentFactory {

	@Override
	public FrameContent createContent(DrawingSurface drawingSurface) {	
		
		return new SelectionTool(drawingSurface);
		
	}

}
