package com.team1757.utils;

/**
 * Provide premade parameter structures for controlling the Gear mechanism 
 * 
 * @author ACabey
 */

public enum FloorGearPivotControlMode {
	kCurrent(0), kManual(0), kReceive(600), kScore(0), kCarry(-246);
	
	private double target;
	
	FloorGearPivotControlMode(double target) {
		this.target = target;
	}
	
	public double getOutput() {
		return target;
	}
}
