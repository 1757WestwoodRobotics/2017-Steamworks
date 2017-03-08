package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    	if (Robot.oi.getLeftTrigger() > 0.2) {
    		if (targetLifterPVbus > 0) {
    			targetLifterPVbus = targetLifterPVbus - 0.01;
    		}
    	} else if (Robot.oi.getRightTrigger() > 0.2) {
    		if (targetLifterPVbus < 1) {
    			targetLifterPVbus = targetLifterPVbus + 0.01;
    		}
    	}
    	
    	SmartDashboard.putNumber("targetLifterPVbus", targetLifterPVbus);
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
