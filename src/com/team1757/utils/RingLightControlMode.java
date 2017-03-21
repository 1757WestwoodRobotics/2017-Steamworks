package com.team1757.utils;

/**
 * Provide premade parameter structures for controlling ring lights 
 * 
 * @author ACabey
 */

public enum RingLightControlMode {
	kGearOn(true), kGearOff(false); //, kShooterOn(true), kShooterOff(false);
	
	private boolean isEnabled;
	
	RingLightControlMode(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public boolean getOutput() {
		return isEnabled;
	}
}
