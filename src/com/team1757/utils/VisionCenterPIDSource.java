package com.team1757.utils;

import com.team1757.robot.Robot;


import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class VisionCenterPIDSource implements PIDSource {
	private VisionDetectionType m_visionDetectionType;

	public VisionCenterPIDSource() {
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
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
			return Robot.vision.getTargetCenterContour();
		} else if (m_visionDetectionType == VisionDetectionType.ContourCenterY) {
			
		} else if (m_visionDetectionType == VisionDetectionType.BlobCenterY) {
			
		} else if (m_visionDetectionType == VisionDetectionType.BlobCenterY) {
			
		}
		return 0;
	}

}
