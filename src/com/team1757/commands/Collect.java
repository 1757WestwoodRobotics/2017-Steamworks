package com.team1757.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.team1757.robot.Robot;
import com.team1757.utils.CollectorControlMode;

/**
 * Operate Collector mechanism.
 * 
 * Defaults to 1.0 in PercentVBus mode
 * 
 * @author ACabey
 */

public class Collect extends Command {
	
	private CollectorControlMode controlMode = CollectorControlMode.kPercentForward;
	
	public Collect() {
        requires(Robot.ballCollector);
    }
	
    public Collect(CollectorControlMode controlMode) {
        requires(Robot.ballCollector);
        this.controlMode = controlMode;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ballCollector.changeControlMode(controlMode.getControlMode());
    	Robot.ballCollector.enableFlyWheel();
    	Robot.ballCollector.enableFlyWheelControl();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.ballCollector.setFlyWheelTarget(controlMode.getOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ballCollector.stopFlyWheel();
    	Robot.ballCollector.disableFlyWheelControl();
    	Robot.ballCollector.disableFlyWheel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
