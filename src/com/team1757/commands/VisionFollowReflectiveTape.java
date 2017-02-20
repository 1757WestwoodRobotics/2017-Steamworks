package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turn towards 3M reflective tape so it is centered in the camera field of view
 */
public class VisionFollowReflectiveTape extends Command {

	public VisionFollowReflectiveTape() {
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Using VisionPID with camera
		// Robot.vision.enableCenterPID();

		// Using GyroPID with camera
		Robot.driveTrain.enableGyroPID();
		Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle() + (Robot.vision.getTargetCenterContour() * 19.82));
//		SmartDashboard.putNumber("Vision center contour", Robot.vision.getTargetCenterContour());
//		SmartDashboard.putNumber("Vision angular center contour", Robot.vision.getTargetCenterContour() * 19.82);
//		SmartDashboard.putNumber("Vision target angle", Robot.driveTrain.getCurrentBoundedAngle() + (Robot.vision.getTargetCenterContour() * 18.55));
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Update motor output

		// Using VisionPID with camera
		// Robot.driveTrain.manualDrive(Robot.oi.getTranslateX(),
		// Robot.oi.getTranslateY(), -Robot.vision.getCenterPID());
		// SmartDashboard.putNumber("visionPIDOutput",
		// -Robot.vision.getCenterPID());

		// Using gyroPID with scaled camera
		Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle() + (Robot.vision.getTargetCenterContour() * 19.82));
		
		Robot.driveTrain.moveWithGyroPID(Robot.oi.getTranslateX(), Robot.oi.getTranslateY());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		// Using VisionPID with camera
		// Robot.vision.disableCenterPID();

		// Using GyroPID with camera
		Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
		Robot.driveTrain.disableGyroPID();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
