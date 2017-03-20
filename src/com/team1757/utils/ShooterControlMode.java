package com.team1757.utils;

import com.ctre.CANTalon.TalonControlMode;

/**
 * Provide premade parameter structures for controlling the Shooter 
 * 
 * @author ACabey
 */

public enum ShooterControlMode {
	kPercentForward(0.8, TalonControlMode.PercentVbus),
	kVoltageForward(4.0, TalonControlMode.Voltage),
	kSpeedForward(32000.0, TalonControlMode.Speed),
	kStop(0.0);
	
	
	private double percentVBus;
	private TalonControlMode talonControl = TalonControlMode.PercentVbus;
	
	ShooterControlMode(double percentVBus) {
		this.percentVBus = percentVBus;
	}
	
	ShooterControlMode(double percentVBus, TalonControlMode talonControl) {
		this.percentVBus = percentVBus;
		this.talonControl = talonControl;
	}
	
	public double getOutput() {
		return percentVBus;
	}
	
	public TalonControlMode getControlMode() {
		return talonControl;
	}
}
