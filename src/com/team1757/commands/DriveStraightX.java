package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;
/**
 *
 */
public class DriveStraightX extends TimedCommand {

	private double velocity = 1.0;
	
    public DriveStraightX(double timeout) {
        super(timeout);
        requires(Robot.driveTrain);
    }
    
    public DriveStraightX(double timeout, double velocity) {
        super(timeout);
        requires(Robot.driveTrain);
        this.velocity = velocity;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.enableGyroPID();
    	Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.moveWithGyroPID(velocity, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.disableGyroPID();
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
