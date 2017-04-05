package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.utils.VariablePIDOutput;
import com.team1757.utils.VisionDetectionTarget;
import com.team1757.utils.VisionPIDTranslationInput;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Aligns the robot perpendicularly to the gear target using vision processing
 */
public class VisionAlignTargetPerpendicular extends Command {

	private VisionDetectionTarget target;
	private PIDController visionTranslationPIDController;

	public VisionAlignTargetPerpendicular(VisionDetectionTarget target) {
		requires(Robot.driveTrain);
		requires(Robot.vision);
		this.target = target;
		visionTranslationPIDController = new PIDController(target.getP(), target.getI(), target.getD(),
				new VisionPIDTranslationInput(target), new VariablePIDOutput());
		visionTranslationPIDController.setInputRange(target.getInputMin(), target.getInputMax());
		visionTranslationPIDController.setOutputRange(target.getOutputMin(), target.getOutputMax());
		visionTranslationPIDController.setAbsoluteTolerance(target.getTolerance());
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Low exposure to remove backlight
		Robot.camera.setExposureLow();

		// Using GyroPID with camera
		Robot.driveTrain.enableGyroPID();

		// Using TranslationGearPID with Camera
		visionTranslationPIDController.enable();
		Robot.driveTrain
				.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle() + Robot.vision.getTargetCenterAngle(target));
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Uses gyroPID to keep the target centered in the cameras view while
		// the robot strafes around the target so it is perpendicular (when the
		// areas of the two targets are equal)
		Robot.driveTrain
				.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle() + Robot.vision.getTargetCenterAngle(target));

		Robot.driveTrain.moveWithGyroPID(-visionTranslationPIDController.get(), Robot.oi.getTranslateY());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// return Robot.vision.reachedVisionGearTranslationSetpoint() &&
		// Robot.driveTrain.reachedGyroSetpoint();
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
		Robot.driveTrain.disableGyroPID();
		visionTranslationPIDController.disable();
		// Automatic exposure for driver
		Robot.camera.setExposureAuto();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
