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

    public void initDefaultCommand() {
        setDefaultCommand(new StopFlyWheel());
    }
    
    public void initializeFlyWheelPID() {
		double pGain = SmartDashboard.getNumber("FlyWheelpGain", 1);
		double iGain = SmartDashboard.getNumber("FlyWheeliGain", 0);
		double dGain = SmartDashboard.getNumber("FlyWheeldGain", 0);
		
		RobotMap.shooterFlyWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	shooterFlyWheel.setPID(pGain, iGain, dGain);
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
    
    public void setModeSpeed() {
		shooterFlyWheel.changeControlMode(TalonControlMode.Speed);
    }
    
    public void setModeVoltage() {
		shooterFlyWheel.changeControlMode(TalonControlMode.Voltage);
    }
    
    public void setFlyWheelTarget(double target) {
    	shooterFlyWheel.set(target);
    }
}

