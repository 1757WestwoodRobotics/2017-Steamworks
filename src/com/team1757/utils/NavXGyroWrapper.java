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
	
	public double getContinuousAngle() {
		return m_navxSource.getAngle();
	}
	
	public double getBoundedAngle() {
		return m_navxSource.getAngle() % 360;
	}
	
	 @Override
	public double pidGet() {
		return getBoundedAngle();
	 }

}
