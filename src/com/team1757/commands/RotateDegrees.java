package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Rotate the shortest direction relative to current angular position
 * 
 * Default timeout of 0.5s
 * 
 * Times out due to problems where we fail to reach the setpoint due to physical
 * barriers, resulting in a stuck robot
 */
public class RotateDegrees extends TimedCommand {

	private static final double TIMEOUT = 0.5;

	private boolean useSmartDashboard;
	private double deltaDegrees;

	/**
	 * No parameters explicit. Pulls delta from SmartDashboard and uses default
	 * timeout of .s
	 */
	public RotateDegrees() {
		super(TIMEOUT);
		this.useSmartDashboard = true;
		requires(Robot.driveTrain);
	}

	/**
	 * Explicitly provide deltaDegrees. Uses default timeout of .5s
	 * 
	 * @param deltaDegrees
	 *            Desired angle relative to current (degrees)
	 */
	public RotateDegrees(double deltaDegrees) {
		super(TIMEOUT);
		this.useSmartDashboard = false;
		this.deltaDegrees = deltaDegrees;
		requires(Robot.driveTrain);
	}

	/**
	 * All parameters explicit. If useSmartDashboard is true, provided
	 * targetAngle has no effect
	 * 
	 * 
	 * @param useSmartDashboard
	 *            Whether or not to pull the targetAngle from the smartDashboard
	 * @param deltaDegrees
	 *            Desired angle relative to current (degrees). No effect if
	 *            useSmartDashboard is true
	 * @param timeout
	 */
	public RotateDegrees(boolean useSmartDashboard, double deltaDegrees, double timeout) {
		super(timeout);
		this.useSmartDashboard = useSmartDashboard;
		this.deltaDegrees = deltaDegrees;
		requires(Robot.driveTrain);
	}

	// Called once before execute
	protected void initialize() {
		Robot.driveTrain.enableGyroPID();

		if (useSmartDashboard) {
			Robot.driveTrain.setTargetAngle(
					Robot.driveTrain.getCurrentBoundedAngle() + SmartDashboard.getNumber("angularDelta", 0));
		} else {
			Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle() + this.deltaDegrees);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Update motor output
		Robot.driveTrain.moveToTargetAngle();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.driveTrain.reachedGyroSetpoint();
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
