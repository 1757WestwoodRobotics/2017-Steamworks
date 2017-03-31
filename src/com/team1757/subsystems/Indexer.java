package com.team1757.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team1757.commands.IndexerStop;
import com.team1757.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author ACabey
 */
public class Indexer extends Subsystem {
	
	private final CANTalon indexerTalon = RobotMap.indexerTalon;

    public void initDefaultCommand() {
        setDefaultCommand(new IndexerStop());
    }
    
    public void enableIndexer() {
    	indexerTalon.enable();
    }
    
    public void disableIndexer() {
    	indexerTalon.disable();
    }
    
    public void setIndexerModeSpeed() {
    	indexerTalon.changeControlMode(TalonControlMode.Speed);
    }
    
    public void setIndexerModePercentVoltage() {
    	indexerTalon.changeControlMode(TalonControlMode.PercentVbus);
    }
    
    public void setIndexerTarget(double target) {
    	indexerTalon.set(target);
    }
}

