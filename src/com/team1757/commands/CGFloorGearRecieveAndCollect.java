package com.team1757.commands;

import com.team1757.utils.FloorGearCollectorControlMode;
import com.team1757.utils.FloorGearPivotControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGFloorGearRecieveAndCollect extends CommandGroup {

    public CGFloorGearRecieveAndCollect() {
    	addParallel(new FloorGearCollect(FloorGearCollectorControlMode.kIntake));
    	addParallel(new FloorGearRun(FloorGearPivotControlMode.kReceive));
    }
}
