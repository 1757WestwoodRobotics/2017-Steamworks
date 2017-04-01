package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Switches streaming between the gear camera and the shooter camera
 * 
 * @author Ryan Marten
 */
public class CameraToggleStream extends Command {

	public CameraToggleStream() {

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.vision.toggleVisionCamera();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
