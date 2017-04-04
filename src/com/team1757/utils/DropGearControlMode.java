package com.team1757.utils;

/**
 * Provide premade parameter structures for controlling the Gear mechanism 
 * 
 * @author ACabey
 */

public enum DropGearControlMode {
	kCurrent(0), kTarget(0), kManual(0), kMatchStart(330), kReceive(780), kHug(580), kScore(1430);
	
	private double target;
	
	DropGearControlMode(double target) {
		this.target = target;
	}
	
	public double getOutput() {
		return target;
	}
}
