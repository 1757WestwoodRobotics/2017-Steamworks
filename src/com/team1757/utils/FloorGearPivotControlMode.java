package com.team1757.utils;

/**
 * Provide premade parameter structures for controlling the Gear mechanism 
 * 
 * @author ACabey
 */

public enum FloorGearPivotControlMode {
	kCurrent(0), kManual(0), kReceive(3200), kScore(2500), kCarry(2000);
	private double target;
	
	FloorGearPivotControlMode(double target) {
		this.target = target;
	}
	
	public double getOutput() {
		return target;
	}
}
