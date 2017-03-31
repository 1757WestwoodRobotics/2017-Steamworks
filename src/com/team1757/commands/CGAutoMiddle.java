package com.team1757.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous for center starting positions on both red and blue alliances
 */
public class CGAutoMiddle extends CommandGroup {

	public CGAutoMiddle() {
		// Face gear loader forward
		addSequential(new DriveDirectionInverted());
		// Drop gear to receive
		addSequential(new GearReceive());
		// Drive straight ~100"
		addSequential(new DriveStraightY(2, -.27));
		// Correct translation with vision
		// addSequential(new VisionFaceGearTarget());
		// Drive straight ~14.3"
		addSequential(new DriveStraightY(.7, -.2));
		// "Wait a beat"
		addSequential(new Delay(.5));
		// Place gear
		addSequential(new GearScore());
		// "Wait a beat"
		addSequential(new Delay(.5));
		// Reverse
		addSequential(new DriveToggleDirection());
		// Drive backwards ~100"
		addSequential(new DriveStraightY(.7));
		// Close gear
		addSequential(new GearHug());
		// Reverse
		addSequential(new DriveToggleDirection());
		// Rotate left 90
//		SmartDashboard.putNumber("targetAngle", 90);
//		addSequential(new RotateToAngle());
		// Translate right
		addSequential(new DriveStraightX(2.0));
		// Cross line
		addSequential(new DriveStraightY(1.65));
	}
}
