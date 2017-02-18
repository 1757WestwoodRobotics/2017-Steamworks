package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunIndexer extends Command {

    public RunIndexer() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.setIndexerModePercentVoltage();
    	Robot.shooter.enableIndexer();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.setIndexerTarget(.20);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.disableIndexer();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
