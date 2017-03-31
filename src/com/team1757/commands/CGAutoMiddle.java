package com.team1757.commands;

import com.team1757.utils.Axis;
import com.team1757.utils.DirectionControlMode;
import com.team1757.utils.GearControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous for center starting positions on both red and blue alliances
 */
public class CGAutoMiddle extends CommandGroup {

	public CGAutoMiddle() {
		// Face gear loader forward
		addSequential(new DriveSetDirection(DirectionControlMode.kInverted));
		// Drop gear to receive
		addSequential(new GearRun(GearControlMode.kReceive));
		// Drive straight ~100"
		addSequential(new DriveStraight(Axis.axisY, 2, -.27));
		// Correct translation with vision
		// addSequential(new VisionFaceGearTarget());
		// Drive straight ~14.3"
		addSequential(new DriveStraight(Axis.axisY, .7, -.2));
		// "Wait a beat"
		addSequential(new Delay(.5));
		// Place gear
		addSequential(new GearRun(GearControlMode.kScore));
		// "Wait a beat"
		addSequential(new Delay(.5));
		// Reverse
		addSequential(new DriveSetDirection(DirectionControlMode.kToggle));
		// Drive backwards ~100"
		addSequential(new DriveStraight(Axis.axisY, .7));
		// Close gear
		addSequential(new GearRun(GearControlMode.kReceive));
		// Reverse
		addSequential(new DriveSetDirection(DirectionControlMode.kToggle));
		// Rotate left 90
		
		// Translate right
		addSequential(new DriveStraight(Axis.axisX, 2.0));
		// Cross line
		addSequential(new DriveStraight(Axis.axisY, 1.65));
	}
}
