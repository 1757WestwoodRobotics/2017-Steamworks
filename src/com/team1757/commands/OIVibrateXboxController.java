package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OIVibrateXboxController extends Command {
	
	private RumbleType type;
	private double value;

    public OIVibrateXboxController(RumbleType type, double value) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.type = type;
    	this.value = value;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.oi.vibrateXboxController(type, value);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
