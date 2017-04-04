package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.utils.FloorGearPivotControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Default command for gear subsystem to continuously respond to feedback by
 * updating motor targets
 * 
 * @author ACabey 
 */
public class FloorGearRun extends Command {

	private FloorGearPivotControlMode pivotControlMode;

	public FloorGearRun(FloorGearPivotControlMode pivotControlMode) {
		requires(Robot.dropGearLoader);
		this.pivotControlMode = pivotControlMode;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.dropGearLoader.initializeGearPID();
		Robot.dropGearLoader.initEncoder();
		Robot.dropGearLoader.enableGearTalon();
		Robot.dropGearLoader.enableGearPIDControl();

		if (pivotControlMode == FloorGearPivotControlMode.kCurrent) {
			Robot.dropGearLoader.setTargetPosition(Robot.dropGearLoader.getPulseWidthPosition());
		} else if (pivotControlMode == FloorGearPivotControlMode.kManual) {
			SmartDashboard.getNumber("Floor Gear Manual Target Position", FloorGearPivotControlMode.kReceive.getOutput());
		} else {
			Robot.dropGearLoader.setTargetPosition(pivotControlMode.getOutput());
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.dropGearLoader.runGearTalon();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (pivotControlMode != FloorGearPivotControlMode.kCurrent) {
			return Robot.dropGearLoader.reachedSetpoint();
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