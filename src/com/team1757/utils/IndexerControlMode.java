package com.team1757.utils;

import com.ctre.CANTalon.TalonControlMode;

/**
 * Provide premade parameter structures for controlling the Indexer 
 * 
 * @author ACabey
 */

public enum IndexerControlMode {
	kPercentForward(0.55, TalonControlMode.PercentVbus), kPercentReverse(-.30, TalonControlMode.PercentVbus),
	kStop(0.0);
	
	
	private double percentVBus;
	private TalonControlMode talonControl = TalonControlMode.PercentVbus;
	
	IndexerControlMode(double percentVBus) {
		this.percentVBus = percentVBus;
	}
	
	IndexerControlMode(double percentVBus, TalonControlMode talonControl) {
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
