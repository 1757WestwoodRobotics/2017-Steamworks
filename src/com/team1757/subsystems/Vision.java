package com.team1757.subsystems;

import com.team1757.robot.RobotMap;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class Vision extends Subsystem {

	private final UsbCamera camera = RobotMap.camera;
	private final PIDController visionTranslationController = RobotMap.visionTranslationController;
	private final PIDController visionGearTranslationController = RobotMap.visionGearTranslationController;
	private NetworkTable contoursReport;
	private NetworkTable blobsReport;
	private NetworkTable linesReport;
	private int xResolution = 160;
	private int yResolution = 120;
	private int fps = 30;

	public void initDefaultCommand() {
	}

	// PID Translation Controllers

	public void enableTranslationPID() {
		visionTranslationController.enable();
	}

	public void disableTranslationPID() {
		visionTranslationController.disable();
	}

	public boolean reachedVisionTranslationSetPoint() {
		return visionTranslationController.onTarget();
	}

	public double getTranslationPID() {
		return visionTranslationController.get();
	}

	public void enableGearTranslationPID() {
		visionGearTranslationController.enable();
	}

	public void disableGearTranslationPID() {
		visionGearTranslationController.disable();
	}

	public boolean reachedVisionGearTranslationSetpoint() {
		return visionGearTranslationController.onTarget();
	}

	public double getGearTranslationPID() {
		return visionGearTranslationController.get();
	}

	// Math operations

	public double normalizePixelsX(double coordinateX) {
		return coordinateX * (2.0f / xResolution) - 1;
	}

	public double normalizePixelsY(double coordinateY) {
		return coordinateY * (2.0f / yResolution) - 1;
	}

	// Camera configuration

	public void init() {
		camera.setFPS(fps);
		camera.setResolution(xResolution, yResolution);
	}

	public void setResolution(int x, int y) {
		xResolution = x;
		yResolution = y;
		camera.setResolution(x, y);
	}

	public int getResolutionX() {
		return xResolution;
	}

	public int getResolutionY() {
		return yResolution;
	}

	public void setFPS(int fps) {
		this.fps = fps;
		camera.setFPS(fps);
	}

	public int getFPS() {
		return fps;
	}

	// Contours

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

	public double getGearTargetCenter() {
		// DOESN'T UPDATE because this only runs when the PIDcontroller using
		// getDifferenceInAreas is running
		// And arrayIndexOutofBounds errors arise when multiple things are
		// updating tables so the index checking doesn't do its job
		if (getContoursCount() == 2) {

			// Center between the two targets
			return normalizePixelsX((getContourCenterX(0) + getContourCenterX(1)) / 2);

		}
		return 0;
	}

	public double getTargetCenterContour() {
		updateContoursReport();
		if (getContoursCount() != 0) {
			return normalizePixelsX(getContourCenterX(0));
		}
		return 0;
	}

	/**
	 * Updates the local contoursReport table.
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
	 * Updates the local blobsReport table.
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
	 * Updates the local linesReport table.
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

	// TODO Check if it is the left point always
	/**
	 * Returns the x coordinate of the ?left? point of line chosen by index
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

	// TODO Check if it is the left point always
	/**
	 * Returns the y coordinate of the ?left? point of line chosen by index
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

	// TODO Check if it is the right point always
	/**
	 * Returns the x coordinate of the ?right? point of line chosen by index
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

	// TODO Check if it is the left point always
	/**
	 * Returns the y coordinate of the ?right? point of line chosen by index
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
