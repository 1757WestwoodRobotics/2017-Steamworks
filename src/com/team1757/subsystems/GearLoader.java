package com.team1757.subsystems;

import com.team1757.commands.GearRun;
import com.team1757.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
/**
 *
 */
public class GearLoader extends Subsystem {

	private static CANTalon gearTalon = RobotMap.gearLoaderTalon;
	private final double GEAR_PID_TOLERANCE = 80;
	
	FeedbackDeviceStatus gearEncoderStatus = gearTalon.isSensorPresent(FeedbackDevice.CtreMagEncoder_Absolute);

    public void initDefaultCommand() {
    	setDefaultCommand(new GearRun());
    }
    
    public void initEncoder() {
    	gearTalon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		gearTalon.changeControlMode(TalonControlMode.Position);
    	//Docs say to call .set() immediately after. Check self-test in Web Dashboard.
		gearTalon.set(getPulseWidthPosition());
    }
    
    public int getPulseWidthPosition() {
    	return gearTalon.getPulseWidthPosition();
    }
    
    public boolean isSensorPresent() {
    	return (FeedbackDeviceStatus.FeedbackStatusPresent == gearEncoderStatus);
    }
     
    public void setTalonPID() {
    	
//    	Gear Talon on TB without Limits
//
//    	pGain 0.64
//    	iGain 1.0E-4
//    	dGain 60.0
//    	fGain 0.0
//    	iZone 100
//		closeLoopRampRate 36.0
//		profile 0

    	gearTalon.setPID(0.64, 0.0001, 60.0, 0.0, 100, 36.0, 0);
    }
    
    public void setTargetPosition(double targetPosition) {
    	gearTalon.set(targetPosition/4096.0);
    }
    
    public void enableGearTalon() {
    	gearTalon.enable();
    }
    
    public void disableGearTalon() {
    	gearTalon.disable();
    }
    
    public void enableGearPIDControl() {
    	gearTalon.enableControl();
    }
    
    public void disableGearPIDControl() {
    	gearTalon.disableControl();
    }
    
    public boolean reachedSetpoint() {
    	return (Math.abs(gearTalon.getSetpoint()*4096.0 - getPulseWidthPosition()) < GEAR_PID_TOLERANCE);
    }
    
    public void runGearTalon() {
    	gearTalon.set(gearTalon.getSetpoint());
    }
}

