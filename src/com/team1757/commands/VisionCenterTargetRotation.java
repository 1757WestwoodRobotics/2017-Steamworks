package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.utils.VisionDetectionTarget;

import edu.wpi.first.wpilibj.command.Command;


public class VisionCenterTargetRotation extends Command {
	
	private VisionDetectionTarget target;

	public VisionCenterTargetRotation(VisionDetectionTarget target) {
		requires(Robot.driveTrain);
		this.target = target;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
		// Low exposure to remove backlight
		Robot.camera.setExposureLow();

		// Using GyroPID with camera
		Robot.driveTrain.enableGyroPID();
		Robot.driveTrain.setTargetAngle(
				Robot.driveTrain.getCurrentBoundedAngle() + Robot.vision.getTargetCenterAngle(target));
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Using gyroPID with scaled camera
		Robot.driveTrain.moveToTargetAngle();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		//return Robot.driveTrain.reachedGyroSetpoint();
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		// Using GyroPID with camera
		Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
		Robot.driveTrain.disableGyroPID();
		// Automatic exposure for driver
		Robot.camera.setExposureAuto();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
