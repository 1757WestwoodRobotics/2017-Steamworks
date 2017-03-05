package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftUp extends Command {
	
	private static double targetLifterPVbus = 0.75; 

    public LiftUp() {
        requires(Robot.lifter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lifter.enableLifter();
    	Robot.lifter.setModePercentVoltage();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.oi.getXbox360().getRawAxis(Robot.oi.getXboxAxisLeftTrigger()) > 0.2) {
    		targetLifterPVbus--;
    	} else if (Robot.oi.getXbox360().getRawAxis(Robot.oi.getXboxAxisRightTrigger()) > 0.2) {
    		targetLifterPVbus++;
    	}
    	
    	Robot.lifter.setLiftTarget(targetLifterPVbus);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
