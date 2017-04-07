package com.team1757.subsystems;

import com.team1757.robot.RobotMap;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem {
	private final UsbCamera dropGearCam = RobotMap.dropGearCam;
	private final UsbCamera floorGearCam = RobotMap.floorGearCam;
	private boolean isGearCamActive = true;
	public int xResolution = 160;
	public int yResolution = 120;
	private int fps = 20;
	
	private final int EXPOSURE_LOW = 0;

	@Override
	protected void initDefaultCommand() {

	}

	/**
	 * Initializes the cameras and starts the CameraServer to stream to GRIP /
	 * SmartDashboard
	 */
	public void init() {
		
		dropGearCam.setFPS(fps);
		dropGearCam.setResolution(xResolution, yResolution);
		dropGearCam.setExposureAuto();
		
		floorGearCam.setFPS(fps);
		floorGearCam.setResolution(xResolution, yResolution);
		floorGearCam.setExposureAuto();
		
	}

	/**
	 * Sets the resolution of both cameras on the robot
	 * 
	 * @param x
	 *            x dimension of the camera's image
	 * @param y
	 *            y dimension of the camera's image
	 */
	public void camerasSetResolution(int x, int y) {
		xResolution = x;
		yResolution = y;
		dropGearCam.setResolution(x, y);
		floorGearCam.setResolution(x, y);
	}

	/**
	 * Accessor for the X resolution used by both cameras on the robot
	 * 
	 * @return x dimension of the camera's image
	 */
	public int getResolutionX() {
		return xResolution;
	}

	/**
	 * Accessor for the Y resolution used by both cameras on the robot
	 * 
	 * @return y dimension of the camera's image
	 */
	public int getResolutionY() {
		return yResolution;
	}

	/**
	 * Sets the frame rate for both cameras on the robot. Streaming to the
	 * CameraServer will not necessarily reach set FPS.
	 * 
	 * @param fps
	 */
	public void camerasSetFPS(int fps) {
		this.fps = fps;
		dropGearCam.setFPS(fps);
		floorGearCam.setFPS(fps);
	}

	/**
	 * Accessor for the frame rate used by both cameras on the robot
	 * 
	 * @return fps
	 */
	public int getFPS() {
		return fps;
	}
	
	/**
	 * Set low exposure for accurate vision processing
	 */
	public void setExposureLow() {
		dropGearCam.setExposureManual(EXPOSURE_LOW);
		floorGearCam.setExposureManual(EXPOSURE_LOW);
	}
	
	/**
	 * Set auto exposure for driver viewing
	 */
	public void setExposureAuto() {
		dropGearCam.setExposureAuto();
		floorGearCam.setExposureAuto();
	}

	/**
	 * Swaps the current camera streaming to GRIP / SmartDashboard with the one
	 * not streaming (and not capturing)
	 */
	public void toggleVisionCamera() {
		if (isGearCamActive) {
			CameraServer.getInstance().getServer().setSource(floorGearCam);
			isGearCamActive = false;
		} else {
			CameraServer.getInstance().getServer().setSource(dropGearCam);
			isGearCamActive = true;
		}
	}
}
