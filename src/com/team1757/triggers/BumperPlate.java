package com.team1757.triggers;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * 
 * @author Ryan Marten
 */
public class BumperPlate extends Trigger {

	public boolean get() {
		return Robot.dropGearLoader.isReedSwitchSeperated();
	}
}
