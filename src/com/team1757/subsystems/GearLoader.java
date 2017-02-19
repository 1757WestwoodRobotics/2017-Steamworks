package com.team1757.subsystems;

import com.team1757.commands.GearMatchStart;
import com.team1757.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GearLoader extends Subsystem {

	private static CANTalon gearTalon = RobotMap.gearLoaderTalon;
	private final double GEAR_PID_TOLERANCE = 50;
	
	FeedbackDeviceStatus gearEncoderStatus = gearTalon.isSensorPresent(FeedbackDevice.CtreMagEncoder_Absolute);

    public void initDefaultCommand() {
    	setDefaultCommand(new GearMatchStart());
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
//    	dGain 60
//    	fGain 0
//    	iZone 100
    	
		double pGain = SmartDashboard.getNumber("pGain", 0.64);
		double iGain = SmartDashboard.getNumber("iGain", 0.0001);
		double dGain = SmartDashboard.getNumber("dGain", 60.0);
		double fGain = SmartDashboard.getNumber("fGain", 0.0);
		double closeLoopRampRate = 36.0;
		int izone = (int) SmartDashboard.getNumber("iZone", 100);
		int profile = 0;
		gearTalon.setPID(pGain, iGain, dGain, fGain, izone, closeLoopRampRate, profile);
    }
    
    public void setTargetPosition(double targetPosition) {
    	System.out.println(targetPosition + " " + getPulseWidthPosition());
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
    	return (Math.abs(gearTalon.getClosedLoopError()) < GEAR_PID_TOLERANCE);
    }
}

