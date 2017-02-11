package org.usfirst.frc.team1757.robot.commands;

import org.usfirst.frc.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Rotate an angular delta from the current reading
 */
public class RotateDegrees extends Command {
	
    public RotateDegrees() {
    	requires(Robot.driveTrain);
    }

    // Called once before execute
    protected void initialize() {
    	Robot.driveTrain.enableGyroPID();
    	// TODO Change the default angle to something more reasonable
    	Robot.driveTrain.changeAngleBy(SmartDashboard.getNumber("angularDelta", 0));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Update motor output
    	Robot.driveTrain.moveToTargetAngle();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveTrain.reachedSetpoint();
    }

    // Called once after isFinished returns true
    protected void end() {
    	// TODO Default to something reasonable
    	Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentBoundedAngle());
    	Robot.driveTrain.disableGyroPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
