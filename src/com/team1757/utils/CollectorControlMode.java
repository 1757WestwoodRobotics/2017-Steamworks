package com.team1757.utils;

import com.ctre.CANTalon.TalonControlMode;

public enum CollectorControlMode {
	kPercentForward(1.0, TalonControlMode.PercentVbus), kPercentReverse(-0.5, TalonControlMode.PercentVbus),
	kSpeedForward(0.0, TalonControlMode.Speed), kSpeedReverse(0.0, TalonControlMode.Speed),
	kStop(0.0);
	
	
	private double percentVBus;
	private TalonControlMode talonControl = TalonControlMode.PercentVbus;
	
	CollectorControlMode(double percentVBus) {
		this.percentVBus = percentVBus;
	}
	
	CollectorControlMode(double percentVBus, TalonControlMode talonControl) {
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
