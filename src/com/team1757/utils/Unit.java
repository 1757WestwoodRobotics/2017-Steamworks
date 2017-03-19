package com.team1757.utils;

public enum Unit {
	kInches(.0394), kCM(.100),kMM(1);
	private double conversionFactor;
	
	Unit(double factor) {
		this.conversionFactor = factor;
	}
	
	double get(double value) {
		return value * conversionFactor;
	}
}