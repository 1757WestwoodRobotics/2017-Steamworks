package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.utils.VariablePIDOutput;
import com.team1757.utils.VisionDetectionTarget;
import com.team1757.utils.VisionPIDTranslationInput;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

public class VisionCenterTargetTranslation extends Command {

	private PIDController visionTranslationPIDController;

	public VisionCenterTargetTranslation(VisionDetectionTarget target) {
		requires(Robot.vision);
		requires(Robot.driveTrain);
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

		// Enable PID controllers
		visionTranslationPIDController.enable();
		Robot.driveTrain.enableGyroPID();
		Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveTrain.moveWithGyroPID(-visionTranslationPIDController.get(), Robot.oi.getTranslateY());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		//return Robot.vision.reachedVisionGearTranslationSetpoint();
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		visionTranslationPIDController.disable();
		Robot.driveTrain.disableGyroPID();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
