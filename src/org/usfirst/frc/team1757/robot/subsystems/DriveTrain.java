package org.usfirst.frc.team1757.robot.subsystems;

import org.usfirst.frc.team1757.robot.RobotMap;
import org.usfirst.frc.team1757.robot.commands.ManualDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDController;

public class DriveTrain extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	// TODO negative setpoint and edge case of 359 don't work as intended

	private final AHRS driveTrainNavX = RobotMap.driveTrainNavX;
	private final PIDController gyroController = RobotMap.gyroController;
	private final RobotDrive driveTrainMecanumDrive = RobotMap.driveTrainMecanumDrive;
	private double targetAngle;

	public void initDefaultCommand() {
		setDefaultCommand(new ManualDrive());
	}

	/**
	 * Motor
	 */

	public void manualDrive(double translateX, double translateY, double rotate) {
		driveTrainMecanumDrive.mecanumDrive_Cartesian(translateX, translateY, rotate, 0);
	}

	public void moveToTargetAngle() {
		driveTrainMecanumDrive.mecanumDrive_Cartesian(0, 0, gyroController.get(), 0);
	}

	public void stop() {
		driveTrainMecanumDrive.stopMotor();
	}

	/**
	 * Gyro
	 */
	public void resetGyro() {
		driveTrainNavX.reset();
	}

	/**
	 * Returns a 0-360 angle based off of the raw angle
	 * 
	 * @return
	 */
	public double getCurrentBoundedAngle() {
		return (360 + (getCurrentRawAngle() % 360)) % 360;
	}

	public double getCurrentRawAngle() {
		return driveTrainNavX.getAngle();
	}

	/**
	 * Gyro PID Controller
	 */

	public void enableGyroPID() {
		gyroController.enable();
	}

	public void disableGyroPID() {
		gyroController.disable();
	}

	public double getTargetAngle() {
		return targetAngle;

	}

	public boolean reachedSetpoint() {
		return (Math.abs(gyroController.getError()) <= .3);
	}

	/**
	 * Precondition targetAngle is positive and between 0 - 360
	 * 
	 * @param targetAngle
	 */
	public void setTargetAngle(double targetAngle) {
		double currentBoundedAngle = getCurrentBoundedAngle();
		double currentRawAngle = getCurrentRawAngle();
		// For idiots
		targetAngle = Math.abs(targetAngle % 360);

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

		// rotates the robot to the shorter distance
		changeAngleBy(deltaAngle);

	}

	public void changeAngleBy(double deltaAngle) {
		gyroController.setSetpoint(deltaAngle + getCurrentRawAngle());
	}
}
