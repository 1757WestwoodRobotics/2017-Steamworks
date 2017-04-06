package com.team1757.commands;

import com.team1757.utils.Axis;
import com.team1757.utils.FloorGearPivotControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGFloorGearScore extends CommandGroup {

    public CGFloorGearScore() {
    	addParallel(new DriveStraight(Axis.axisY, .5, -.3));
    	addParallel(new FloorGearRun(FloorGearPivotControlMode.kScore));
    }
}
