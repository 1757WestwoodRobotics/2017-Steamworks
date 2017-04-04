package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GetStatus extends Command {

	public GetStatus() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Floor Gear Pivot Manual - FloorGearRun.java
		SmartDashboard.putNumber("Floor Gear Manual Target Position", 0.0);
		// Drop Gear Manual - DropGearRun.java
		SmartDashboard.putNumber("Gear Manual Target Position", 0.0);
		//RotateDegrees
		SmartDashboard.putNumber("angularDelta", 0.0);
		//RotateToAngle
		SmartDashboard.putNumber("targetAngle", 0.0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SmartDashboard.putNumber("GyroAngle", Robot.driveTrain.getCurrentRawAngle());
		SmartDashboard.putNumber("BoundedGyroAngle", Robot.driveTrain.getCurrentBoundedAngle());
//		SmartDashboard.putNumber("Analog Ultrasonic Distance", Robot.driveTrain.getAnalogRangeInches());
//		SmartDashboard.putNumber("Analog Ultrasonic Distance MM", Robot.driveTrain.getAnalogRangeMM());
//		SmartDashboard.putNumber("Serial Ultrasonic Distance", Robot.driveTrain.getSerialRangeInches());
		SmartDashboard.putNumber("targetLifterPVbus", Robot.lifter.getLiftTarget());
		SmartDashboard.putBoolean("isShooting", Robot.shooter.getIsShooting());

		try {
			if (Robot.dropGearLoader.isSensorPresent()) {
				SmartDashboard.putNumber("Gear - ActualPosition", Robot.dropGearLoader.getPulseWidthPosition());
			}

		} catch (Exception e) {
			System.out.println("Error with gear subsystem: " + e.getMessage());
		}
		
		try {
			if (Robot.floorGearLoader.isSensorPresent()) {
				SmartDashboard.putNumber("Pivot - ActualPosition", Robot.floorGearLoader.getPulseWidthPosition());
			}

		} catch (Exception e) {
			System.out.println("Error with floor gear subsystem: " + e.getMessage());
		}
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
