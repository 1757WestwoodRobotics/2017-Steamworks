package com.team1757.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.team1757.commands.StopFlyWheel;
import com.team1757.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author ACabey
 */
public class Shooter extends Subsystem {
	
	private final CANTalon shooterFlyWheel = RobotMap.shooterFlyWheel;
	private final CANTalon indexerFlyWheel = RobotMap.indexerFlyWheel;

    public void initDefaultCommand() {
        setDefaultCommand(new StopFlyWheel());
    }
    
    public void initializeFlyWheelPID() {
    	// TODO Hardcode constants
		double pGain = SmartDashboard.getNumber("FlyWheelpGain", 1);
		double iGain = SmartDashboard.getNumber("FlyWheeliGain", 0);
		double dGain = SmartDashboard.getNumber("FlyWheeldGain", 0);
		
		RobotMap.shooterFlyWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	shooterFlyWheel.setPID(pGain, iGain, dGain);
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

