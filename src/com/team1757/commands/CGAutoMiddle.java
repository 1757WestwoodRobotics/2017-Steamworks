package com.team1757.commands;

import com.team1757.utils.Axis;
import com.team1757.utils.DirectionControlMode;
import com.team1757.utils.DropGearControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous for center starting positions on both red and blue alliances
 */
public class CGAutoMiddle extends CommandGroup {

	public CGAutoMiddle() {
		addSequential(new TriggerSetEnabledStatus(false));
		// Forward direction
		addSequential(new DriveSetDirection(DirectionControlMode.kDropGear));
		// Drop gear to receive
		addSequential(new DropGearRun(DropGearControlMode.kReceive));
		// Drive straight ~100" (changed from 2.0 for district champs)
		addSequential(new DriveStraight(Axis.axisY, 2.2, .27));
		// Correct translation with vision
		// addSequential(new VisionFaceGearTarget());
		// Drive straight ~14.3"
		addSequential(new DriveStraight(Axis.axisY, .7, .2));
		// "Wait a beat"
		addSequential(new Delay(.5));
		// Place gear
		addSequential(new DropGearRun(DropGearControlMode.kScore));
		// "Wait a beat"
		addSequential(new Delay(.5));
		// Drive backwards ~100"
		addSequential(new DriveStraight(Axis.axisY, .7, -.35));
		// Close gear
		addSequential(new DropGearRun(DropGearControlMode.kReceive));
		// Translate right
		addSequential(new DriveStraight(Axis.axisX, 2.0, 1.0));
		// Cross line
		addSequential(new DriveStraight(Axis.axisY, 1.65, .35));
		addSequential(new TriggerSetEnabledStatus(true));
	}
}
