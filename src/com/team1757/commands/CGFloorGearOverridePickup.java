package com.team1757.commands;

import com.team1757.utils.FloorGearCollectorControlMode;
import com.team1757.utils.FloorGearPivotControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGFloorGearOverridePickup extends CommandGroup {

	/**
	 * When the internal switch is triggered, lift to upright then stop collector
	 */
	public CGFloorGearOverridePickup() {
		addSequential(new FloorGearRun(FloorGearPivotControlMode.kCarry));
		addSequential(new FloorGearCollect(FloorGearCollectorControlMode.kStop));
	}
}
