package com.team1757.utils;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * @author ACabey
 */
public class VariablePIDInput implements PIDSource {

	protected PIDSource m_pidSource;
	
	public VariablePIDInput(PIDSource wrappedSource) {
		m_pidSource = wrappedSource;
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		m_pidSource.setPIDSourceType(pidSource);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return m_pidSource.getPIDSourceType();
	}

	@Override
	public double pidGet() {
		return m_pidSource.pidGet();
	}
}
