package com.team1757.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGCenterAndScoreGear extends CommandGroup {

    public CGCenterAndScoreGear() {
        addSequential(new VisionGetReadyToScoreGear());
        addSequential(new DriveGyroAssisted());
    }
}
