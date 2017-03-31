package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.utils.LifterControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Operate Lifter mechanism.
 * 
 * Defaults to 0.75 in PercentVBus mode
 * 
 * @author ACabey
 */

public class Lift extends Command {
	
	private LifterControlMode controlMode = LifterControlMode.kUp;
	private double targetLifterPVbus;
	
	public Lift() {
        requires(Robot.lifter);
        targetLifterPVbus = controlMode.getOutput();
    }
	
    public Lift(LifterControlMode controlMode) {
        requires(Robot.lifter);
        this.controlMode = controlMode;
        targetLifterPVbus = controlMode.getOutput();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lifter.enableLifter();
    	Robot.lifter.setModePercentVoltage();
    	this.targetLifterPVbus = controlMode.getOutput();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (controlMode == LifterControlMode.kUp) {
        	if (Robot.oi.getRightTrigger() > 0.2) {
        		targetLifterPVbus = 1.0;
        	} else {
        		targetLifterPVbus = .75;
        	}
    	}
    	
    	Robot.lifter.setLiftTarget(targetLifterPVbus);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lifter.stopLifter();
    	Robot.lifter.disableLifter();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
