package com.team1757.robot;

import com.team1757.commands.CGCenterAndScoreGear;
import com.team1757.commands.CGShootandIndex;
import com.team1757.commands.CGShootandIndexStop;
import com.team1757.commands.CollectReverseWithPercentVoltage;
import com.team1757.commands.CollectWithPercentVoltage;
import com.team1757.commands.CollectorStop;
import com.team1757.commands.DriveGyroAssisted;
import com.team1757.commands.DriveStraight;
import com.team1757.commands.DriveToggleDirection;
import com.team1757.commands.VisionFaceReflectiveTape;
import com.team1757.commands.VisionFollowReflectiveTape;
import com.team1757.commands.GearManualInput;
import com.team1757.commands.GearMatchStart;
import com.team1757.commands.GearReceive;
import com.team1757.commands.GearScore;
import com.team1757.commands.DriveGyroPIDClear;
import com.team1757.commands.DriveManual;
import com.team1757.commands.LiftUp;
import com.team1757.commands.DriveResetGyro;
import com.team1757.commands.RotateDegrees;
import com.team1757.commands.RotateDegreesShortest;
import com.team1757.commands.RotateToAngle;
import com.team1757.commands.IndexerRun;
import com.team1757.commands.ShootWithSpeed;
import com.team1757.commands.ShootWithVoltage;
import com.team1757.commands.IndexerStop;
import com.team1757.commands.LifterStop;
import com.team1757.commands.ShooterStop;
import com.team1757.commands.VisionCenterOnGearTranslationX;
import com.team1757.commands.VisionGetReadyToScoreGear;
import com.team1757.commands.VisionShooterRingLightOff;
import com.team1757.commands.VisionShooterRingLightOn;
import com.team1757.commands.VisionGearRingLightOff;
import com.team1757.commands.VisionGearRingLightOn;
import com.team1757.commands.VisionToggleCamera;
import com.team1757.commands.VisionCenterTranslationX;
import com.team1757.commands.VisionFaceGearTarget;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	// Joystick Objects
	private Joystick xbox360;
	private Joystick buttonBox;

	// Xbox360 Button Objects
	private JoystickButton xboxButtonA;
	private JoystickButton xboxButtonB;
	private JoystickButton xboxButtonX;
	private JoystickButton xboxButtonY;
	private JoystickButton xboxButtonLB;
	private JoystickButton xboxButtonRB;
	private JoystickButton xboxButtonBack;
	private JoystickButton xboxButtonStart;

	// ButtonBox Button Objects
	private JoystickButton buttonBoxButton1;
	private JoystickButton buttonBoxButton2;
	private JoystickButton buttonBoxButton3;
	private JoystickButton buttonBoxButton4;
	private JoystickButton buttonBoxButton5;
	private JoystickButton buttonBoxButton6;

	// Joystick Ports
	private final int xbox360Port = 0;
	private final int buttonBoxPort = 1;

	// Xbox360 Axis Ports
	private final int xboxLeftStickX = 0;
	private final int xboxLeftStickY = 1;
	private final int xboxLeftTrigger = 2;
	private final int xboxRightTrigger = 3;
	private final int xboxRightStickX = 4;
	private final int xboxRightStickY = 5;

	// Xbox360 Button Ports
	private final int xboxButtonAPort = 1;
	private final int xboxButtonBPort = 2;
	private final int xboxButtonXPort = 3;
	private final int xboxButtonYPort = 4;
	private final int xboxButtonLBPort = 5;
	private final int xboxButtonRBPort = 6;
	private final int xboxButtonBackPort = 7;
	private final int xboxButtonStartPort = 8;

	// ButtonBox Button Ports
	private final int buttonBoxButton1Port = 1;
	private final int buttonBoxButton2Port = 2;
	private final int buttonBoxButton3Port = 3;
	private final int buttonBoxButton4Port = 4;
	private final int buttonBoxButton5Port = 5;
	private final int buttonBoxButton6Port = 6;

	// Input Control Constants
	private final static double DEADBAND = 0.2;
	private final static double DEADBAND_NEW = 0.1;
	private final static double GAIN = 0.7;

	// OI Constructor
	public OI() {

		// Initialize Joysticks
		xbox360 = new Joystick(xbox360Port);
		buttonBox = new Joystick(buttonBoxPort);

		// Initialize Xbox360 Buttons
		xboxButtonA = new JoystickButton(xbox360, xboxButtonAPort);
		xboxButtonB = new JoystickButton(xbox360, xboxButtonBPort);
		xboxButtonX = new JoystickButton(xbox360, xboxButtonXPort);
		xboxButtonY = new JoystickButton(xbox360, xboxButtonYPort);
		xboxButtonLB = new JoystickButton(xbox360, xboxButtonLBPort);
		xboxButtonRB = new JoystickButton(xbox360, xboxButtonRBPort);
		xboxButtonBack = new JoystickButton(xbox360, xboxButtonBackPort);
		xboxButtonStart = new JoystickButton(xbox360, xboxButtonStartPort);

		// Initialize ButtonBox Buttons
		buttonBoxButton1 = new JoystickButton(buttonBox, buttonBoxButton1Port);
		buttonBoxButton2 = new JoystickButton(buttonBox, buttonBoxButton2Port);
		buttonBoxButton3 = new JoystickButton(buttonBox, buttonBoxButton3Port);
		buttonBoxButton4 = new JoystickButton(buttonBox, buttonBoxButton4Port);
		buttonBoxButton5 = new JoystickButton(buttonBox, buttonBoxButton5Port);
		buttonBoxButton6 = new JoystickButton(buttonBox, buttonBoxButton6Port);

		// Bind Commands to ButtonBox
		buttonBoxButton1.whenPressed(new GearReceive());
		buttonBoxButton4.whenPressed(new GearScore());
		buttonBoxButton2.toggleWhenPressed(new CollectWithPercentVoltage());
		buttonBoxButton5.toggleWhenPressed(new VisionToggleCamera());
		buttonBoxButton3.toggleWhenPressed(new CGShootandIndex());
		buttonBoxButton6.toggleWhenPressed(new CGCenterAndScoreGear());

		// Bind Commands to Xbox Controller
		xboxButtonY.whenPressed(new DriveToggleDirection());
		xboxButtonA.whenPressed(new DriveManual());

		// Put Commands on SmartDashboard
		// Drive functions
		SmartDashboard.putData(new DriveStraight(.5));
		SmartDashboard.putData(new DriveGyroAssisted());
		SmartDashboard.putData(new DriveToggleDirection());

		// Gyro Systems
		SmartDashboard.putData(new DriveResetGyro());
		SmartDashboard.putData(new DriveGyroPIDClear());

		// Gyro Commands
		SmartDashboard.putData(new RotateToAngle());
		SmartDashboard.putData(new RotateDegrees());
		SmartDashboard.putData(new RotateDegreesShortest());

		// Shooter Commands
		SmartDashboard.putData(new ShootWithSpeed());
		SmartDashboard.putData(new ShootWithVoltage());
		SmartDashboard.putData(new ShooterStop());

		// Indexer Commands
		SmartDashboard.putData(new IndexerRun());
		SmartDashboard.putData(new IndexerStop());

		// Collector Commands
		SmartDashboard.putData(new CollectWithPercentVoltage());
		SmartDashboard.putData(new CollectReverseWithPercentVoltage());
		SmartDashboard.putData(new CollectorStop());

		// GearLoader Commands
		SmartDashboard.putData(new GearManualInput());
		SmartDashboard.putData(new GearMatchStart());
		SmartDashboard.putData(new GearReceive());
		SmartDashboard.putData(new GearScore());

		// Lifter Commands
		SmartDashboard.putData(new LiftUp());
		SmartDashboard.putData(new LifterStop());

		// Vision Commands
		SmartDashboard.putData(new VisionFollowReflectiveTape());
		SmartDashboard.putData(new VisionFaceReflectiveTape());
		SmartDashboard.putData(new VisionCenterTranslationX());
		SmartDashboard.putData(new VisionGetReadyToScoreGear());
		SmartDashboard.putData(new VisionFaceGearTarget());
		SmartDashboard.putData(new VisionCenterOnGearTranslationX());

		// RingLight Commands
		SmartDashboard.putData(new VisionGearRingLightOn());
		SmartDashboard.putData(new VisionGearRingLightOff());
		SmartDashboard.putData(new VisionShooterRingLightOn());
		SmartDashboard.putData(new VisionShooterRingLightOff());

		// CommandGroup Functions
		SmartDashboard.putData(new CGShootandIndex());
		SmartDashboard.putData(new CGShootandIndexStop());
		
		// Configure LiveWindow
		SmartDashboard.putNumber("targetAngle", 0.0);
		SmartDashboard.putNumber("angularDelta", 0.0);
		SmartDashboard.putNumber("angularDeltaShortest", 0.0);
		SmartDashboard.putNumber("Gear Manual Target Position", 618.0);

	}

	public Joystick getXbox360() {
		return xbox360;
	}

	public Joystick getButtonBox() {
		return buttonBox;
	}

	public int getXboxAxisLeftStickX() {
		return xboxLeftStickX;
	}

	public int getXboxAxisLeftStickY() {
		return xboxLeftStickY;
	}

	public int getAxisRightStickX() {
		return xboxRightStickX;
	}

	public double getTranslateX() {
		return inputControlX(xbox360.getRawAxis(xboxLeftStickX));
	}

	public double getTranslateY() {
		return inputControlY(xbox360.getRawAxis(xboxLeftStickY));
	}

	public double getRotate() {
		// Use inputControlY because that model works for rotation
		return inputControlY(xbox360.getRawAxis(xboxRightStickX));
	}
	
	/**
	 * Sets the vibration motors of the Xbox 360 gamepad controller.
	 * 
	 * @param type		Pick a motor to vibrate. Left motor is rougher than right motor. ex.) RumbleType.kLeftMotor
	 * @param value		The strength at which the motor vibrates. Values from 0 to 1.
	 */
	public void vibrateXboxController(RumbleType type, double value) {
		xbox360.setRumble(type, value);
	}
	
	public static double inputControlY(double axis) {
		// Modeled by y=0.9x^3 + 0.1
		double output = 0.0;
		if (axis > DEADBAND_NEW) {
			output = (0.9 * Math.pow(axis, 3)) + (0.1);
		} else if (axis < -DEADBAND_NEW) {
			axis = -axis;
			output = -(0.9 * Math.pow(axis, 3)) - (0.1);
		} else {
			output = 0.0;
		}
		return output;
	}

	public static double inputControlYOld2(double axis) {
		// Modeled by -4.59x^4+10.027x^3-6.344x^2+1.909x-0.0002595
		double output = 0;
		if (axis > OI.DEADBAND) {
			// output = (Math.pow(axis - DEADBAND, 3) * GAIN) + ((axis -
			// DEADBAND) * 0.802);
			// NEEDS TESTING!!!
			output = -4.59 * (Math.pow(axis, 4)) + 10.027 * (Math.pow(axis, 3)) - 6.322 * (Math.pow(axis, 2))
					+ 1.909 * axis - 0.0002595;
		} else if (axis < -OI.DEADBAND) {
			// output = (Math.pow(axis + DEADBAND, 3) * GAIN) + ((axis +
			// DEADBAND) * 0.802);
			// NEEDS TESTING
			axis = -axis;
			output = -(-4.59 * (Math.pow(axis, 4)) + 10.027 * (Math.pow(axis, 3)) - 6.322 * (Math.pow(axis, 2))
					+ 1.909 * axis - 0.0002595);
		} else {
			output = 0.0;
		}
		return output;
	}

	public static double inputControlYOld(double axis) {
		// Model by G(X-D)^3 + GX
		double output = 0;
		if (axis > OI.DEADBAND) {
			output = (Math.pow(axis - DEADBAND, 3) * GAIN) + ((axis - DEADBAND) * GAIN);
		} else if (axis < -OI.DEADBAND) {
			output = (Math.pow(axis + DEADBAND, 3) * GAIN) + ((axis + DEADBAND) * GAIN);
		} else {
			output = 0.0;
		}
		return output;
	}

	public static double inputControlX(double axis) {
		// Model by 1.5X + .2
		double output = 0;
		if (axis > OI.DEADBAND) {
			output = 1.5 * (axis - DEADBAND) + .2;
		} else if (axis < -OI.DEADBAND) {
			output = 1.5 * (axis + DEADBAND) - .2;
		} else {
			output = 0.0;
		}
		return output;
	}

}
