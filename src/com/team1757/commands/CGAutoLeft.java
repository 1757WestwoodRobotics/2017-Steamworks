package com.team1757.commands;

import com.team1757.utils.Axis;
import com.team1757.utils.DirectionControlMode;
import com.team1757.utils.DropGearControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous for Red alliance left side (not near boiler) and Blue alliance left side (near boiler)
 */
public class CGAutoLeft extends CommandGroup {

	public CGAutoLeft() {
		//Reset the Gyro
		addSequential(new DriveResetGyro());
		// Face gear loader forward
		addSequential(new DriveSetDirection(DirectionControlMode.kFloorGear));
		// Drop gear to receive
		addSequential(new DropGearRun(DropGearControlMode.kReceive));
		// Drive straight ~114"
		addSequential(new DriveStraight(Axis.axisY, 1.6));
		// Rotate right 60
		addSequential(new RotateToAngle(60.0));
		// Correct translation with vision
		// addSequential(new VisionFaceGearTarget());
		// Drive straight ~14.3"
		addSequential(new DriveStraight(Axis.axisY, 1, -.2));
//		// "Wait a beat"
//		addSequential(new Delay(.2));
//		// Place gear
//		addSequential(new GearScore());
//		// "Wait a beat"
//		addSequential(new Delay(.2));
//		// Reverse
//		addSequential(new DriveToggleDirection());
//		// Drive backwards ~100"
//		addSequential(new DriveStraightY(.4));
//		// Close gear
//		addSequential(new GearReceive());
//		// Rotate left 60
//		addSequential(new RotateSetTargetAngle(0));
//		addSequential(new RotateToAngle(.5));
//		//Reverse
//		addSequential(new DriveToggleDirection());
//		// Drive straight across the baseline
//		addSequential(new DriveStraightY(.8));
	}
}
