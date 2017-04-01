package com.team1757.utils;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * A PID source for gear target using difference in the two areas of 3M tape
 * 
 * @author Ryan Marten
 *
 */
public class VisionAirshipSource implements PIDSource {
	
	private PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
	private double offset;

	public VisionAirshipSource() {
		this.offset = 0;
	}
	
	public VisionAirshipSource(double offset){
		this.offset = offset;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidSourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidSourceType;
	}

	@Override
	public double pidGet() {
		return Robot.vision.getGearTargetCenter() + offset;
	}
}
