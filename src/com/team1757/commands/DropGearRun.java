package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.utils.DropGearControlMode;

import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Default command for gear subsystem to continuously respond to feedback by
 * updating motor targets
 * 
 * @author ACabey 
 */
public class DropGearRun extends TimedCommand {

	private DropGearControlMode controlMode;
	private static final double TIMEOUT =  .7;

	public DropGearRun(DropGearControlMode controlMode) {
		super(TIMEOUT);
		requires(Robot.dropGearLoader);
		this.controlMode = controlMode;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.dropGearLoader.initializeGearPID();
		Robot.dropGearLoader.initEncoder();
		Robot.dropGearLoader.enableGearTalon();
		Robot.dropGearLoader.enableGearPIDControl();

		if (controlMode == DropGearControlMode.kCurrent) {
			Robot.dropGearLoader.setTargetPosition(Robot.dropGearLoader.getPulseWidthPosition());
		} else if (controlMode == DropGearControlMode.kManual) {
			Robot.dropGearLoader.setTargetPosition(SmartDashboard.getNumber("Drop Gear Manual Target Position", DropGearControlMode.kReceive.getOutput()));
		} else {
			Robot.dropGearLoader.setTargetPosition(controlMode.getOutput());
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.dropGearLoader.runGearTalon();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (controlMode != DropGearControlMode.kCurrent) {
			return Robot.dropGearLoader.reachedSetpoint() || isTimedOut();
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.dropGearLoader.disableGearPIDControl();
		Robot.dropGearLoader.disableGearTalon();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}