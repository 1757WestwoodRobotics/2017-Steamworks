package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.utils.Axis;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author ACabey
 * 
 * Translate a given distance where accelerometer PID controls translation and gyroscope PID rotation
 * 
 */
public class DriveDistanceAccel extends Command {
	
	private double deltaDistance;
	private Axis axis;
	
    public DriveDistanceAccel(Axis axis) {
    	requires(Robot.driveTrain);
    	this.axis = axis;
    	this.deltaDistance
    }
    
    public DriveDistanceAccel(Axis axis, double deltaDistance) {
    	requires(Robot.driveTrain);
    	this.axis = axis;
    	this.deltaDistance = deltaDistance;
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
