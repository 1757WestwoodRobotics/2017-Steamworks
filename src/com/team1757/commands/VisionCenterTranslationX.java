package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author ACabey
 */
public class VisionCenterTranslationX extends Command {

    public VisionCenterTranslationX() {
        requires(Robot.vision);
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.vision.enableTranslationPID();
    	Robot.driveTrain.enableGyroPID();
    	Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.moveWithGyroPID(-Robot.vision.getTranslationPID(), 0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.vision.reachedSetPoint();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.vision.disableCenterPID();
    	Robot.driveTrain.disableGyroPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
