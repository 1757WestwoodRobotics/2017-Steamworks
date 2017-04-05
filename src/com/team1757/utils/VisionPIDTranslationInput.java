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
public class VisionPIDTranslationInput implements PIDSource {
	
	private PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
	private VisionDetectionTarget target;

	public VisionPIDTranslationInput(VisionDetectionTarget target){
		this.target = target;
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
		return Robot.vision.getTargetTranslationInput(target);
	}
}
