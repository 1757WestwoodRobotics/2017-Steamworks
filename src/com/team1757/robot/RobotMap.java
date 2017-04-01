package com.team1757.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import com.team1757.subsystems.Vision;
import com.team1757.utils.IllegalSourceException;
import com.team1757.utils.NavXGyroWrapper;
import com.team1757.utils.VariablePIDOutput;
import com.team1757.utils.VisionCenterGearPIDSource;
import com.team1757.utils.VisionCenterPIDSource;
import com.team1757.utils.VisionDetectionType;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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
	private static final int gearLoaderTalonPort = 6;
	private static final int liftTalonPort = 5;

	// Talon objects
	public static CANTalon driveTrainLeftFront;
	public static CANTalon driveTrainLeftBack;
	public static CANTalon driveTrainRightFront;
	public static CANTalon driveTrainRightBack;

	public static CANTalon shooterFlyWheel;
	public static CANTalon indexerTalon;

	public static CANTalon collectorFlyWheel;

	public static CANTalon gearLoaderTalon;

	public static CANTalon liftTalon;

	// Robot objects
	public static RobotDrive driveTrainMecanumDrive;

	public static AHRS driveTrainNavX;

	public static PIDController gyroController;
	public static PIDController accelControllerX;
	public static PIDController accelControllerY;
	public static PIDController visionGearTranslationController;

	private static NavXGyroWrapper gyroInput;
	private static VariablePIDOutput gyroOutput;

	public static PIDController visionTranslationController;
	// TODO change back to private
	public static VisionCenterPIDSource visionCenterInput;
	public static VariablePIDOutput visionCenterOutput;

	private static VisionCenterGearPIDSource visionCenterGearInput;
	public static VariablePIDOutput visionCenterGearOutput;

	public static Vision vision;
	
	public static DigitalOutput gearRingLightController;

	public static UsbCamera gearCam; // = new UsbCamera("gearCam", 0);
	public static UsbCamera shooterCam; // = new UsbCamera("shooterCam", 1);

	// This will allow you to stream both at the same time...
	// public static UsbCamera gearCam =
	// CameraServer.getInstance().startAutomaticCapture(0);
	// public static UsbCamera shooterCam =
	// CameraServer.getInstance().startAutomaticCapture(1);

	public static void init() {
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
		LiveWindow.addActuator("Shooter", "Shooter FlyWheel", shooterFlyWheel);

		collectorFlyWheel = new CANTalon(collectorFlyWheelPort);
		LiveWindow.addActuator("Collector", "Collector FlyWheel", collectorFlyWheel);

		indexerTalon = new CANTalon(indexerTalonPort);
		LiveWindow.addActuator("Indexer", "Indexer FlyWheel", indexerTalon);

		gearLoaderTalon = new CANTalon(gearLoaderTalonPort);
		LiveWindow.addActuator("GearLoader", "GearLoader Talon", gearLoaderTalon);

		liftTalon = new CANTalon(liftTalonPort);
		LiveWindow.addActuator("Lifter", "Lifter Talon", liftTalon);

		// Configure Talons
		// Invert talons to correct driving
		driveTrainLeftFront.setInverted(true);
		driveTrainLeftBack.setInverted(true);
		// Change to brake mode tighter steering and autonomous stopping
		driveTrainLeftFront.enableBrakeMode(true);
		driveTrainLeftBack.enableBrakeMode(true);
		driveTrainRightFront.enableBrakeMode(true);
		driveTrainRightBack.enableBrakeMode(true);

		gearLoaderTalon.enableBrakeMode(true);

		// Soft Limits
		gearLoaderTalon.setReverseSoftLimit(270 / 4096.0);
		gearLoaderTalon.enableReverseSoftLimit(false);
		gearLoaderTalon.setForwardSoftLimit(1300 / 4096.0);
		gearLoaderTalon.enableForwardSoftLimit(false);

		gearLoaderTalon.configMaxOutputVoltage(4.2);
		gearLoaderTalon.configNominalOutputVoltage(4.2, 4.2);

		liftTalon.enableBrakeMode(false);
		
		// Initialize gearRingLight
		gearRingLightController = new DigitalOutput(0);
		

		// Initialize RobotDrive
		driveTrainMecanumDrive = new RobotDrive(driveTrainLeftFront, driveTrainLeftBack, driveTrainRightFront,
				driveTrainRightBack);

		// Configure RobotDrive
		driveTrainMecanumDrive.setSafetyEnabled(true);
		driveTrainMecanumDrive.setExpiration(0.1);
		driveTrainMecanumDrive.setMaxOutput(1.0);

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
		gyroOutput = new VariablePIDOutput();

		// Initialize PIDController (gyroscope)
		gyroController = new PIDController(0.034, 0.0, 0.04, gyroInput, gyroOutput);

		// Configure PIDController (gyroscope)
		//SmartDashboard.putData("RotateController", gyroController);
		gyroController.setOutputRange(-1.0, 1.0);
		gyroController.setAbsoluteTolerance(2.0f);
		gyroController.setContinuous(true);
		driveTrainNavX.reset();

		// Initialize PID Input/ Output (VisionCenter)
		visionCenterInput = new VisionCenterPIDSource();
		visionCenterInput.setVisionDetectionType(VisionDetectionType.ContourCenterX);
		visionCenterOutput = new VariablePIDOutput();

		// Initialize PIDController (VisionCenter)
		visionTranslationController = new PIDController(1.0, 0.0, 0.04, visionCenterInput, visionCenterOutput);

		// Configure PIDController (VisionCenter)
	//	SmartDashboard.putData("visionTranslationController", visionTranslationController);
		visionTranslationController.setInputRange(-1.0, 1.0);
		visionTranslationController.setOutputRange(-1, 1);
		visionTranslationController.setAbsoluteTolerance(.0005);

		// Initialize PID Input / Output (Gear)
		visionCenterGearInput = new VisionCenterGearPIDSource();
		visionCenterGearInput.setVisionDetectionType(VisionDetectionType.ContourCenterX);
		visionCenterGearOutput = new VariablePIDOutput();

		// Initialize PIDController (Gear)
		visionGearTranslationController = new PIDController(.03, 0.0002, .04, visionCenterGearInput,
				visionCenterGearOutput);

		// Configure PIDController (Gear)
		//SmartDashboard.putData("visionGearTranslationController", visionGearTranslationController);
		visionGearTranslationController.setOutputRange(-.5, .5);
		visionGearTranslationController.setAbsoluteTolerance(8);


	}
}
