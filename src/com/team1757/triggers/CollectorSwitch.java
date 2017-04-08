package com.team1757.triggers;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * 
 * @author Ryan Marten
 */
public class CollectorSwitch extends Trigger {

	private Timer timer = new Timer();

	public boolean get() {
		if (Robot.floorGearLoader.isSwitchActive()) {
			if (timer.get() == 0) {
				timer.start();
			}
		} else {
			timer.stop();
			timer.reset();
		}
		return Robot.floorGearLoader.isTriggerEnabled() && timer.get() > .2;
	}
}
