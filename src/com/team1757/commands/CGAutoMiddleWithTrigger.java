package com.team1757.commands;

import com.team1757.utils.Axis;
import com.team1757.utils.DirectionControlMode;
import com.team1757.utils.DropGearControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous for center starting positions on both red and blue alliances
 */
public class CGAutoMiddleWithTrigger extends CommandGroup {

	public CGAutoMiddleWithTrigger() {
		addSequential(new TriggerSetEnabledStatus(true));
		// Forward direction
		addSequential(new DriveSetDirection(DirectionControlMode.kDropGear));
		// Drop gear to receive
		addSequential(new DropGearRun(DropGearControlMode.kReceive));
		// Drive straight ~100" (changed from 2.0 for district champs)
		addSequential(new DriveStraight(Axis.axisY, 2.3, .27));
/*		// Instead of driving straight a certain distance, it will drive
		// straight for a little longer (1 instead of .7 seconds) at .2 speed
		// and if it bumper plate is triggered, then it deploys, if not it holds on to the gear
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
		addSequential(new DriveStraight(Axis.axisX, 2.0));
		// Cross line
		addSequential(new DriveStraight(Axis.axisY, 1.65, .35));*/
	}
}
