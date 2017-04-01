package com.team1757.utils;

/**
 * Provide premade parameter structures for controlling the Lifter 
 * 
 * @author ACabey
 */

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