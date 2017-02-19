package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GearManualInput extends Command {

    public GearManualInput() {
    	requires(Robot.gearLoader);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gearLoader.setTalonPID();
    	Robot.gearLoader.initEncoder();
    	Robot.gearLoader.enableGearTalon();
    	Robot.gearLoader.enableGearPIDControl();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gearLoader.setTargetPosition(SmartDashboard.getNumber("Gear Manual Target Position", 0));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.gearLoader.reachedSetpoint();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gearLoader.disableGearPIDControl();
    	Robot.gearLoader.disableGearTalon();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
