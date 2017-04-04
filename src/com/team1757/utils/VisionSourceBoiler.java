package com.team1757.utils;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * A PID source for a single vision target using the center of the target
 * 
 * @author Ryan Marten
 *
 */
public class VisionSourceBoiler implements PIDSource {
	
	

	private PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
	private double offset;

	public VisionSourceBoiler() {
		this.offset = 0;
	}
	
	public VisionSourceBoiler(double offset){
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
			return Robot.vision.getTargetCenterContour() + offset;
	}

}
