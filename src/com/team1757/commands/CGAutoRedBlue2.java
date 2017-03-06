package com.team1757.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous for center starting positions on both red and blue alliances
 */
public class CGAutoRedBlue2 extends CommandGroup {

    public CGAutoRedBlue2() {
//		Drop gear to receive
//    	addSequential(new GearReceive());    	
//    	Drive straight ~100"
    	addSequential(new DriveStraight(1.65));
//    	Correct translation with vision
    	addSequential(new VisionFaceGearTarget());
//    	Drive straight ~14.3"
    	addSequential(new DriveStraight(.1));
//    	Place gear
//    	addSequential(new GearScore());
//    	Close gear
//    	addSequentia(new GearReceive());
//		Reverse
    	addSequential(new DriveToggleDirection());
//    	Drive backwards ~100"
    	addSequential(new DriveStraight(1.65));
//    	Rotate left 90
    	addSequential(new RotateToAngle(-90));
    }
}
