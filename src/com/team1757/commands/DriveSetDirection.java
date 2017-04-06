package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.utils.DirectionControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the direction for operator control inversion
 *
 * No default; must explicitly provide DirectionControlMode
 *
 * @author ACabey
 */
public class DriveSetDirection extends Command {

	private DirectionControlMode controlMode;
	
	public DriveSetDirection(DirectionControlMode controlMode) {
		this.controlMode = controlMode;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (controlMode == DirectionControlMode.kToggle) {
			Robot.driveTrain.toggleInversion();
		}
		else if (controlMode == DirectionControlMode.kDropGear) {
			Robot.driveTrain.setInversionForward();
		}
		else {
			Robot.driveTrain.setInversionBackward();
		}
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
