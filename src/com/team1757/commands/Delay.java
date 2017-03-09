package com.team1757.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class Delay extends TimedCommand {

    public Delay(double timeout) {
        super(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isTimedOut();
    }
    
    // Called once after timeout
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
