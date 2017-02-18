package com.team1757.utils;

import org.usfirst.frc.team1757.robot.Robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class VisionCenterPID implements PIDSource {
	private VisionDetectionType m_visionDetectionType;

	public VisionCenterPID() {
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setVisionDetectionType(VisionDetectionType visionDetection) {
		m_visionDetectionType = visionDetection;
	}

	public VisionDetectionType getVisionDetectionType() {
		return m_visionDetectionType;
	}

	@Override
	public double pidGet() {
		if (m_visionDetectionType == VisionDetectionType.ContourCenterX) {
			Robot.vision.updateContoursReport();
			return Robot.vision.normalizePixelsX(Robot.vision.getContourCenterX(0));
		} else if (m_visionDetectionType == VisionDetectionType.ContourCenterY) {
			Robot.vision.updateContoursReport();
			return Robot.vision.normalizePixelsX(Robot.vision.getContourCenterX(0));
		} else if (m_visionDetectionType == VisionDetectionType.BlobCenterY) {
			Robot.vision.updateBlobsReport();
			return Robot.vision.normalizePixelsX(Robot.vision.getBlobCenterX(0));
		} else if (m_visionDetectionType == VisionDetectionType.BlobCenterY) {
			Robot.vision.updateBlobsReport();
			return Robot.vision.normalizePixelsX(Robot.vision.getBlobCenterY(0));
		}
		return 0;
	}

}
