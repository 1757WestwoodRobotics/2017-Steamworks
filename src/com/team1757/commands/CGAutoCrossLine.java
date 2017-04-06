package com.team1757.commands;

import com.team1757.utils.Axis;
import com.team1757.utils.DirectionControlMode;
import com.team1757.utils.DropGearControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous for just crossing the base line
 */
public class CGAutoCrossLine extends CommandGroup {

	public CGAutoCrossLine() {
		// Face gear loader forward
		// Drop gear to receive
		addSequential(new DropGearRun(DropGearControlMode.kReceive));
		// Drive straight ~100"
		addSequential(new DriveStraight(Axis.axisY, 2.0, -.7));
	}
}
