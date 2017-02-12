package com.team1757.utils;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDSource;
import com.team1757.utils.IllegalSourceException;

/**
 * @author ACabey
 * 
 * Supply the angle reading to PIDController rather than the yaw that is provided by default
 * 
 * getBoundedAngle() provides the angular reading within 0-360 range
 */
public class NavXGyroWrapper extends VariablePIDInput {
	private AHRS m_navxSource;
	public NavXGyroWrapper(PIDSource wrappedSource) throws IllegalSourceException {
		super(wrappedSource);
		if (wrappedSource.getClass() != AHRS.class) {
			throw new IllegalSourceException();
		}
		else {
			m_navxSource = ((AHRS) m_pidSource);
		}
	}
	
	/**
	 * Get gyroscope bounded angular reading in range [0,360)
	 * 
	 * @return double angle in range [0,360)
	 */
	public double getCurrentBoundedAngle() {
		return (360 + (getContinuousAngle() % 360)) % 360;
	}
	
	/**
	 * Get gyroscope continuous (unbounded) angular reading in range
	 * (-MAX_DOUBLE, MAX_DOUBLE)
	 * 
	 * @return double angle in range (-MAX_DOUBLE, MAX_DOUBLE)
	 */
	public double getContinuousAngle() {
		return m_navxSource.getAngle();
	}
	
	 @Override
	public double pidGet() {
		return getContinuousAngle();
	 }

}
