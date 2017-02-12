package com.team1757.utils;

import org.usfirst.frc.team1757.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class VisionCenterPID implements PIDSource {
	private Vision m_vision;
	private VisionDetectionType m_visionDetectionType;

	public VisionCenterPID(Vision vision) {
		m_vision = vision;
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
			m_vision.updateContoursReport();
			return m_vision.getContourCenterX(0);
		} else if (m_visionDetectionType == VisionDetectionType.ContourCenterY) {
			m_vision.updateContoursReport();
			return m_vision.getContourCenterX(0);
		} else if (m_visionDetectionType == VisionDetectionType.BlobCenterY) {
			m_vision.updateBlobsReport();
			return m_vision.getBlobCenterX(0);
		} else if (m_visionDetectionType == VisionDetectionType.BlobCenterY) {
			m_vision.updateBlobsReport();
			return m_vision.getBlobCenterY(0);
		}
		return 0;
	}

}
