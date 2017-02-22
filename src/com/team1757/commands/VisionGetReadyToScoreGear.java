package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionGetReadyToScoreGear extends Command {

	public VisionGetReadyToScoreGear() {
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Using GyroPID with camera
		Robot.driveTrain.enableGyroPID();
		
		// Using TranslationGearPID with Camera
		Robot.vision.enableGearTranslationPID();
		
		Robot.driveTrain.setTargetAngle(
				Robot.driveTrain.getCurrentBoundedAngle() + (Robot.vision.getGearTargetCenter() * 19.82));
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Using gyroPID with scaled camera
		Robot.driveTrain.setTargetAngle(
				Robot.driveTrain.getCurrentBoundedAngle() + (Robot.vision.getGearTargetCenter() * 19.82));
		SmartDashboard.putNumber("TranslationWithTrackingGear", -Robot.vision.getGearTranslationPID());
		Robot.driveTrain.moveWithGyroPID(-Robot.vision.getGearTranslationPID(), Robot.oi.getTranslateY());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		//return Robot.vision.reachedVisionGearTranslationSetpoint() && Robot.driveTrain.reachedGyroSetpoint();
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
		Robot.driveTrain.disableGyroPID();
		Robot.vision.disableGearTranslationPID();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}