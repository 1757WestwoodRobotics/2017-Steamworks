package com.team1757.commands;

import com.team1757.utils.IndexerControlMode;
import com.team1757.utils.ShooterControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */

public class CGShootandIndex extends CommandGroup {

    public CGShootandIndex() {
    	addParallel(new Shoot(ShooterControlMode.kVoltageForward));
    	addParallel(new Index(IndexerControlMode.kPercentForward));
    }
}
