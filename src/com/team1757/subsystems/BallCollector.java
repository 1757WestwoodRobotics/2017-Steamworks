package com.team1757.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.team1757.commands.StopCollector;
import com.team1757.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author ACabey
 */
public class BallCollector extends Subsystem {
	
	private final CANTalon collectorFlyWheel = RobotMap.collectorFlyWheel;

    public void initDefaultCommand() {
        setDefaultCommand(new StopCollector());
    }
    
    public void initializeFlyWheelPID() {
    	// TODO Hardcode constants
		double pGain = SmartDashboard.getNumber("CollectorpGain", 1);
		double iGain = SmartDashboard.getNumber("CollectoriGain", 0);
		double dGain = SmartDashboard.getNumber("CollectordGain", 0);
		
		RobotMap.shooterFlyWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	collectorFlyWheel.setPID(pGain, iGain, dGain);
    }
    
    public void enableFlyWheel() {
    	collectorFlyWheel.enable();
    }
    
    public void disableFlyWheel() {
    	collectorFlyWheel.disable();
    }
    
    public void enableFlyWheelControl() {
    	collectorFlyWheel.enableControl();
    }
    
    public void disableFlyWheelControl() {
    	collectorFlyWheel.disableControl();
    }
    
    public void stopFlyWheel() {
    	collectorFlyWheel.disableControl();
    }
    
    public void setModeSpeed() {
    	collectorFlyWheel.changeControlMode(TalonControlMode.Speed);
    }
    
    public void setModePercentVoltage() {
    	collectorFlyWheel.changeControlMode(TalonControlMode.PercentVbus);
    }
    
    public void setFlyWheelTarget(double target) {
    	collectorFlyWheel.set(target);
    }
}

