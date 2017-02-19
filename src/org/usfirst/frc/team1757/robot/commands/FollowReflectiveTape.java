package org.usfirst.frc.team1757.robot.commands;

import org.usfirst.frc.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn towards 3M reflective tape so it is centered in the camera field of view
 */
public class FollowReflectiveTape extends Command {

	public FollowReflectiveTape() {
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.vision.enableCenterPID();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Update motor output
		Robot.driveTrain.manualDrive(Robot.oi.getTranslateX(), Robot.oi.getTranslateY(), -Robot.vision.getCenterPID());
		SmartDashboard.putNumber("visionPIDOutput", -Robot.vision.getCenterPID());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		// TODO Default to something reasonable
		Robot.vision.disableCenterPID();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
