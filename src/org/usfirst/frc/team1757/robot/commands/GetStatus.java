package org.usfirst.frc.team1757.robot.commands;

import org.usfirst.frc.team1757.robot.Robot;
import org.usfirst.frc.team1757.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GetStatus extends Command {

    public GetStatus() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("GyroAngle", RobotMap.driveTrainNavX.getAngle());
    	SmartDashboard.putNumber("BoundedGyroAngle", Robot.driveTrain.getCurrentBoundedAngle());
    	SmartDashboard.putNumber("DisplacementX", Robot.driveTrain.getCurrentDisplacementX());
    	SmartDashboard.putNumber("DisplacementY",  Robot.driveTrain.getCurrentDisplacementY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
