package org.usfirst.frc.team1757.robot.subsystems;

import java.util.Arrays;

import org.usfirst.frc.team1757.robot.RobotMap;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Vision extends Subsystem {

	private final UsbCamera camera = RobotMap.camera;
	private NetworkTable contoursReport;

	public void initDefaultCommand() {

	}

	/**
	 * Updates the local contoursReport table NEED to call this, other methods
	 * do not update the local table so that index errors don't arise
	 */
	public void updateContoursReport() {
		// A contours report contains centerX[], centerY[], solidity[],
		// height[], area[], and width[]
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
	 * @return centerX
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
	 * @return centerY
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
	 * @return height
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
	 * @return area
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
	 * @return width
	 */
	public double getContourWith(int contourIndex) {
		double[] defaultValue = new double[0];
		double[] widths = contoursReport.getNumberArray("width", defaultValue);
		return widths[contourIndex];
	}

}
