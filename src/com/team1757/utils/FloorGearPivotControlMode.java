package com.team1757.utils;

/**
 * Provide premade parameter structures for controlling the Gear mechanism 
 * 
 * @author ACabey
 */

public enum FloorGearPivotControlMode {
	//kCurrent(0), kManual(0), kReceive(3140), kScore(2440), kCarry(1940);
	kCurrent(0), kManual(0), kReceive(2640), kScore(1940), kCarry(1440);
	private double target;
	
	FloorGearPivotControlMode(double target) {
		this.target = target;
	}
	
	public double getOutput() {
		return target;
	}
}
