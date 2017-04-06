package com.team1757.subsystems;

import com.team1757.commands.DropGearRun;
import com.team1757.robot.RobotMap;
import com.team1757.utils.DropGearControlMode;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DropGearLoader extends Subsystem {

	private static CANTalon gearTalon = RobotMap.dropGearLoaderTalon;
	private final double GEAR_PID_TOLERANCE = 80;
	private DigitalInput reedSwitch = RobotMap.dropGearReedSwitch;

	FeedbackDeviceStatus gearEncoderStatus = gearTalon.isSensorPresent(FeedbackDevice.CtreMagEncoder_Absolute);

	public void initDefaultCommand() {
		setDefaultCommand(new DropGearRun(DropGearControlMode.kCurrent));
	}

	
	public boolean isReedSwitchSeperated(){
		System.out.println(reedSwitch.get());
		return reedSwitch.get();
	}
	
	/**
	 * Initialize PID feedback and constants for gear loader
	 * 
	 * pGain 0.64
	 * iGain 1.0E-4
	 * dGain 60.0
	 * fGain 0.0
	 * iZone 100
	 * closeLoopRampRate 36.0
	 * profile 0
	 */
	public void initializeGearPID() {
		gearTalon.setPID(0.64, 0.0001, 60.0, 0.0, 100, 36.0, 0);
	}

	/** 
	 * Initialize encoder PID feedback
	 * 
	 * Set feedback device to encoder
	 * Set feedback mode to position
	 * 
	 * Set PID target to current position to lock in place
	 */
	public void initEncoder() {
		gearTalon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		gearTalon.changeControlMode(TalonControlMode.Position);

		// Lock into place (assuming starting position is MatchStart)
		gearTalon.set(getPulseWidthPosition());
	}
	
	/**
	 * Set gear PID target
	 * 
	 * @param targetPosition
	 *            Position in encoder counts
	 */
	public void setTargetPosition(double targetPosition) {
		gearTalon.set(targetPosition / 4096.0);
	}

	/**
	 * Enable Gear Loader
	 */
	public void enableGearTalon() {
		gearTalon.enable();
	}

	/**
	 * Disable Gear Loader
	 */
	public void disableGearTalon() {
		gearTalon.disable();
	}

	/**
	 * Enable onboard PID control
	 */
	public void enableGearPIDControl() {
		gearTalon.enableControl();
	}

	/**
	 * Disable onboard PID control
	 */
	public void disableGearPIDControl() {
		gearTalon.disableControl();
	}

	/**
	 * Get current encoder reading in pulses
	 * 
	 * @return PulseWidthPosition Encoder position (pulses) in range
	 *         [-MAX_DOUBLE, MAX_DOUBLE]
	 */
	public int getPulseWidthPosition() {
		return gearTalon.getPulseWidthPosition();
	}

	/**
	 * Get encoder present on Talon
	 * 
	 * @return Encoder present on Talon
	 */
	public boolean isSensorPresent() {
		return (FeedbackDeviceStatus.FeedbackStatusPresent == gearEncoderStatus);
	}

	/**
	 * Determine if PID controller is within tolerance
	 * 
	 * @return PID controller within tolerance
	 */
	public boolean reachedSetpoint() {
		return (Math.abs(gearTalon.getSetpoint() * 4096.0 - getPulseWidthPosition()) < GEAR_PID_TOLERANCE);
	}

	/**
	 * Continuously set PID setpoint to maintain position
	 * 
	 * Similar functionality to brake mode, but closed loop and error correcting
	 */
	public void runGearTalon() {
		gearTalon.set(gearTalon.getSetpoint());
	}
}
