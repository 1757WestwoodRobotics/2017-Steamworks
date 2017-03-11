package com.team1757.subsystems;

import com.team1757.robot.RobotMap;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Subsystem that contains cameras and ring lights. Interacts with GRIP for
 * vision processing via NetworkTables and has several methods for computing
 * different responses to the GRIP output
 * 
 * @author Ryan Marten
 */
public class Vision extends Subsystem {

	private final UsbCamera gearCam = RobotMap.gearCam;
	private final UsbCamera shooterCam = RobotMap.shooterCam;

	private final PIDController visionTranslationController = RobotMap.visionTranslationController;
	private final PIDController visionGearTranslationController = RobotMap.visionGearTranslationController;

	private static boolean isGearCamActive = true;

	private NetworkTable contoursReport;
	private NetworkTable blobsReport;
	private NetworkTable linesReport;

	private int xResolution = 160;
	private int yResolution = 120;
	private int fps = 30;

	private final double GEAR_TRANSLATION_PID_TOLERANCE = 100;

	public void initDefaultCommand() {
	}

	// Cameras

	/**
	 * Initializes the cameras and starts the CameraServer to stream to GRIP /
	 * SmartDashboard
	 */
	public void init() {
		gearCam.setFPS(fps);
		gearCam.setResolution(xResolution, yResolution);
		// Use zero exposure for bright vision targets and back light
		// gearCam.setExposureManual(0);

		shooterCam.setFPS(fps);
		shooterCam.setResolution(xResolution, yResolution);
		// Use zero exposure for bright vision targets and back light
		// shooterCam.setExposureManual(0);

		CameraServer.getInstance().addCamera(gearCam);

		CameraServer.getInstance().addCamera(shooterCam);

		CameraServer.getInstance().startAutomaticCapture(gearCam);

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

	// Ring Lights

	/**
	 * Activates the relay connected to the gear camera's ring light
	 */
	public void turnOnGearRingLight() {
	}

	/**
	 * Deactivates the relay connected to the gear camera's ring light
	 */
	public void turnOffGearRingLight() {
	}

	// PID Translation Controllers

	/**
	 * Enables the PID controller for using X translation of the robot to center
	 * a single target
	 */
	public void enableTranslationPID() {
		visionTranslationController.enable();
	}

	/**
	 * Disable the PID controller for using X translation of the robot to center
	 * a single target
	 */
	public void disableTranslationPID() {
		visionTranslationController.disable();
	}

	/**
	 * Returns whether the robot has centered itself on the single vision target
	 * with a tolerance of .001
	 * 
	 * @return isSetPointedReached
	 */
	public boolean reachedVisionTranslationSetPoint() {
		return Math.abs(visionTranslationController.getError()) < .001;
	}

	/**
	 * Returns the output of the PID controller for translation to center a
	 * single vision target
	 * 
	 * @return PIDOutput
	 */
	public double getTranslationPID() {
		return visionTranslationController.get();
	}

	/**
	 * Enables the PID controller for using X translation of the robot to center
	 * the gear's double vision targets
	 */
	public void enableGearTranslationPID() {
		visionGearTranslationController.enable();
	}

	/**
	 * Disables the PID controller for using X translation of the robot to
	 * center the gear's double vision targets
	 */
	public void disableGearTranslationPID() {
		visionGearTranslationController.disable();
	}

	/**
	 * Gets the difference between current setpoint (0) and current difference
	 * in gear target areas (in pixels squared)
	 * 
	 * @return error in difference between gear target areas in range
	 *         (-MAX_DOUBLE, MAX_DOUBLE)
	 */
	public double getVisionGearTranslationControllerError() {
		return 0 - getDifferenceInGearTargetAreas();
	}

	/**
	 * Returns whether the robot has centered itself on the gear's double vision
	 * targets with a tolerance of .001
	 * 
	 * @return isSetPointedReached
	 */
	public boolean reachedVisionGearTranslationSetpoint() {
		return Math.abs(getVisionGearTranslationControllerError()) <= GEAR_TRANSLATION_PID_TOLERANCE;
	}

	/**
	 * Returns the output of the PID controller for translation to center a the
	 * gear's double vision targets
	 * 
	 * @return PIDOutput
	 */
	public double getGearTranslationPID() {
		// System.out.println(visionGearTranslationController.get());
		return visionGearTranslationController.get();
	}

	// Math operations

	/**
	 * Converts an x coordinate received from a camera to a value between [-1,1]
	 * using the resolution set for the vision subsystem
	 * 
	 * @param coordinateX
	 * @return normalizedX
	 */
	public double normalizePixelsX(double coordinateX) {
		return coordinateX * (2.0f / xResolution) - 1;
	}

	/**
	 * Converts an Y coordinate received from a camera to a value between [-1,1]
	 * using the resolution set for the vision subsystem
	 * 
	 * @param coordinateY
	 * @return normalizedY
	 */
	public double normalizePixelsY(double coordinateY) {
		return coordinateY * (2.0f / yResolution) - 1;
	}

	// Contour Vision Processing

	/**
	 * When two targets are detected, this will return the difference in those
	 * targets' areas, otherwise, 0.
	 * 
	 * @return differenceInGearTargetAreas
	 */
	public double getDifferenceInGearTargetAreas() {
		// TODO Can do something smart with finding similar targets to use for
		// this math when false targets like lights are published and can't be
		// done with grip contours filter

		updateContoursReport();
		if (getContoursCount() == 2) {

			// Left rectangle minus right rectangle
			if (getContourCenterX(0) < getContourCenterX(1)) {
				return getContourArea(0) - getContourArea(1);
			} else {
				return getContourArea(1) - getContourArea(0);
			}

		}
		return 0;
	}

	/**
	 * Returns the center of two targets if they are detected, otherwise, 0.
	 * 
	 * @return gearTargetCenter in the image from [-1,1]
	 */
	public double getGearTargetCenter() {
		updateContoursReport();
		if (getContoursCount() == 2) {

			// Center between the two targets
			return normalizePixelsX((getContourCenterX(0) + getContourCenterX(1)) / 2);

		}
		return 0;
	}

	/**
	 * Returns the center of a target if it is detected, otherwise, 0.
	 * 
	 * @return targetCenter in the image from [-1,1]
	 */
	public double getTargetCenterContour() {
		updateContoursReport();
		if (getContoursCount() != 0) {
			return normalizePixelsX(getContourCenterX(0));
		}
		return 0;
	}

	/**
	 * Updates the local contoursReport table. WARNING, this has to be somewhat
	 * syncrhonized so one thing doesn't update the table while another tries to
	 * access an element that might not exist in the updated table
	 *
	 */
	public void updateContoursReport() {
		// A contours report contains centerX[], centerY[], solidity[],
		// height[], area[], and width[]

		// I think that it publishes the contours with the smallest area first
		// in the array
		contoursReport = NetworkTable.getTable("GRIP/myContoursReport");
	}

	/**
	 * Returns the number of contours found in the report. Use to ensure index
	 * is range for accessing contour properties
	 * 
	 * 
	 * @return contours
	 * 
	 */
	public int getContoursCount() {
		double[] defaultValue = new double[0];
		double[] xCenters = contoursReport.getNumberArray("centerX", defaultValue);
		return xCenters.length;
	}

	/**
	 * Returns the x coordinate for the center of contour chosen by index
	 * 
	 * Precondition: contourIndex is not out of range
	 * 
	 * @param contourIndex
	 * @return centerX in pixels
	 */
	public double getContourCenterX(int contourIndex) {
		double[] defaultValue = new double[0];
		double[] xCenters = contoursReport.getNumberArray("centerX", defaultValue);
		return xCenters[contourIndex];
	}

	/**
	 * Returns the y coordinate for the center of contour chosen by index
	 * 
	 * Precondition: contourIndex is not out of range
	 * 
	 * @param contourIndex
	 * @return centerY in pixels
	 */
	public double getContourCenterY(int contourIndex) {
		double[] defaultValue = new double[0];
		double[] yCenters = contoursReport.getNumberArray("centerY", defaultValue);
		return yCenters[contourIndex];
	}

	/**
	 * Returns the solidity of contour chosen by index
	 * 
	 * Precondition: contourIndex is not out of range
	 * 
	 * @param contourIndex
	 * @return solidity
	 */
	public double getContourSolidity(int contourIndex) {
		double[] defaultValue = new double[0];
		double[] solidities = contoursReport.getNumberArray("solidity", defaultValue);
		return solidities[contourIndex];
	}

	/**
	 * Returns the height of contour chosen by index
	 * 
	 * Precondition: contourIndex is not out of range
	 * 
	 * @param contourIndex
	 * @return height in pixels
	 */
	public double getContourHeight(int contourIndex) {
		double[] defaultValue = new double[0];
		double[] heights = contoursReport.getNumberArray("height", defaultValue);
		return heights[contourIndex];
	}

	/**
	 * Returns the area of contour chosen by index
	 * 
	 * Precondition: contourIndex is not out of range
	 * 
	 * @param contourIndex
	 * @return area in square pixels
	 */
	public double getContourArea(int contourIndex) {
		double[] defaultValue = new double[0];
		double[] areas = contoursReport.getNumberArray("area", defaultValue);
		return areas[contourIndex];
	}

	/**
	 * Returns the width of contour chosen by index
	 * 
	 * Precondition: contourIndex is not out of range
	 * 
	 * @param contourIndex
	 * @return width in pixels
	 */
	public double getContourWith(int contourIndex) {
		double[] defaultValue = new double[0];
		double[] widths = contoursReport.getNumberArray("width", defaultValue);
		return widths[contourIndex];
	}

	// Blobs

	/**
	 * Updates the local blobsReport table. WARNING, this has to be somewhat
	 * syncrhonized so one thing doesn't update the table while another tries to
	 * access an element that might not exist in the updated table
	 * 
	 * 
	 */
	public void updateBlobsReport() {
		// A blobs report contains x[], y[], and size[]
		blobsReport = NetworkTable.getTable("GRIP/myBlobsReport");
	}

	/**
	 * Returns the number of blobs found in the report. Use to ensure index is
	 * range for accessing blob properties
	 * 
	 * @return integer blobs detected
	 */
	public int getBlobsCount() {
		double[] defaultValue = new double[0];
		double[] xCenters = blobsReport.getNumberArray("centerX", defaultValue);
		return xCenters.length;
	}

	/**
	 * Returns the x coordinate for the center of blob chosen by index
	 * 
	 * Precondition: blobIndex is not out of range
	 * 
	 * @param blobIndex
	 * @return centerX in pixels
	 */
	public double getBlobCenterX(int blobIndex) {
		double[] defaultValue = new double[0];
		double[] xCenters = blobsReport.getNumberArray("x", defaultValue);
		return xCenters[blobIndex];
	}

	/**
	 * Returns the y coordinate for the center of blob chosen by index
	 * 
	 * Precondition: blobIndex is not out of range
	 * 
	 * @param blobIndex
	 * @return centerY in pixels
	 */
	public double getBlobCenterY(int blobIndex) {
		double[] defaultValue = new double[0];
		double[] yCenters = blobsReport.getNumberArray("y", defaultValue);
		return yCenters[blobIndex];
	}

	/**
	 * Returns the area of blob chosen by index
	 * 
	 * Precondition: blobIndex is not out of range
	 * 
	 * @param blobIndex
	 * @return area in square pixels
	 */
	public double getBlobArea(int blobIndex) {
		double[] defaultValue = new double[0];
		double[] areas = blobsReport.getNumberArray("size", defaultValue);
		return areas[blobIndex];
	}

	// Lines

	/**
	 * Updates the local linesReport table. WARNING, this has to be somewhat
	 * syncrhonized so one thing doesn't update the table while another tries to
	 * access an element that might not exist in the updated table
	 * 
	 * 
	 */
	public void updateLinesReport() {
		// A contours report contains x1[], y1[], x2[], y2[], length[], angle[]
		linesReport = NetworkTable.getTable("GRIP/myLinesReport");
	}

	/**
	 * Returns the number of lines found in the report. Use to ensure index is
	 * range for accessing line properties
	 * 
	 * 
	 * @return integer of lines detected
	 * 
	 */
	public int getLinesCount() {
		double[] defaultValue = new double[0];
		double[] xCenters = linesReport.getNumberArray("centerX", defaultValue);
		return xCenters.length;
	}

	// Not sure if grip always returns point 1 as the right or left one...

	/**
	 * Returns the x coordinate of the ???? point of line chosen by index
	 * 
	 * Precondition: lineIndex is not out of range
	 * 
	 * @param lineIndex
	 * @return x1 coordinate in pixels
	 */
	public double getLineX1(int lineIndex) {
		double[] defaultValue = new double[0];
		double[] x1s = linesReport.getNumberArray("x1", defaultValue);
		return x1s[lineIndex];
	}

	/**
	 * Returns the y coordinate of the ???? point of line chosen by index
	 * 
	 * Precondition: lineIndex is not out of range
	 * 
	 * @param lineIndex
	 * @return y1 coordinate in pixels
	 */
	public double getLineY1(int lineIndex) {
		double[] defaultValue = new double[0];
		double[] y1s = linesReport.getNumberArray("y1", defaultValue);
		return y1s[lineIndex];
	}

	/**
	 * Returns the x coordinate of the ???? point of line chosen by index
	 * 
	 * Precondition: lineIndex is not out of range
	 * 
	 * @param lineIndex
	 * @return x2 coordinate in pixels
	 */
	public double getLineX2(int lineIndex) {
		double[] defaultValue = new double[0];
		double[] x2s = linesReport.getNumberArray("x2", defaultValue);
		return x2s[lineIndex];
	}

	/**
	 * Returns the y coordinate of the ???? point of line chosen by index
	 * 
	 * Precondition: lineIndex is not out of range
	 * 
	 * @param lineIndex
	 * @return y2 coordinate in pixels
	 */
	public double getLineY2(int lineIndex) {
		double[] defaultValue = new double[0];
		double[] y2s = linesReport.getNumberArray("y2", defaultValue);
		return y2s[lineIndex];
	}

}
