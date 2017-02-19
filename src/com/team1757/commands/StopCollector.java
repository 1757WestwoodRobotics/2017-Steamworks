package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StopCollector extends Command {

    public StopCollector() {
        requires(Robot.ballCollector);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ballCollector.initializeFlyWheelPID();
    	Robot.ballCollector.setModePercentVoltage();
    	Robot.ballCollector.enableFlyWheel();
    	Robot.ballCollector.enableFlyWheelControl();
    	Robot.ballCollector.setFlyWheelTarget(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.ballCollector.setFlyWheelTarget(0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ballCollector.disableFlyWheelControl();
    	Robot.ballCollector.disableFlyWheel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
