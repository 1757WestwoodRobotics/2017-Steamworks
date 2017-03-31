package com.team1757.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Sets the target angle for gyroscope PID controller via SmartDashboard
 */
public class RotateSetTargetAngle extends Command {

	private double target = 0;
	
    public RotateSetTargetAngle(double target) {
    	this.target = target;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putNumber("targetAngle", target);
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
