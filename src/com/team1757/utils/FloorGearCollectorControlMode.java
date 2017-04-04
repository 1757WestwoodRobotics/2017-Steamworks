package com.team1757.utils;

import com.ctre.CANTalon.TalonControlMode;

/**
 * Provide premade parameter structures for controlling the floor gear collector 
 * 
 * @author ACabey
 */

public enum FloorGearCollectorControlMode {
	kIntake(0.5, TalonControlMode.PercentVbus), kDump(-0.8, TalonControlMode.PercentVbus),
	kStop(0.0);
	
	
	private double percentVBus;
	private TalonControlMode talonControl = TalonControlMode.PercentVbus;
	
	FloorGearCollectorControlMode(double percentVBus) {
		this.percentVBus = percentVBus;
	}
	
	FloorGearCollectorControlMode(double percentVBus, TalonControlMode talonControl) {
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
