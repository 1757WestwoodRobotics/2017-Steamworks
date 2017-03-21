package com.team1757.commands;

import com.team1757.robot.Robot;
import com.team1757.subsystems.RingLight;
import com.team1757.utils.RingLightControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class RingLightSwitch extends InstantCommand {

	private RingLightControlMode ringLightControl;
	
    public RingLightSwitch(RingLightControlMode ringLightControl) {
        requires(Robot.ringLight);
        this.ringLightControl = ringLightControl;
    }
    
    protected void initialize() {
    	Robot.ringLight.setRingLight(ringLightControl);
    }
    
	protected boolean isFinished() {
	    return true;
	}
}
