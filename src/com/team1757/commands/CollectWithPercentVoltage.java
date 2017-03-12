package com.team1757.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.team1757.robot.Robot;

/**
 *
 */
public class CollectWithPercentVoltage extends Command {

    public CollectWithPercentVoltage() {
        requires(Robot.ballCollector);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ballCollector.setModePercentVoltage();
    	Robot.ballCollector.enableFlyWheel();
    	Robot.ballCollector.enableFlyWheelControl();
    	SmartDashboard.putBoolean("isCollecting", true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.ballCollector.setFlyWheelTarget(-1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ballCollector.disableFlyWheelControl();
    	Robot.ballCollector.disableFlyWheel();
    	SmartDashboard.putBoolean("isCollecting", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
