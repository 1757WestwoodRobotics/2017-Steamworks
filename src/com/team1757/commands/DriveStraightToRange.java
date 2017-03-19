package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.utils.Unit;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive to a given ultrasonic range
 * 
 * Translation Y controller by ultrasonic rangefinder
 * Rotation controlled by gyroscope
 */
public class DriveStraightToRange extends Command {
	
	private double targetDistance = 0.0;
	private Unit distanceUnit = Unit.kInches;
	
    public DriveStraightToRange(double targetDistance) {
        requires(Robot.driveTrain);
        this.targetDistance = targetDistance;
    }
    
    public DriveStraightToRange(double targetDistance, Unit unit) {
        requires(Robot.driveTrain);
        this.targetDistance = targetDistance;
        this.distanceUnit = unit;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.enableGyroPID();
    	Robot.driveTrain.enableUltrasonicPID();
    	Robot.driveTrain.setUltrasonicDefaultUnit(distanceUnit);
    	Robot.driveTrain.setUltrasonicTargetDistance(targetDistance);
    	Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.moveWithUltrasonicGyroPID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.disableUltrasonicPID();
    	Robot.driveTrain.disableGyroPID();
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
