package com.team1757.subsystems;

import com.team1757.commands.RingLightSwitch;
import com.team1757.robot.RobotMap;
import com.team1757.utils.RingLightControlMode;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Ring Light Controller
 * 
 * @author Larry
 */

public class RingLight extends Subsystem {
	
	private DigitalOutput gearRingLightController = RobotMap.gearRingLightController;

    public void initDefaultCommand() {
        setDefaultCommand(new RingLightSwitch(RingLightControlMode.kGearLightOff));
    }
    
	public void setRingLight(RingLightControlMode ringLightControl) {
		gearRingLightController.set(RingLightControlMode.kGearLightOn.getOutput());
	}
	
	public boolean getRingLightStatus() {
		return gearRingLightController.get();
	}
}

