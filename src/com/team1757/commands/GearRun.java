package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearRun extends Command {
	
    public GearRun() {
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
    	Robot.gearLoader.runGearTalon();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
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