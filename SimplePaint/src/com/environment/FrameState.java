package com.environment;

public interface FrameState {

	public void next(FrameState state);
	public void setup();
	
}
