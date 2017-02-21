package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author ACabey
 */
public class VisionCenterOnGearTranslationX extends Command {

	public VisionCenterOnGearTranslationX() {
		requires(Robot.vision);
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.vision.enableGearTranslationPID();
		Robot.driveTrain.enableGyroPID();
		Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
//		if(Robot.vision.reachedVisionGearTranslationSetpoint()){
//			Robot.driveTrain.moveWithGyroPID(0, Robot.oi.getTranslateY());
//		}else {
//			Robot.driveTrain.moveWithGyroPID(-Robot.vision.getGearTranslationPID(), Robot.oi.getTranslateY());
//		}
		
		Robot.driveTrain.moveWithGyroPID(-Robot.vision.getGearTranslationPID(), Robot.oi.getTranslateY());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// return Robot.vision.reachedSetPoint();
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.vision.disableGearTranslationPID();
		Robot.driveTrain.disableGyroPID();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
