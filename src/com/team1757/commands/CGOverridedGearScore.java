package com.team1757.commands;

import com.team1757.utils.Axis;
import com.team1757.utils.DropGearControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGOverridedGearScore extends CommandGroup {

    public CGOverridedGearScore() {
        addSequential(new DriveStop());
        addSequential(new DropGearRun(DropGearControlMode.kScore));
        //Go backwards
        //addSequential(new DriveStraight(Axis.axisY, .7));
        addSequential(new Delay(.5));
        addSequential(new DropGearRun(DropGearControlMode.kReceive));
    }
}
 