package com.team1757.utils;

/**
 */
public enum VisionDetectionTarget {
	GearAirship(.03, 0.0002, .04, -1000000, 1000000, -.5, .5, 8), GearPlayerStation(.03, 0.0002, .04, -1000000, 1000000, -.5, .5, 8), BallHighGoal(1.0, 0.0,
			0.04, -1.0, 1.0, -1.0, 1.0, .0005), TestSingleTarget(1.0, 0.0, 0.04, -1.0, 1.0, -1.0, 1.0, .0005);

	private double p, i, d, inputMin, inputMax, outputMin, outputMax, tolerance;

	private VisionDetectionTarget(double p, double i, double d, double inputMin, double inputMax, double outputMin, double outputMax, double tolerance) {
		this.p = p;
		this.i = i;
		this.d = d;
		this.inputMin = inputMin;
		this.outputMin = outputMin;
		this.inputMax = inputMax;
		this.outputMax = outputMax;
		this.tolerance = tolerance;
	}

	public double getP() {
		return p;
	}

	public double getI() {
		return i;
	}

	public double getD() {
		return d;
	}
	
	public double getInputMin() {
		return inputMin;
	}
	
	public double getInputMax() {
		return inputMax;
	}
	
	public double getOutputMin(){
		return outputMin;
	}
	
	public double getOutputMax(){
		return outputMax;
	}
	
	public double getTolerance(){
		return tolerance;
	}
}
