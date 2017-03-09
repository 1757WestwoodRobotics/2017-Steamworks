package com.team1757.commands;

import edu.wpi.first.wpilibj.Timer;
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
		addSequential(new DriveStraight(1.2));
		// Correct translation with vision
		// addSequential(new VisionFaceGearTarget());
		// Drive straight ~14.3"
		addSequential(new DriveStraight(.1));
		// "Wait a beat"
		addSequential(new Delay(.5));
		// Place gear
		addSequential(new GearScore());
		// "Wait a beat"
		addSequential(new Delay(.5));
		// Reverse
		addSequential(new DriveToggleDirection());
		// Drive backwards ~100"
		addSequential(new DriveStraight(1.0));
		// Close gear
		addSequential(new GearReceive());
		// Rotate left 90
		SmartDashboard.putNumber("targetAngle", 90);
		addSequential(new RotateToAngle());
	}
}
