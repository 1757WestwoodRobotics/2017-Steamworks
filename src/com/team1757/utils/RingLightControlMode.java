package com.team1757.utils;

/**
 * Provide premade parameter structures for controlling ring lights 
 * 
 * @author ACabey
 */

public enum RingLightControlMode {
	// reversed for default as off
	kGearLightOn(false), kGearLightOff(true); //, kShooterLightOn(true), kShooterLightOff(false);
	
	private boolean isEnabled;
	
	RingLightControlMode(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public boolean getOutput() {
		return isEnabled;
	}
}
