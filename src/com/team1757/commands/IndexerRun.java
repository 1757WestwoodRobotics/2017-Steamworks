package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IndexerRun extends Command {

    public IndexerRun() {
    	requires(Robot.indexer);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.indexer.setIndexerModePercentVoltage();
    	Robot.indexer.enableIndexer();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.indexer.setIndexerTarget(.55);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.indexer.disableIndexer();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
