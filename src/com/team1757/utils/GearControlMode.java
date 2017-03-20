package com.team1757.utils;

public enum GearControlMode {
	kCurrent(0), kManual(0), kMatchStart(330), kReceive(780), kHug(580), kScore(1430);
	
	private double target;
	
	GearControlMode(double target) {
		this.target = target;
	}
	
	public double getOutput() {
		return target;
	}
}
