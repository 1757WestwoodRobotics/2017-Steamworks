package com.team1757.utils;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * @author ACabey
 * 
 * Force WPIlib PIDController to write to an accessible instance variable rather than directly to a SpeedController
 */
public class VariablePIDOutput implements PIDOutput {

	private double value;
	@Override
	public void pidWrite(double output) {
		value = output;
	}
	
	public double getValue() {
		return value;
	}
}
