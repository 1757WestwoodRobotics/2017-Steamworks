package com.team1757.commands;

import com.team1757.utils.VisionDetectionTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Aligns to the gear target centered and perpendicularly and allows the driver
 * to drive competely straight (with PID) to score
 * 
 * @author Ryan Marten
 */
public class CGCenterAndScoreGear extends CommandGroup {

	public CGCenterAndScoreGear() {
		addSequential(new VisionAlignTargetPerpendicular(VisionDetectionTarget.GearAirship));
		addSequential(new DriveGyroAssisted());
	}
}
