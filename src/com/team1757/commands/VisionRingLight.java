package com.team1757.commands;

import com.team1757.utils.RingLightControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Operate ring lights
 * 
 * No default; must explicitly provide control mode
 * 
 * @author ACabey
 */
public class VisionRingLight extends Command {

	// TODO Implement the controller and an Arduino subsystem
	
	@SuppressWarnings("unused")
	private RingLightControlMode controlMode;
	
    public VisionRingLight(RingLightControlMode controlMode) {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
