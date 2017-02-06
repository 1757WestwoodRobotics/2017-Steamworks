package org.usfirst.frc.team1757.robot.commands;

import org.usfirst.frc.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author ACabey
 * 
 * Rotate an angular delta from the current reading
 */
public class RotateDegrees extends Command {

    public RotateDegrees() {
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.enablePID();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// TODO Change the default angle to something more reasonable
    	Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentRawAngle() + SmartDashboard.getNumber("angularDelta", 0));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	// TODO Default to something reasonable
    	Robot.driveTrain.setTargetAngle(Robot.driveTrain.getCurrentRawAngle());
    	Robot.driveTrain.disablePID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
