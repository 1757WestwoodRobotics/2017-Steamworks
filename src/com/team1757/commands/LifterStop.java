package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LifterStop extends Command {
	
	private static double targetLifterPVbus = 0; 

    public LifterStop() {
        requires(Robot.lifter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lifter.enableLifter();
    	Robot.lifter.setModePercentVoltage();
    	Robot.lifter.setLiftTarget(targetLifterPVbus);
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
    	Robot.lifter.disableLifter();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
