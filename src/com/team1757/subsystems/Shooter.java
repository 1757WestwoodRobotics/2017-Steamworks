package com.team1757.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.team1757.commands.ShooterStop;
import com.team1757.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author ACabey
 */
public class Shooter extends Subsystem {

	private final CANTalon shooterFlyWheel = RobotMap.shooterFlyWheel;

	public void initDefaultCommand() {
		setDefaultCommand(new ShooterStop());
	}

	/**
	 * Initialize PID feedback and constants for shooter
	 * 
	 * pGain 0.0
	 * iGain 0.0001
	 * dGain 0.0
	 */
	public void initializeFlyWheelPID() {
		// TODO Tune PID if we are going to use encoder feedback
		RobotMap.shooterFlyWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		shooterFlyWheel.setPID(0.0, 0.0001, 0.0);
	}

	/**
	 * Enable shooter flywheel
	 */
	public void enableFlyWheel() {
		shooterFlyWheel.enable();
	}

	/**
	 * Disable shooter flywheel
	 */
	public void disableFlyWheel() {
		shooterFlyWheel.disable();
	}

	/**
	 * Enable onboard PID control
	 */
	public void enableFlyWheelControl() {
		shooterFlyWheel.enableControl();
	}

	/**
	 * Disable onboard PID control
	 */
	public void disableFlyWheelControl() {
		shooterFlyWheel.disableControl();
	}

	/**
	 * Stop flywheel by setting target to 0
	 */
	public void stopFlyWheel() {
		shooterFlyWheel.set(0);
	}

	/**
	 * Set flywheel control mode to speed (requires feedback device)
	 */
	public void setFlyWheelModeSpeed() {
		shooterFlyWheel.changeControlMode(TalonControlMode.Speed);
	}

	/**
	 * Set flywheel control mode to percent vbus
	 */
	public void setFlyWheelModePercentVoltage() {
		shooterFlyWheel.changeControlMode(TalonControlMode.PercentVbus);
	}

	/**
	 * Set flywheel target for any control mode
	 * 
	 * @param target
	 *            Usually voltage in range [-5, 5]
	 */
	public void setFlyWheelTarget(double target) {
		shooterFlyWheel.set(target);
	}
}
