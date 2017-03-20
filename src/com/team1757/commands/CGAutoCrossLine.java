package com.team1757.commands;

import com.team1757.utils.DirectionControlMode;
import com.team1757.utils.GearControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous for just crossing the base line
 */
public class CGAutoCrossLine extends CommandGroup {

	public CGAutoCrossLine() {
		// Face gear loader forward
		addSequential(new DriveSetDirection(DirectionControlMode.kInverted));
		// Drop gear to receive
		addSequential(new GearRun(GearControlMode.kReceive));
		// Drive straight ~100"
		addSequential(new DriveStraightY(2.0));
	}
}
