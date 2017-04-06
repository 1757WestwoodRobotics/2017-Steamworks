package com.team1757.triggers;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * 
 * @author Ryan Marten
 */
public class BumperPlate extends Trigger {
	
	private Timer timer = new Timer();
	
	public boolean get() {
		if(Robot.dropGearLoader.isReedSwitchSeperated()){
			timer.start();
		} else {
			timer.stop();
			timer.reset();
		}
		return  Robot.dropGearLoader.isTriggerEnabled() && timer.get() > .5;
	}
}
