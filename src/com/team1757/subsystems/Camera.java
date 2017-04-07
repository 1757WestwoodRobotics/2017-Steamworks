package com.team1757.subsystems;

import com.team1757.robot.RobotMap;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem {
	private final UsbCamera gearCam = RobotMap.gearCam;
	private final UsbCamera shooterCam = RobotMap.shooterCam;
	private boolean isGearCamActive = true;
	public int xResolution = 160;
	public int yResolution = 120;
	private int fps = 30;
	
	private final int EXPOSURE_LOW = 0;

	@Override
	protected void initDefaultCommand() {

	}

	/**
	 * Initializes the cameras and starts the CameraServer to stream to GRIP /
	 * SmartDashboard
	 */
	public void init() {
		
//		gearCam.setFPS(fps);
//		gearCam.setResolution(xResolution, yResolution);
//		gearCam.setExposureAuto();
//		
//		shooterCam.setFPS(fps);
//		shooterCam.setResolution(xResolution, yResolution);
//		shooterCam.setExposureAuto();
//		
//		CameraServer.getInstance().addCamera(gearCam);
//		Timer.delay(.5);
//		CameraServer.getInstance().addCamera(shooterCam);
//
//		CameraServer.getInstance().startAutomaticCapture(gearCam);
//		CameraServer.getInstance().startAutomaticCapture(shooterCam);
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
		gearCam.setResolution(x, y);
		shooterCam.setResolution(x, y);
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
		gearCam.setFPS(fps);
		shooterCam.setFPS(fps);
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
		gearCam.setExposureManual(EXPOSURE_LOW);
		shooterCam.setExposureManual(EXPOSURE_LOW);
	}
	
	/**
	 * Set auto exposure for driver viewing
	 */
	public void setExposureAuto() {
		gearCam.setExposureAuto();
		shooterCam.setExposureAuto();
	}

	/**
	 * Swaps the current camera streaming to GRIP / SmartDashboard with the one
	 * not streaming (and not capturing)
	 */
	public void toggleVisionCamera() {
		if (isGearCamActive) {
			CameraServer.getInstance().getServer().setSource(shooterCam);
			isGearCamActive = false;
		} else {
			CameraServer.getInstance().getServer().setSource(gearCam);
			isGearCamActive = true;
		}
	}
}
