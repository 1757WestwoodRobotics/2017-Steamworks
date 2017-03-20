package com.team1757.utils;

public enum LifterControlMode {
	kUp(0.75), kDown(0.5), kStop(0.0);
	
	private double percentVBus;
	
	LifterControlMode(double percentVBus) {
		this.percentVBus = percentVBus;
	}
	
	public double getOutput() {
		return percentVBus;
	}
}
