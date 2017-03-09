package com.team1757.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Autonomous for center starting positions on both red and blue alliances
 */
public class CGAutoRedBlue2 extends CommandGroup {

	public CGAutoRedBlue2() {
		// Face gear loader forward
		addSequential(new DriveDirectionInverted());
		// Drop gear to receive
		addSequential(new GearReceive());
		// Drive straight ~100"
		addSequential(new DriveStraight(1.65));
		// Correct translation with vision
		addSequential(new VisionFaceGearTarget());
		// Drive straight ~14.3"
		addSequential(new DriveStraight(.1));
		// Place gear
		addSequential(new GearScore());
		// Close gear
		addSequential(new GearReceive());
		// Reverse
		addSequential(new DriveToggleDirection());
		// Drive backwards ~100"
		addSequential(new DriveStraight(1.65));
		// Rotate left 90
		SmartDashboard.putNumber("targetNumber", 90);
		addSequential(new RotateToAngle());
	}
}
