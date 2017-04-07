package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.utils.Axis;

import edu.wpi.first.wpilibj.command.TimedCommand;
/**
 * Maintain Z axis with gyroscope's PID control while moving on the provided axis
 * 
 * Defaults to Y Axis
 * 
 * @author ACabey
 */
public class DriveStraightWithTrigger extends TimedCommand {

	private Axis axis = Axis.axisX;
	
	private double velocity = 1.0;
	
    public DriveStraightWithTrigger(Axis axis, double timeout) {
        super(timeout);
        this.axis = axis;
        requires(Robot.driveTrain);
    }
    
    public DriveStraightWithTrigger(Axis axis, double timeout, double velocity) {
        super(timeout);
        this.axis = axis;
        this.velocity = velocity;
        requires(Robot.driveTrain);
    }
	
    public DriveStraightWithTrigger(double timeout) {
        super(timeout);
        requires(Robot.driveTrain);
    }
    
    public DriveStraightWithTrigger(double timeout, double velocity) {
        super(timeout);
        this.velocity = velocity;
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.enableGyroPID();
    	Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (axis == Axis.axisX) {
    		Robot.driveTrain.moveWithGyroPID(velocity, 0);
    	}
    	else {
    		Robot.driveTrain.moveWithGyroPID(0, velocity);
    	}
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
