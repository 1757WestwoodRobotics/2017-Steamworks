package com.team1757.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.team1757.robot.Robot;

/**
 *
 */
public class CollectWithSpeed extends Command {

    public CollectWithSpeed() {
        requires(Robot.ballCollector);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ballCollector.setModeSpeed();
    	Robot.ballCollector.enableFlyWheel();
    	Robot.ballCollector.enableFlyWheelControl();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// TODO Give a real target speed
    	Robot.ballCollector.setFlyWheelTarget(0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
