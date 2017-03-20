package com.team1757.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.team1757.robot.Robot;
import com.team1757.utils.ShooterControlMode;

/**
 *
 */
public class Shoot extends Command {

	private ShooterControlMode controlMode = ShooterControlMode.kPercentForward;
	
    public Shoot() {
        requires(Robot.shooter);
    }
    
    public Shoot(ShooterControlMode controlMode) {
        requires(Robot.shooter);
        this.controlMode = controlMode;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.initializeFlyWheelPID();
    	Robot.shooter.changeControlMode(controlMode.getControlMode());
    	Robot.shooter.enableFlyWheel();
    	Robot.shooter.enableFlyWheelControl();
    	SmartDashboard.putBoolean("isShooting", true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.setFlyWheelTarget(controlMode.getOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.disableFlyWheelControl();
    	Robot.shooter.disableFlyWheel();
    	SmartDashboard.putBoolean("isShooting", true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
