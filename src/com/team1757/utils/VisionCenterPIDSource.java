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
public class VisionCenterPIDSource implements PIDSource {
	
	
	private VisionDetectionType visionDetectionType;
	@SuppressWarnings("unused")
	private PIDSourceType pidSourceType;

	public VisionCenterPIDSource() {
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidSourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	public void setVisionDetectionType(VisionDetectionType visionDetection) {
		visionDetectionType = visionDetection;
	}

	public VisionDetectionType getVisionDetectionType() {
		return visionDetectionType;
	}

	@Override
	public double pidGet() {
		if (visionDetectionType == VisionDetectionType.ContourCenterX) {
			return Robot.vision.getTargetCenterContour();
		} else if (visionDetectionType == VisionDetectionType.ContourCenterY) {

		} else if (visionDetectionType == VisionDetectionType.BlobCenterY) {

		} else if (visionDetectionType == VisionDetectionType.BlobCenterY) {

		}
		return 0;
	}

}
