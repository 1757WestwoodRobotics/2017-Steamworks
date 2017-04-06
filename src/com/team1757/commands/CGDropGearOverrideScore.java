package com.team1757.commands;

import com.team1757.utils.Axis;
import com.team1757.utils.DropGearControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGDropGearOverrideScore extends CommandGroup {

	/**
	 * Ideally we want the robot to stop, score, and skrt if the reed switch is
	 * opened significantly for more than a second. If it closes before a second
	 * is up, the robot does not take any action. 
	 * 
	 * So have a trigger that starts when the switch is opened and resets when closed.
	 * This trigger uses .whenActive() on the command group to stop, score, and skrt. 
	 */
	public CGDropGearOverrideScore() {
		addSequential(new DriveStop());
		addSequential(new DropGearRun(DropGearControlMode.kScore));
		// Go backwards
		addSequential(new DriveStraight(Axis.axisY, .6, -.5));
		addSequential(new Delay(.5));
		addSequential(new DropGearRun(DropGearControlMode.kReceive));
	}
}
