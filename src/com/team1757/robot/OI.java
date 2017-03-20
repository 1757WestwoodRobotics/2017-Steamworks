package com.team1757.robot;

import com.team1757.commands.CGAutoRight;
import com.team1757.commands.CGAutoCrossLine;
import com.team1757.commands.CGAutoLeft;
import com.team1757.commands.CGAutoMiddle;
import com.team1757.commands.CGShootandIndex;
import com.team1757.commands.Collect;
import com.team1757.commands.DriveGyroAssisted;
import com.team1757.commands.DriveStraightY;
import com.team1757.commands.DriveToggleDirection;
import com.team1757.commands.GearRun;
import com.team1757.commands.VisionFaceReflectiveTape;
import com.team1757.commands.VisionFollowReflectiveTape;
import com.team1757.commands.DriveGyroPIDClear;
import com.team1757.commands.DriveManual;
import com.team1757.commands.DriveResetGyro;
import com.team1757.commands.DriveStraightToRange;
import com.team1757.commands.RotateDegreesShortest;
import com.team1757.commands.RotateToAngle;
import com.team1757.commands.Shoot;
import com.team1757.commands.Index;
import com.team1757.commands.Lift;
import com.team1757.commands.VisionCenterOnGearTranslationX;
import com.team1757.commands.VisionGetReadyToScoreGear;
import com.team1757.commands.VisionRingLight;
import com.team1757.commands.VisionToggleCamera;
import com.team1757.utils.CollectorControlMode;
import com.team1757.utils.GearControlMode;
import com.team1757.utils.IndexerControlMode;
import com.team1757.utils.LifterControlMode;
import com.team1757.utils.RingLightControlMode;
import com.team1757.utils.ShooterControlMode;
import com.team1757.utils.Unit;
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
	private final static double DEADBAND = 0.1;
	private final static double GAIN = 0.9;

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
		buttonBoxButton1.whenPressed(new GearRun(GearControlMode.kReceive));
		buttonBoxButton4.whenPressed(new GearRun(GearControlMode.kScore));
		buttonBoxButton2.toggleWhenPressed(new Collect(CollectorControlMode.kPercentForward));
		buttonBoxButton5.whenPressed(new GearRun(GearControlMode.kHug));
		buttonBoxButton3.toggleWhenPressed(new CGShootandIndex());
		buttonBoxButton6.whenPressed(new VisionToggleCamera());

		// Bind Commands to Xbox Controller
		xboxButtonY.whenPressed(new DriveToggleDirection());
		xboxButtonA.whenPressed(new DriveManual());
		xboxButtonLB.toggleWhenPressed(new Lift(LifterControlMode.kUp));
		xboxButtonRB.whileHeld(new Lift(LifterControlMode.kUp));
		
		// Put Commands on SmartDashboard
		// Drive functions
		SmartDashboard.putData(new DriveStraightY(1.65));
		SmartDashboard.putData(new DriveGyroAssisted());
		SmartDashboard.putData(new DriveToggleDirection());
		SmartDashboard.putData(new DriveStraightToRange(60, Unit.kInches));			

		// Gyro Systems
		SmartDashboard.putData(new DriveResetGyro());
		SmartDashboard.putData(new DriveGyroPIDClear());

		// Gyro Commands
		SmartDashboard.putData(new RotateToAngle());
		SmartDashboard.putData(new RotateDegreesShortest());

		// Shooter Commands
		SmartDashboard.putData(new Shoot(ShooterControlMode.kSpeedForward));
		SmartDashboard.putData(new Shoot(ShooterControlMode.kPercentForward));
		SmartDashboard.putData(new Shoot(ShooterControlMode.kVoltageForward));
		SmartDashboard.putData(new Shoot(ShooterControlMode.kStop));

		// Indexer Commands
		SmartDashboard.putData(new Index(IndexerControlMode.kPercentForward));
		SmartDashboard.putData(new Index(IndexerControlMode.kPercentReverse));
		SmartDashboard.putData(new Index(IndexerControlMode.kStop));

		// Collector Commands
		SmartDashboard.putData(new Collect(CollectorControlMode.kPercentForward));
		SmartDashboard.putData(new Collect(CollectorControlMode.kPercentReverse));
		SmartDashboard.putData(new Collect(CollectorControlMode.kStop));

		// GearLoader Commands
		
		SmartDashboard.putData(new GearRun(GearControlMode.kManual));
		SmartDashboard.putData(new GearRun(GearControlMode.kMatchStart));
		SmartDashboard.putData(new GearRun(GearControlMode.kReceive));
		SmartDashboard.putData(new GearRun(GearControlMode.kScore));
		SmartDashboard.putData(new GearRun(GearControlMode.kHug));

		// Lifter Commands
		SmartDashboard.putData(new Lift(LifterControlMode.kUp));
		SmartDashboard.putData(new Lift(LifterControlMode.kStop));

		// Vision Commands
		SmartDashboard.putData(new VisionFollowReflectiveTape());
		SmartDashboard.putData(new VisionFaceReflectiveTape());
		SmartDashboard.putData(new VisionCenterTranslationX());
		SmartDashboard.putData(new VisionGetReadyToScoreGear());
		SmartDashboard.putData(new VisionFaceGearTarget());
		SmartDashboard.putData(new VisionCenterOnGearTranslationX());
		SmartDashboard.putData(new VisionToggleCamera());

		// RingLight Commands
		SmartDashboard.putData(new VisionRingLight(RingLightControlMode.kGearOn));
		SmartDashboard.putData(new VisionRingLight(RingLightControlMode.kGearOff));

		// CommandGroup Functions
		SmartDashboard.putData(new CGShootandIndex());
		SmartDashboard.putData(new CGAutoMiddle());
		SmartDashboard.putData(new CGAutoRight());
		SmartDashboard.putData(new CGAutoLeft());
		SmartDashboard.putData(new CGAutoCrossLine());

		// Configure LiveWindow
		SmartDashboard.putNumber("targetAngle", 0.0);
		SmartDashboard.putNumber("angularDelta", 0.0);
		SmartDashboard.putNumber("angularDeltaShortest", 0.0);
		SmartDashboard.putNumber("Gear Manual Target Position", 618.0);
		
		//Other SmartDashboard
		SmartDashboard.putNumber("driveLength", 0);
		SmartDashboard.putNumber("driveSpeed", .2);

	}

	/**
	 * Get Xbox 360 gamepad object
	 * 
	 * @return Joystick Xbox 360 object representing Xbox 360 gamepad
	 */
	public Joystick getXbox360() {
		return xbox360;
	}

	/**
	 * Get Button Box gamepad object
	 * 
	 * @return Joystick buttonBox object representing Button Box gamepad
	 */
	public Joystick getButtonBox() {
		return buttonBox;
	}

	/**
	 * Get X translation operator operator input
	 * 
	 * @return Normalized input from X axis of Xbox 360 gamepad's left stick in range [-1, 1]
	 */
	public double getTranslateX() {
		return inputControlX(xbox360.getRawAxis(xboxLeftStickX));
	}

	/**
	 * Get Y translation operator input
	 * 
	 * @return Normalized input from Y axis of Xbox 360 gamepad's left stick in range [-1, 1]
	 */
	public double getTranslateY() {
		return inputControlY(xbox360.getRawAxis(xboxLeftStickY));
	}

	/**
	 * Get rotation operator input
	 * 
	 * @return Normalized input from X axis of Xbox 360 gamepad's right stick in range [-1, 1]
	 */
	public double getRotate() {
		return inputControlX(xbox360.getRawAxis(xboxRightStickX));
	}

	/**
	 * Get right trigger
	 * 
	 * @return Raw analog reading from Xbox 360 gamepad's right trigger in range [-1, 1]
	 */
	public double getRightTrigger() {
		return xbox360.getRawAxis(xboxRightTrigger);
	}

	/**
	 * Get left trigger
	 * 
	 * @return Raw analog reading from Xbox 360 gamepad's left trigger in range [-1, 1]
	 */
	public double getLeftTrigger() {
		return xbox360.getRawAxis(xboxLeftTrigger);
	}

	/**
	 * Sets the vibration motors of the Xbox 360 gamepad controller
	 * 
	 * @param type	The motor to vibrate. Left motor is rougher than right motor. Uses a RumbleType constant.
	 * @param value	The strength at which the motor vibrates in range [0, 1]
	 */
	public void vibrateXboxController(RumbleType type, double value) {
		xbox360.setRumble(type, value);
	}

	/**
	 * Y axis input normalization modeled by y=0.9x^3 + 0.1
	 * 
	 * @param axis	Raw operator input from Y axis in range [-1, 1] 
	 * @return Normalized output in range [-1, 1]
	 */
	public static double inputControlY(double axis) {
		// Modeled by y=0.9x^3 + 0.1
		double output = 0.0;
		if (axis > DEADBAND) {
			output = (Math.pow(axis, 3) * GAIN) + DEADBAND;
		} else if (axis < -DEADBAND) {
			axis = -axis;
			output = -(Math.pow(axis, 3) * GAIN) - DEADBAND;
		} else {
			output = 0.0;
		}
		return output;
	}

	/**
	 * X axis input normalization modeled by y=0.9x^2 + 0.1
	 * 
	 * @param axis	Raw operator input from X axis in range [-1, 1]
	 * @return Normalized output in range [-1, 1]
	 */
	public static double inputControlX(double axis) {
		// Modeled by y=0.9x^2 + 0.1
		double output = 0.0;
		if (axis > DEADBAND) {
			output = (Math.pow(axis, 2) * GAIN) + DEADBAND;
		} else if (axis < -DEADBAND) {
			axis = -axis;
			output = -(Math.pow(axis, 2) * GAIN) - DEADBAND;
		} else {
			output = 0.0;
		}
		return output;
	}
}
