package com.team1757.commands;

import com.team1757.robot.Robot;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Rotate directly to a given angle
 * 
 * Default timeout of 1.0s
 * 
 * Times out due to problems where we fail to reach the setpoint due to physical
 * barriers, resulting in a stuck robot
 */
public class RotateToAngle extends TimedCommand {

	private static final double TIMEOUT = 1.0;

	private boolean useSmartDashboard = true;
	private double targetAngle;

	/**
	 * No parameters explicit. Pulls target angle from SmartDashboard and uses
	 * default timeout of 1.0s
	 */
	public RotateToAngle() {
		super(TIMEOUT);
		this.useSmartDashboard = true;
		requires(Robot.driveTrain);
	}

	/**
	 * Explicitly provide targetAngle. Uses default timeout of .5s
	 * 
	 * @param targetAngle
	 *            Desired absolute angle (degrees)
	 */
	public RotateToAngle(double targetAngle) {
		super(TIMEOUT);
		this.useSmartDashboard = false;
		this.targetAngle = targetAngle;
		requires(Robot.driveTrain);
	}

	/**
	 * All parameters explicit. If useSmartDashboard is true, provided
	 * targetAngle has no effect
	 * 
	 * 
	 * @param useSmartDashboard
	 *            Whether or not to pull the targetAngle from the smartDashboard
	 * @param targetAngle
	 *            Desired absolute angle (degrees) No effect if
	 *            useSmartDashboard is true
	 * @param timeout
	 */
	public RotateToAngle(boolean useSmartDashboard, double targetAngle, double timeout) {
		super(timeout);
		this.useSmartDashboard = useSmartDashboard;
		this.targetAngle = targetAngle;
		requires(Robot.driveTrain);
	}

	// Called once before execute
	protected void initialize() {
		Robot.driveTrain.enableGyroPID();

		if (useSmartDashboard) {
			Robot.driveTrain.setTargetAngle(SmartDashboard.getNumber("targetAngle", Robot.driveTrain.getCurrentBoundedAngle()));
		} else {
			Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle() + this.targetAngle);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Update motor output
		Robot.driveTrain.moveToTargetAngle();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.driveTrain.reachedGyroSetpoint() || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
		Robot.driveTrain.disableGyroPID();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
