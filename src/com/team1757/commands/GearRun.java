package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.utils.GearControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Default command for gear subsystem to continuously respond to feedback by
 * updating motor targets
 * 
 * @author ACabey 
 */
public class GearRun extends Command {

	private GearControlMode controlMode;

	public GearRun(GearControlMode controlMode) {
		requires(Robot.gearLoader);
		this.controlMode = controlMode;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.gearLoader.initializeGearPID();
		Robot.gearLoader.initEncoder();
		Robot.gearLoader.enableGearTalon();
		Robot.gearLoader.enableGearPIDControl();

		if (controlMode == GearControlMode.kCurrent) {
			Robot.gearLoader.setTargetPosition(Robot.gearLoader.getPulseWidthPosition());
		} else if (controlMode == GearControlMode.kManual) {
			SmartDashboard.getNumber("Gear Manual Target Position", GearControlMode.kReceive.getOutput());
		} else {
			Robot.gearLoader.setTargetPosition(controlMode.getOutput());
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.gearLoader.runGearTalon();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (controlMode != GearControlMode.kCurrent) {
			return Robot.gearLoader.reachedSetpoint();
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.gearLoader.disableGearPIDControl();
		Robot.gearLoader.disableGearTalon();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}