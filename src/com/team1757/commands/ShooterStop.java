package com.team1757.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.team1757.robot.Robot;

/**
 *
 */
public class ShooterStop extends Command {

    public ShooterStop() {
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.initializeFlyWheelPID();
    	Robot.shooter.setFlyWheelModeSpeed();
    	Robot.shooter.enableFlyWheel();
    	Robot.shooter.enableFlyWheelControl();
    	Robot.shooter.setFlyWheelTarget(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.setFlyWheelTarget(0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.disableFlyWheelControl();
    	Robot.shooter.disableFlyWheel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
