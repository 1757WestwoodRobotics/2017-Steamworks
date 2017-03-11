package com.team1757.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous for Red alliance left side (not near boiler) and Blue alliance left side (near boiler)
 */
public class CGAutoLeft extends CommandGroup {

	public CGAutoLeft() {
		//Reset the Gyro
		addSequential(new DriveResetGyro());
		// Face gear loader forward
		addSequential(new DriveDirectionInverted());
		// Drop gear to receive
		addSequential(new GearReceive());
		// Drive straight ~114"
		addSequential(new DriveStraightY(1.3));
		// Rotate right 60
		addSequential(new RotateSetTargetAngle(60));
		addSequential(new RotateToAngle());
		// Correct translation with vision
		// addSequential(new VisionFaceGearTarget());
		// Drive straight ~14.3"
		addSequential(new DriveStraightY(.2));
		// "Wait a beat"
		addSequential(new Delay(.2));
		// Place gear
//		addSequential(new GearScore());
		// "Wait a beat"
		addSequential(new Delay(.2));
		// Reverse
		addSequential(new DriveToggleDirection());
		// Drive backwards ~100"
		addSequential(new DriveStraightY(.4));
		// Close gear
		addSequential(new GearReceive());
		// Rotate left 60
		addSequential(new RotateSetTargetAngle(0));
		addSequential(new RotateToAngle());
		// Drive straight across the baseline
		addSequential(new DriveStraightY(.8));
	}
}
