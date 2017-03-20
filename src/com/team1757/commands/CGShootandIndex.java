package com.team1757.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGShootandIndex extends CommandGroup {

    public CGShootandIndex() {
    	
    	addParallel(new ShootWithVoltage());
    	addParallel(new Index());
    }
}
