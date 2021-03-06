package com.team1757.commands;

import com.team1757.robot.Robot;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Rotate directly to a given angle
 */
public class RotateToAngle extends TimedCommand {

	public RotateToAngle(double timeout) {
		super(timeout);
		requires(Robot.driveTrain);
	}

	// Called once before execute
	protected void initialize() {
		Robot.driveTrain.enableGyroPID();
		// Angle from SmartDash, default is the robots current angle
		double targetAngle = SmartDashboard.getNumber("targetAngle", Robot.driveTrain.getCurrentBoundedAngle());
		System.out.println(targetAngle);
		Robot.driveTrain.setTargetAngle(targetAngle);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Update motor output
		Robot.driveTrain.moveToTargetAngle();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// TODO Use onTarget() provided by controller
		return Robot.driveTrain.reachedGyroSetpoint() || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		// TODO Default to something reasonable
		Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
		Robot.driveTrain.disableGyroPID();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
