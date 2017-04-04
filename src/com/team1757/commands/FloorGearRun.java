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
		requires(Robot.floorGearLoader);
		this.pivotControlMode = pivotControlMode;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.floorGearLoader.initializePivotPID();
		Robot.floorGearLoader.initPivotEncoder();
		Robot.floorGearLoader.enablePivotTalon();
		Robot.floorGearLoader.enablePivotPIDControl();

		if (pivotControlMode == FloorGearPivotControlMode.kCurrent) {
			Robot.floorGearLoader.setPivotTargetPosition(Robot.floorGearLoader.getPulseWidthPosition());
		} else if (pivotControlMode == FloorGearPivotControlMode.kManual) {
			Robot.floorGearLoader.setPivotTargetPosition(SmartDashboard.getNumber("Floor Gear Manual Target Position", FloorGearPivotControlMode.kReceive.getOutput()));
		} else {
			Robot.floorGearLoader.setPivotTargetPosition(pivotControlMode.getOutput());
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.floorGearLoader.runPivotTalon();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (pivotControlMode != FloorGearPivotControlMode.kCurrent) {
			return Robot.floorGearLoader.reachedPivotSetpoint();
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.floorGearLoader.disablePivotPIDControl();
		Robot.floorGearLoader.disablePivotTalon();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}