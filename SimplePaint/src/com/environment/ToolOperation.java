package com.environment;

public abstract class ToolOperation {
	
	private DrawingSurfaceConfigurationMemento configurationMemento;
	
	protected ToolOperation(DrawingSurfaceConfigurationMemento configurationMemento){
		this.configurationMemento = configurationMemento;
	}

	public void execute(DrawingSurface drawingSurface){
		configurationMemento.restore(drawingSurface);
		draw(drawingSurface);
	}
	
	protected abstract void draw(DrawingSurface drawingSurface);
	
}
