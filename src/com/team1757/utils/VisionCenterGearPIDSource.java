package com.team1757.utils;

import com.team1757.robot.Robot;

public class VisionCenterGearPIDSource extends VisionCenterPIDSource {
	@Override
	public double pidGet() {
		if (this.getVisionDetectionType() == VisionDetectionType.ContourCenterX) {
			return Robot.vision.getDifferenceInGearTargetAreas();
		} else if (this.getVisionDetectionType() == VisionDetectionType.ContourCenterY) {

		} else if (this.getVisionDetectionType() == VisionDetectionType.BlobCenterY) {

		} else if (this.getVisionDetectionType() == VisionDetectionType.BlobCenterY) {
		}
		return 0;
	}
}
