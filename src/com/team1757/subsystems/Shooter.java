package com.team1757.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.team1757.commands.StopShooter;
import com.team1757.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author ACabey
 */
public class Shooter extends Subsystem {
	
	private final CANTalon shooterFlyWheel = RobotMap.shooterFlyWheel;
	private final CANTalon indexerFlyWheel = RobotMap.indexerFlyWheel;

    public void initDefaultCommand() {
        setDefaultCommand(new StopShooter());
    }
    
    public void initializeFlyWheelPID() {
		RobotMap.shooterFlyWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	shooterFlyWheel.setPID(0.0, 0.0001, 0.0);
    }
    
    public void enableIndexer() {
    	indexerFlyWheel.enable();
    }
    
    public void disableIndexer() {
    	indexerFlyWheel.disable();
    }
    
    public void setIndexerModeSpeed() {
    	indexerFlyWheel.changeControlMode(TalonControlMode.Speed);
    }
    
    public void setIndexerModePercentVoltage() {
    	indexerFlyWheel.changeControlMode(TalonControlMode.PercentVbus);
    }
    
    public void setIndexerTarget(double target) {
    	indexerFlyWheel.set(target);
    }
    
    public void enableFlyWheel() {
    	shooterFlyWheel.enable();
    }
    
    public void disableFlyWheel() {
    	shooterFlyWheel.disable();
    }
    
    public void enableFlyWheelControl() {
    	shooterFlyWheel.enableControl();
    }
    
    public void disableFlyWheelControl() {
    	shooterFlyWheel.disableControl();
    }
    
    public void stopFlyWheel() {
    	shooterFlyWheel.disableControl();
    }
    
    public void setFlyWheelModeSpeed() {
		shooterFlyWheel.changeControlMode(TalonControlMode.Speed);
    }
    
    public void setFlyWheelModePercentVoltage() {
		shooterFlyWheel.changeControlMode(TalonControlMode.PercentVbus);
    }
    
    public void setFlyWheelTarget(double target) {
    	shooterFlyWheel.set(target);
    }
}

