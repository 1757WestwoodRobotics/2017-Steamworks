package com.team1757.utils;

public class IllegalSourceException extends Exception {
	/**
	 * @author ACabey
	 * Throws if the NavXGyroWrapper class is supplied a non-AHRS PIDSource object
	 */
	private static final long serialVersionUID = -5599061318558012769L;

	public IllegalSourceException() {
		super("Must supply NavXGyroWrapper an AHRS instance");
	}
}
