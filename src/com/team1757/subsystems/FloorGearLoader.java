package com.team1757.subsystems;

import com.team1757.commands.FloorGearRun;
import com.team1757.utils.FloorGearPivotControlMode;
import com.team1757.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class FloorGearLoader extends Subsystem {

	private final CANTalon pivotTalon = RobotMap.floorGearPivotTalon;
	private final CANTalon gearTalon = RobotMap.floorGearLoaderTalon;
	
	private final DigitalInput limitSwitch1 = RobotMap.floorGearLimitSwitch;
	private final DigitalInput limitSwitch2 = RobotMap.floorGearLimitSwitch2;
	
	private boolean isTriggerEnabled = false;
	
	private final double GEAR_PID_TOLERANCE = 0;
	// TODO Tolerance adjustment

	FeedbackDeviceStatus gearEncoderStatus = pivotTalon.isSensorPresent(FeedbackDevice.CtreMagEncoder_Absolute);

	public void initDefaultCommand() {
		setDefaultCommand(new FloorGearRun(FloorGearPivotControlMode.kCurrent));
	}

	/**
	 * Initialize PID feedback and constants for Pivot
	 * 
	 * pGain 0.64
	 * iGain 1.0E-4
	 * dGain 60.0
	 * fGain 0.0
	 * iZone 100
	 * closeLoopRampRate 36.0
	 * profile 0
	 */
	public void initializePivotPID() {
		pivotTalon.setPID(0.64, 0.0001, 60.0, 0.0, 100, 36.0, 0);
	}

	/** 
	 * Initialize encoder PID feedback
	 * 
	 * Set feedback device to encoder
	 * Set feedback mode to position
	 * 
	 * Set PID target to current position to lock in place
	 */
	public void initPivotEncoder() {
		pivotTalon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		pivotTalon.changeControlMode(TalonControlMode.Position);

		// Lock into place (assuming starting position is MatchStart)
		pivotTalon.set(getPulseWidthPosition());
	}
	
	/**
	 * Set Pivot PID target
	 * 
	 * @param targetPosition
	 *            Position in encoder counts
	 */
	public void setPivotTargetPosition(double targetPosition) {
		pivotTalon.set(targetPosition / 4096.0);
	}

	/**
	 * Enable Pivot Loader
	 */
	public void enablePivotTalon() {
		pivotTalon.enable();
	}

	/**
	 * Disable Pivot Loader
	 */
	public void disablePivotTalon() {
		pivotTalon.disable();
	}

	/**
	 * Enable onboard PID control
	 */
	public void enablePivotPIDControl() {
		pivotTalon.enableControl();
	}

	/**
	 * Disable onboard PID control
	 */
	public void disablePivotPIDControl() {
		pivotTalon.disableControl();
	}

	/**
	 * Get current encoder reading in pulses
	 * 
	 * @return PulseWidthPosition Encoder position (pulses) in range
	 *         [-MAX_DOUBLE, MAX_DOUBLE]
	 */
	public int getPulseWidthPosition() {
		return pivotTalon.getPulseWidthPosition();
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
	public boolean reachedPivotSetpoint() {
		return (Math.abs(pivotTalon.getSetpoint() * 4096.0 - getPulseWidthPosition()) < GEAR_PID_TOLERANCE);
	}

	/**
	 * Continuously set PID setpoint to maintain position
	 * 
	 * Similar functionality to brake mode, but closed loop and error correcting
	 */
	public void runPivotTalon() {
		pivotTalon.set(pivotTalon.getSetpoint());
	}
	
	
	/** 
	 * Enable collector talon
	 */
	public void enableCollector() {
		gearTalon.enable();
	}
	
	/** 
	 * Disable collector talon
	 */
	public void disableCollector() {
		gearTalon.disable();
	}
	
	/** 
	 * Enable collector talon control
	 */
	public void enableCollectorControl() {
		gearTalon.enableControl();
	}
	
	/** 
	 * Disable collector talon control
	 */
	public void disableCollectorControl() {
		gearTalon.disableControl();
	}
	
	
	/**
	 * Change the control mode of the collector mechanism
	 * @param mode
	 */
	public void changeCollectorControlMode(TalonControlMode mode) {
		gearTalon.changeControlMode(mode);
	}
	
	/**
	 * Run the floor gear loader collector
	 * 
	 * @param target
	 * 		Generally percentVbus in range [-1.0, 1.0]
	 */
	public void setCollectorTarget(double target) {
		gearTalon.set(target);
	}

	/** 
	 * Stop the floor gear loader collector
	 */
	public void stopCollector() {
		gearTalon.set(0);
		
	}
	
	public boolean isTriggerEnabled(){
		return isTriggerEnabled;
	}
	
	public void disableTrigger(){
		isTriggerEnabled = false;
	}

	public void enableTrigger(){
		isTriggerEnabled = true;
	}
	
	public boolean isSwitchActive(){
		return !(limitSwitch1.get() || limitSwitch2.get());
	}

}
