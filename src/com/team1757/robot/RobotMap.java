package com.team1757.robot;


import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import com.team1757.utils.IllegalSourceException;
import com.team1757.utils.MaxbotixUltrasonicAnalog;
import com.team1757.utils.MaxbotixUltrasonicSerial;
import com.team1757.utils.NavXGyroWrapper;
import com.team1757.utils.VariablePIDOutput;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// Talon ids
	private static final int driveTrainLeftFrontPort = 1;
	private static final int driveTrainLeftBackPort = 2;
	private static final int driveTrainRightFrontPort = 3;
	private static final int driveTrainRightBackPort = 4;

	private static final int shooterFlyWheelPort = 8;
	private static final int indexerTalonPort = 9;
	private static final int collectorFlyWheelPort = 7;
	private static final int dropGearLoaderTalonPort = 6;
	private static final int floorGearLoaderTalonPort = 10;
	private static final int floorGearPivotTalonPort = 11; // TODO Change to actual port
	private static final int liftTalonPort = 5;
	private static final int lift2TalonPort = 12;

	// Talon objects
	public static CANTalon driveTrainLeftFront;
	public static CANTalon driveTrainLeftBack;
	public static CANTalon driveTrainRightFront;
	public static CANTalon driveTrainRightBack;

	public static CANTalon shooterFlyWheel;
	public static CANTalon indexerTalon;
	public static CANTalon collectorFlyWheel;
	public static CANTalon dropGearLoaderTalon;
	public static CANTalon floorGearLoaderTalon;
	public static CANTalon floorGearPivotTalon;
	public static CANTalon liftTalon;
	public static CANTalon lift2Talon;

	// Robot objects
	public static RobotDrive driveTrainMecanumDrive;

	public static AHRS driveTrainNavX;
	public static MaxbotixUltrasonicAnalog ultrasonicAnalog;
	public static MaxbotixUltrasonicSerial ultrasonicSerial;

	public static PIDController gyroController;
	public static PIDController accelControllerX;
	public static PIDController accelControllerY;
	public static PIDController ultrasonicController;
	public static PIDController visionGearTranslationController;
	public static PIDController visionTranslationController;

	private static NavXGyroWrapper gyroInput;

	// Vision
	public static UsbCamera dropGearCam;
	public static UsbCamera floorGearCam;
	public static CameraServer server;
	
	public static DigitalInput dropGearReedSwitch;

	public static void init() {

		
		server = CameraServer.getInstance();
		dropGearCam = server.startAutomaticCapture(0);
		floorGearCam = server.startAutomaticCapture(1);
		
		dropGearReedSwitch = new DigitalInput(0);
		
		// Initialize Talons
		driveTrainLeftFront = new CANTalon(driveTrainLeftFrontPort);
		LiveWindow.addActuator("Drive Train", "Left Front", driveTrainLeftFront);

		driveTrainLeftBack = new CANTalon(driveTrainLeftBackPort);
		LiveWindow.addActuator("Drive Train", "Left Back", driveTrainLeftBack);

		driveTrainRightFront = new CANTalon(driveTrainRightFrontPort);
		LiveWindow.addActuator("Drive Train", "Right Front", driveTrainRightFront);

		driveTrainRightBack = new CANTalon(driveTrainRightBackPort);
		LiveWindow.addActuator("Drive Train", "Right Back", driveTrainRightBack);

		shooterFlyWheel = new CANTalon(shooterFlyWheelPort);
		LiveWindow.addActuator("Shooter", "ShooterFlyWheel", shooterFlyWheel);

		collectorFlyWheel = new CANTalon(collectorFlyWheelPort);
		LiveWindow.addActuator("Collector", "Collector FlyWheel", collectorFlyWheel);

		indexerTalon = new CANTalon(indexerTalonPort);
		LiveWindow.addActuator("Indexer", "Indexer FlyWheel", indexerTalon);

		dropGearLoaderTalon = new CANTalon(dropGearLoaderTalonPort);
		LiveWindow.addActuator("DropGearLoader", "Drop GearLoader Talon", dropGearLoaderTalon);

		floorGearLoaderTalon = new CANTalon(floorGearLoaderTalonPort);
		LiveWindow.addActuator("FloorGearLoader", "Floor GearLoader Talon", floorGearLoaderTalon);

		floorGearPivotTalon = new CANTalon(floorGearPivotTalonPort);
		LiveWindow.addActuator("FloorGearPivot", "Floor GearLoader Pivot", floorGearPivotTalon);

		liftTalon = new CANTalon(liftTalonPort);
		LiveWindow.addActuator("Lifter", "Lifter Talon", liftTalon);
		
		lift2Talon = new CANTalon(lift2TalonPort);
		LiveWindow.addActuator("Lifter", "Lifter Talon #2", liftTalon);

		// Configure Talons
		
		// Set floor 
		floorGearLoaderTalon.enableBrakeMode(false);
		floorGearLoaderTalon.setInverted(true);
		
		//Invert lifter talon #2, which should rotate in the opposite direction as #1
		lift2Talon.setInverted(true);

		// Invert talons to correct driving direction
		driveTrainLeftFront.setInverted(true);
		driveTrainLeftBack.setInverted(true);

		// Collector and shooter reverse
		collectorFlyWheel.setInverted(true);
		shooterFlyWheel.setInverted(true);

		// Change to brake mode tighter steering and autonomous stopping
		driveTrainLeftFront.enableBrakeMode(true);
		driveTrainLeftBack.enableBrakeMode(true);
		driveTrainRightFront.enableBrakeMode(true);
		driveTrainRightBack.enableBrakeMode(true);
		dropGearLoaderTalon.enableBrakeMode(true);

		// Soft Limits
		dropGearLoaderTalon.setReverseSoftLimit(270 / 4096.0);
		dropGearLoaderTalon.enableReverseSoftLimit(false);
		dropGearLoaderTalon.setForwardSoftLimit(1300 / 4096.0);
		dropGearLoaderTalon.enableForwardSoftLimit(false);

		dropGearLoaderTalon.configMaxOutputVoltage(4.2);
		dropGearLoaderTalon.configNominalOutputVoltage(4.2, 4.2);
		
		floorGearPivotTalon.setCurrentLimit(5);
		floorGearPivotTalon.EnableCurrentLimit(true);
		floorGearPivotTalon.configMaxOutputVoltage(4.2);
		floorGearPivotTalon.configNominalOutputVoltage(4.2, 4.2);

		liftTalon.enableBrakeMode(false);

		// Initialize RobotDrive
		driveTrainMecanumDrive = new RobotDrive(driveTrainLeftFront, driveTrainLeftBack, driveTrainRightFront,
				driveTrainRightBack);

		// Configure RobotDrive
		driveTrainMecanumDrive.setSafetyEnabled(true);
		driveTrainMecanumDrive.setExpiration(0.1);
		driveTrainMecanumDrive.setMaxOutput(1.0);

		// Initialize Ultrasonic Sensor

		// TODO Use ultrasonic
//		ultrasonicAnalog = new MaxbotixUltrasonicAnalog(0);

		// Configure Ultrasonic Sensor
//		ultrasonicSerial = new MaxbotixUltrasonicSerial();

		// Initialize NavX
		try {
			driveTrainNavX = new AHRS(SPI.Port.kMXP);
			driveTrainNavX.reset();
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}

		// Configure NavX
		driveTrainNavX.setPIDSourceType(PIDSourceType.kDisplacement);
		driveTrainNavX.reset();
		
		// Initialize PID Input/ Output (gyroscope)
		try {
			gyroInput = new NavXGyroWrapper(driveTrainNavX);
		} catch (IllegalSourceException e) {
			DriverStation.reportError("Error instantiating NavXWGyroWrapper: " + e.getMessage(), true);
		}

		// Initialize PIDController (gyroscope)
		gyroController = new PIDController(0.034, 0.0, 0.04, gyroInput, new VariablePIDOutput());

		// Configure PIDController (gyroscope)
		// SmartDashboard.putData("RotateController", gyroController);
		gyroController.setOutputRange(-1.0, 1.0);
		gyroController.setAbsoluteTolerance(2.0f);
		gyroController.setContinuous(true);
		driveTrainNavX.reset();

		// Initialize PIDController (rangefinder)
//		ultrasonicController = new PIDController(1.0, 0.0, 0.0, ultrasonicAnalog, new VariablePIDOutput());
//		SmartDashboard.putData("rangeController", ultrasonicController);
//		ultrasonicController.setOutputRange(-1, 1);

	}
}
