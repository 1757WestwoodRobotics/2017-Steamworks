package com.team1757.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team1757.commands.Index;
import com.team1757.robot.RobotMap;
import com.team1757.utils.IndexerControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author ACabey
 */
public class Indexer extends Subsystem {
	
	private final CANTalon indexerTalon = RobotMap.indexerTalon;

    public void initDefaultCommand() {
        setDefaultCommand(new Index(IndexerControlMode.kStop));
    }
    
    public void enableIndexer() {
    	indexerTalon.enable();
    }
    
    public void disableIndexer() {
    	indexerTalon.disable();
    }
    
    public void changeControlMode(TalonControlMode talonControl) {
    	indexerTalon.changeControlMode(talonControl);
    }
    
    public void setIndexerTarget(double target) {
    	indexerTalon.set(target);
    }
    
    public void stopIndexer() {
    	indexerTalon.set(0);
    }
}

