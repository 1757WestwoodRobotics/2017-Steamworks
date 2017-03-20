package com.team1757.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.team1757.commands.Collect;
import com.team1757.robot.RobotMap;
import com.team1757.utils.CollectorControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author ACabey
 */
public class BallCollector extends Subsystem {

	private final CANTalon collectorFlyWheel = RobotMap.collectorFlyWheel;

	public void initDefaultCommand() {
		setDefaultCommand(new Collect(CollectorControlMode.kStop));
	}

	/**
	 * Initialize PID feedback and constants for flywheel
	 * 
	 * pGain 0.0 iGain 0.0001 dGain 0.0
	 */
	public void initializeFlyWheelPID() {
		RobotMap.shooterFlyWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		// TODO Tune collector PID if we want to use speed
		collectorFlyWheel.setPID(0.0, 0.0001, 0.0);
	}

	/**
	 * Enable flywheel
	 */
	public void enableFlyWheel() {
		collectorFlyWheel.enable();
	}

	/**
	 * Disable flywheel
	 */
	public void disableFlyWheel() {
		collectorFlyWheel.disable();
	}

	/**
	 * Enable flywheel onboard PID control
	 */
	public void enableFlyWheelControl() {
		collectorFlyWheel.enableControl();
	}

	/**
	 * Disable flywheel onboard PID control
	 */
	public void disableFlyWheelControl() {
		collectorFlyWheel.disableControl();
	}

	/**
	 * Stop flywheel by setting target to 0
	 */
	public void stopFlyWheel() {
		collectorFlyWheel.set(0);
	}

	/**
	 * Change Talon control mode
	 * @param TalonControlMode 
	 */
	public void changeControlMode(TalonControlMode talonControl) {
		collectorFlyWheel.changeControlMode(talonControl);
	}

	/**
	 * Set target for any control mode
	 * 
	 * @param target
	 *            The target for any given control mode [-1, 1] for percent vbus
	 *            [-MAX_DOUBLE, MAX_DOUBLE] for speed
	 */
	public void setFlyWheelTarget(double target) {
		collectorFlyWheel.set(target);
	}
}
