package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.utils.LifterControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lift extends Command {
	
	private LifterControlMode controlMode;
	private double targetLifterPVbus;

    public Lift(LifterControlMode controlMode) {
        requires(Robot.lifter);
        this.controlMode = controlMode;
        targetLifterPVbus = controlMode.getOutput();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lifter.enableLifter();
    	Robot.lifter.setModePercentVoltage();
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
