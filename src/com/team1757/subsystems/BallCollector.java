package com.team1757.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.team1757.commands.CollectorStop;
import com.team1757.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * @author ACabey
 */
public class BallCollector extends Subsystem {
	
	private final CANTalon collectorFlyWheel = RobotMap.collectorFlyWheel;

    public void initDefaultCommand() {
        setDefaultCommand(new CollectorStop());
    }
    
    public void initializeFlyWheelPID() {
		RobotMap.shooterFlyWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	// TODO Tune collector PID if we want to use speed
		collectorFlyWheel.setPID(0.0, 0.0001, 0.0);
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
    
    public double getCollectorFlyWheelCurrent() {
    	return collectorFlyWheel.getOutputCurrent();
    }
    
}

