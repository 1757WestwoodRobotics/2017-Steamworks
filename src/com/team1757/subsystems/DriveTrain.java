package com.team1757.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.team1757.commands.DriveManual;
import com.team1757.robot.RobotMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDController;

/**
 * @author ACabey
 */

public class DriveTrain extends Subsystem {
	private final AHRS driveTrainNavX = RobotMap.driveTrainNavX;
	private final PIDController gyroController = RobotMap.gyroController;
	private final PIDController accelControllerX = RobotMap.accelControllerX;
	private final PIDController accelControllerY = RobotMap.accelControllerY;
	private final RobotDrive driveTrainMecanumDrive = RobotMap.driveTrainMecanumDrive;
	private final double GYRO_PID_TOLERANCE = .5;
	private final double ACCEL_PID_TOLERANCE = .05;
	private boolean isInverted = false;

	public void initDefaultCommand() {
		setDefaultCommand(new DriveManual());
	}

	// Motor

	/**
	 * Toggle direction of robot drive by inverting motor directions
	 * 
	 * Left side inverted (default) is Collector forward Right side inverted is
	 * Gear Loader forward
	 */
	public void toggleInversion() {
		isInverted = !isInverted;
	}
	
	/**
	 * Set direction to forward (left inverted)
	 * 
	 * Left side inverted (default) is Collector forward Right side inverted is
	 * Gear Loader forward
	 */
	public void setInversionForward() {
		isInverted = false;
	}
	
	/**
	 * Set direction to inverted (right inverted)
	 * 
	 * Left side inverted (default) is Collector forward Right side inverted is
	 * Gear Loader forward
	 */
	public void setInversionBackward() {
		isInverted = true;
	}

	/**
	 * Get drivetrain is inverted (Gear Loader forward)
	 * 
	 * @return isInverted
	 */
	public boolean getInverted() {
		return isInverted;
	}

	/**
	 * Manual Cartesian Drive
	 * 
	 * @param translateX
	 *            Generally the (normalized) X input of a gamepad, [-1, 1]
	 *            Positive values correspond to translation to the right
	 * @param translateY
	 *            Generally the (normalized) Y input of a gamepad, [-1, 1]
	 *            Negative values correspond to translation forward
	 * @param rotate
	 *            Generally the (normalized) X input of a gamepad (right stick),
	 *            [-1, 1] Positive values correspond to rotation to the right
	 */
	public void manualDrive(double translateX, double translateY, double rotate) {
		if (isInverted) {
			driveTrainMecanumDrive.mecanumDrive_Cartesian(-translateX, -translateY, rotate, 0);
		} else {
			driveTrainMecanumDrive.mecanumDrive_Cartesian(translateX, translateY, rotate, 0);
		}

	}

	/**
	 * Motor update based on gyroscope PID output
	 * 
	 * Updates motors solely based off of gyroscope PID Controller in order to
	 * reach an angular setpoint
	 */
	public void moveToTargetAngle() {
		manualDrive(0, 0, gyroController.get());
	}

	/**
	 * Gyroscope PID Assisted Drive
	 * 
	 * Updates motors using provided translation input and gyroscope PID
	 * Controller to maintain an angular position while translating.
	 * 
	 * @param translateX
	 *            Generally the (normalized) X input of a gamepad, [-1, 1]
	 *            Positive values correspond to translation to the right
	 * @param translateY
	 *            Generally the (normalized) Y input of a gamepad, [-1, 1]
	 *            Negative values correspond to translation forward
	 */
	public void moveWithGyroPID(double translateX, double translateY) {
		manualDrive(translateX, translateY, gyroController.get());
	}

	/**
	 * Accelerometer PID Drive
	 * 
	 * Updates motors with accelerometer PID controller output
	 */
	public void moveWithAccelPID() {
		manualDrive(accelControllerX.get(), -accelControllerY.get(), 0);
	}

	/**
	 * Stop motors
	 * 
	 * Sets all motor controllers to 0
	 */
	public void stop() {
		driveTrainMecanumDrive.stopMotor();
	}

	// Gyroscope

	/**
	 * Reset gyroscope
	 * 
	 * Reset gyroscope angular reading to 0
	 */
	public void resetGyro() {
		driveTrainNavX.reset();
	}

	/**
	 * Get gyroscope bounded angular reading in range [0,360)
	 * 
	 * @return double angle in range [0,360)
	 */
	public double getCurrentBoundedAngle() {
		return (360 + (getCurrentRawAngle() % 360)) % 360;
	}

	/**
	 * Get gyroscope continuous (unbounded) angular reading in range
	 * (-MAX_DOUBLE, MAX_DOUBLE)
	 * 
	 * @return double angle in range (-MAX_DOUBLE, MAX_DOUBLE)
	 */
	public double getCurrentRawAngle() {
		return driveTrainNavX.getAngle();
	}

	// Accelerometer

	/**
	 * Reset accelerometer displacement
	 */
	public void resetAccel() {
		driveTrainNavX.resetDisplacement();
	}

	/**
	 * Get accelerometer Y axis displacement (meters) in range (-MAX_DOUBLE,
	 * MAX_DOUBLE)
	 * 
	 * @return double distance in range (-MAX_DOUBLE, MAX_DOUBLE) measured in
	 *         meters
	 */
	public double getCurrentDisplacementY() {
		return driveTrainNavX.getDisplacementY();
	}

	/**
	 * Get accelerometer X axis displacement (meters) in range (-MAX_DOUBLE,
	 * MAX_DOUBLE)
	 * 
	 * @return double distance in range (-MAX_DOUBLE, MAX_DOUBLE) measured in
	 *         meters
	 */
	public double getCurrentDisplacementX() {
		return driveTrainNavX.getDisplacementX();
	}

	// Gyroscope PID

	/**
	 * Enable gyroscope PID Controller
	 */
	public void enableGyroPID() {
		gyroController.enable();
	}

	/**
	 * Disable gyroscope PID Controller
	 */
	public void disableGyroPID() {
		gyroController.disable();
	}

	/**
	 * Get gyroscope PID Controller target angle (setpoint)
	 * 
	 * @return double angle in range (-MAX_DOUBLE, MAX_DOUBLE)
	 */
	public double getTargetAngle() {
		return gyroController.getSetpoint();
	}
	
	/**
	 * Gets the difference between current setpoint and current gyro angle (unbounded)
	 * @return anglular error in range (-MAX_DOUBLE, MAX_DOUBLE)
	 */
	public double getGyroControllerError(){
		return gyroController.getSetpoint() - getCurrentRawAngle();
	}

	/**
	 * Get gyroscope PID Controller reached setpoint within tolerance
	 * 
	 * @return boolean gyroscope PID controller error within constant tolerance
	 */
	public boolean reachedGyroSetpoint() {
		//return (Math.abs(gyroController.getError()) <= GYRO_PID_TOLERANCE); 
		//Unreliably works on second run of the command
		return Math.abs(getGyroControllerError()) <= GYRO_PID_TOLERANCE;
	}

	/**
	 * Set gyroscope PID Controller target angle (setpoint) to a given angle
	 * Calculates the shortest required travek distance for a desired angle
	 * 
	 * @param targetAngle
	 *            Double angle in range [0, 360)
	 */
	public void setTargetAngle(double targetAngle) {
		double currentBoundedAngle = getCurrentBoundedAngle();

		// Filter input if precondition not met
		if ((targetAngle < 0) || (targetAngle > 360)) {
			targetAngle = (360 + (targetAngle % 360)) % 360;
		}

		// Finds the bigger and smaller angles in terms of 0-360
		double bigger;
		double smaller;
		if (targetAngle > currentBoundedAngle) {
			bigger = targetAngle;
			smaller = currentBoundedAngle;
		} else {
			bigger = currentBoundedAngle;
			smaller = targetAngle;
		}

		// Calculates the two possible distances from bigger to smaller or
		// smaller to bigger
		double distanceA = bigger - smaller;
		double distanceB = smaller + 360 - bigger;
		double deltaAngle = 0;
		if (smaller == currentBoundedAngle && distanceA < distanceB) {
			deltaAngle = distanceA;
		} else if (smaller == currentBoundedAngle && distanceB < distanceA) {
			deltaAngle = -distanceB;
		} else if (bigger == currentBoundedAngle && distanceA < distanceB) {
			deltaAngle = -distanceA;
		} else if (bigger == currentBoundedAngle && distanceB < distanceA) {
			deltaAngle = distanceB;
		}

		// Rotate by the shorter distance
		changeAngleBy(deltaAngle);

	}

	/**
	 * Rotate by angular delta
	 * 
	 * @param deltaAngle
	 *            Double angle representing target delta
	 */
	public void changeAngleBy(double deltaAngle) {
		gyroController.setSetpoint(deltaAngle + getCurrentRawAngle());
	}

	// Accelerometer PID Y

	/**
	 * Enable accelerometer Y axis PID controller
	 */
	public void enableAccelPIDY() {
		accelControllerY.enable();
	}

	/**
	 * Disable accelerometer Y axis PID controller
	 */
	public void disableAccelPIDY() {
		accelControllerY.disable();
	}

	/**
	 * Get accelerometer Y axis PID controller target angle (setpoint)
	 * 
	 * @return double distance in range (-MAX_DOUBLE, MAX_DOUBLE)
	 */
	public double getTargetDistanceY() {
		return accelControllerY.getSetpoint();
	}

	/**
	 * Set accelerometer Y axis PID Controller target distance (setpoint) to a
	 * given distance (meters)
	 * 
	 * @param distanceY
	 *            Double distance in range (-MAX_DOUBLE, MAX_DOUBLE) measured in
	 *            meters
	 */
	public void setTargetDistanceY(double distanceY) {
		accelControllerY.setSetpoint(distanceY);
	}

	/**
	 * Get accelerometer Y axis PID Controller reached setpoint within tolerance
	 * 
	 * @return boolean accelerometer PID controller error within constant
	 *         tolerance
	 */
	public boolean reachedAccelSetpointY() {
		return (Math.abs(accelControllerY.getError()) <= ACCEL_PID_TOLERANCE);
	}

	// Accelerometer PID X

	/**
	 * Enable accelerometer X axis PID controller
	 */
	public void enableAccelPIDX() {
		accelControllerX.enable();
	}

	/**
	 * Disable accelerometer X axis PID controller
	 */
	public void disableAccelPIDX() {
		accelControllerX.disable();
	}

	/**
	 * Get accelerometer X axis PID controller target angle (setpoint)
	 * 
	 * @return double distance in range (-MAX_DOUBLE, MAX_DOUBLE)
	 */
	public double getTargetDistanceX() {
		return accelControllerX.getSetpoint();
	}

	/**
	 * Set accelerometer X axis PID Controller target distance (setpoint) to a
	 * given distance (meters)
	 * 
	 * @param distanceX
	 *            Double distance in range (-MAX_DOUBLE, MAX_DOUBLE) measured in
	 *            meters
	 */
	public void setTargetDistanceX(double distanceX) {
		accelControllerY.setSetpoint(distanceX);
	}

	/**
	 * Get accelerometer X axis PID Controller reached setpoint within tolerance
	 * 
	 * @return boolean accelerometer PID controller error within constant
	 *         tolerance
	 */
	public boolean reachedAccelSetpointX() {
		return (Math.abs(accelControllerX.getError()) <= ACCEL_PID_TOLERANCE);
	}

}
