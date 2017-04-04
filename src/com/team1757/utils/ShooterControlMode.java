package com.team1757.utils;

import com.ctre.CANTalon.TalonControlMode;

/**
 * Provide premade parameter structures for controlling the Shooter 
 * 
 * @author ACabey
 */

public enum ShooterControlMode {
	kPercentForward(0.8, TalonControlMode.PercentVbus, true),
	kVoltageForward(10.0, TalonControlMode.Voltage, true),
	kSpeedForward(32000.0, TalonControlMode.Speed, true),
	kStop(0.0, false);
	
	
	private double percentVBus;
	private TalonControlMode talonControl = TalonControlMode.PercentVbus;
	private boolean isShooting;
	
	ShooterControlMode(double percentVBus, boolean isShooting) {
		this.percentVBus = percentVBus;
		this.isShooting = isShooting;
	}
	
	ShooterControlMode(double percentVBus, TalonControlMode talonControl, boolean isShooting) {
		this.percentVBus = percentVBus;
		this.talonControl = talonControl;
		this.isShooting = isShooting;
	}
	
	public double getOutput() {
		return percentVBus;
	}
	
	public TalonControlMode getControlMode() {
		return talonControl;
	}
	
	public boolean getIsShooting() {
		return isShooting;
	}
}
