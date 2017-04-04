package com.team1757.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.team1757.robot.Robot;
import com.team1757.utils.ShooterControlMode;

/**
 * Operate Shooter mechanism.
 * 
 * Defaults to 0.80 in PercentVBus mode
 * 
 * @author ACabey
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
    	Robot.shooter.setIsShooting(controlMode.getIsShooting());
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
    	Robot.shooter.stopFlyWheel();
    	Robot.shooter.disableFlyWheelControl();
    	Robot.shooter.disableFlyWheel();
    	Robot.shooter.setIsShooting(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
