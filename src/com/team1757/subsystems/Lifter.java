package com.team1757.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team1757.commands.Lift;
import com.team1757.robot.RobotMap;
import com.team1757.utils.LifterControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author ACabey
 */
public class Lifter extends Subsystem {

	private final CANTalon liftTalon = RobotMap.liftTalon;

	public void initDefaultCommand() {
		setDefaultCommand(new Lift(LifterControlMode.kStop));
	}

	/**
	 * Enable lifter
	 */
	public void enableLifter() {
		liftTalon.enable();
	}

	/**
	 * Disable lifter
	 */
	public void disableLifter() {
		liftTalon.disable();
	}

	/**
	 * Stop lifter by setting target to 0
	 */
	public void stopLifter() {
		liftTalon.set(0);
	}

	/**
	 * Set lifter mode percent vbus
	 */
	public void setModePercentVoltage() {
		liftTalon.changeControlMode(TalonControlMode.PercentVbus);
	}

	/**
	 * Set target for any control mode
	 * 
	 * @param target
	 *            Generally percent voltage in range [-1, 1]
	 */
	public void setLiftTarget(double target) {
		liftTalon.set(target);
	}
	
	
	/**
	 * Get target for any control mode
	 * 
	 * @return target
	 * 		Generally percent voltage in range [-1, 1]
	 */
	public double getLiftTarget() {
		return liftTalon.get();
	}
}
