package org.usfirst.frc.team1757.robot.commands;

import org.usfirst.frc.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author ACabey
 * 
 * Drive a given distance in the X direction using the accelerometer and PID controller
 */
public class DriveDistanceAccelX extends Command {

    public DriveDistanceAccelX() {
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Reset for delta displacement
    	Robot.driveTrain.resetAccel();
    	
    	Robot.driveTrain.enableAccelPIDX();
    	Robot.driveTrain.setTargetDistanceX(SmartDashboard.getNumber("distanceDeltaX", 0));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.moveWithAccelPID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveTrain.reachedAccelSetpointX();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.disableAccelPIDX();
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
