package com.team1757.utils;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDSource;
import com.team1757.utils.IllegalSourceException;

/**
 * @author ACabey
 * 
 * Supply the Y displacement reading to PIDController rather than the yaw that is provided by default
 */
public class NavXAccelWrapper extends VariablePIDInput {

	private AHRS m_navxSource;
	private Axis axis;
	
	public NavXAccelWrapper(PIDSource wrappedSource, Axis axis) throws IllegalSourceException {
		super(wrappedSource);
		if (wrappedSource.getClass() != AHRS.class) {
			throw new IllegalSourceException();
		}
		else {
			m_navxSource = ((AHRS) m_pidSource);
			this.axis = axis;
		}
	}
	
	 @Override
	public double pidGet() {
		 if (axis == Axis.axisX) {
			 return m_navxSource.getDisplacementX();
		 }
		 else {
			 return m_navxSource.getDisplacementY();
		 }
	 }
}
