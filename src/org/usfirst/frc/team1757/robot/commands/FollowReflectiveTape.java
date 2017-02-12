package org.usfirst.frc.team1757.robot.commands;

import org.usfirst.frc.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turn towards 3M reflective tape so it is centered in the camera field of view
 */
public class FollowReflectiveTape extends Command {

	public FollowReflectiveTape() {
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveTrain.enableGyroPID();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Update vision target
		Robot.vision.updateContoursReport();
		
		double targetX = Robot.vision.getContourCenterX(0);

		// Update motor output
		Robot.driveTrain.setTargetAngle(0);
		Robot.driveTrain.moveToTargetAngle();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
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
