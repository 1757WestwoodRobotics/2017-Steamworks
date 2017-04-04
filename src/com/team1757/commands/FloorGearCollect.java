package com.team1757.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.team1757.robot.Robot;
import com.team1757.utils.FloorGearCollectorControlMode;

/**
 * Operate Floor Gear Collector mechanism.
 * 
 * Defaults to .5 in PercentVBus mode
 * 
 * @author ACabey
 */

public class FloorGearCollect extends Command {
	
	private FloorGearCollectorControlMode controlMode = FloorGearCollectorControlMode.kIntake;
	
	public FloorGearCollect() {
        requires(Robot.ballCollector);
    }
	
    public FloorGearCollect(FloorGearCollectorControlMode controlMode) {
        requires(Robot.ballCollector);
        this.controlMode = controlMode;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.floorGearLoader.changeCollectorControlMode(controlMode.getControlMode());
    	Robot.floorGearLoader.enableCollector();
    	Robot.floorGearLoader.enableCollectorControl();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.floorGearLoader.setCollectorTarget(controlMode.getOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.floorGearLoader.stopCollector();
    	Robot.floorGearLoader.disableCollectorControl();
    	Robot.floorGearLoader.disableCollector();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
