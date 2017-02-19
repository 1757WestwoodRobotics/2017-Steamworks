package com.team1757.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import com.team1757.utils.IllegalSourceException;
import com.team1757.utils.NavXGyroWrapper;
import com.team1757.utils.VariablePIDOutput;

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
	private static final int driveTrainLeftFrontPort = 1;
	private static final int driveTrainLeftBackPort = 2;
	private static final int driveTrainRightFrontPort = 3;
	private static final int driveTrainRightBackPort = 4;
	
	private static final int shooterFlyWheelPort = 5; 
	private static final int shooterIndexerPort = 9;
	
	private static final int collectorFlyWheelPort = 6;
	
	private static final int gearLoaderTalonPort = 7;
	
	private static final int liftTalonPort = 8;
		
	public static CANTalon driveTrainLeftFront;
    public static CANTalon driveTrainLeftBack;
    public static CANTalon driveTrainRightFront;
    public static CANTalon driveTrainRightBack;
    
    public static CANTalon shooterFlyWheel;
    public static CANTalon indexerFlyWheel;
    
    public static CANTalon collectorFlyWheel;
    
    public static CANTalon gearLoaderTalon;
    
    public static CANTalon liftTalon;
    
    public static RobotDrive driveTrainMecanumDrive;

    public static AHRS driveTrainNavX;
    
	public static PIDController gyroController;
	public static PIDController accelControllerX;
	public static PIDController accelControllerY;
	
	private static NavXGyroWrapper gyroInput;
	private static VariablePIDOutput gyroOutput;
	
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
        
        indexerFlyWheel = new CANTalon(shooterIndexerPort);
        LiveWindow.addActuator("Collector", "Indexer FlyWheel", indexerFlyWheel);
        
        gearLoaderTalon = new CANTalon(gearLoaderTalonPort);
        LiveWindow.addActuator("GearLoader", "GearLoader Talon", gearLoaderTalon);
        
        liftTalon = new CANTalon(liftTalonPort);
        LiveWindow.addActuator("Lifter", "Lifter Talon", liftTalon);
        
        // Configure Talons
        //Invert talons to correct driving
        driveTrainLeftFront.setInverted(true);
        driveTrainLeftBack.setInverted(true);
        //Change to brake mode tighter steering and autonomous stopping 
        driveTrainLeftFront.enableBrakeMode(true);
        driveTrainLeftBack.enableBrakeMode(true);
        driveTrainRightFront.enableBrakeMode(true);
        driveTrainRightBack.enableBrakeMode(true);
        
        gearLoaderTalon.enableBrakeMode(true);
        gearLoaderTalon.setReverseSoftLimit(210);
        gearLoaderTalon.enableReverseSoftLimit(true);
        gearLoaderTalon.setForwardSoftLimit(1430);
        gearLoaderTalon.enableForwardSoftLimit(true);
        gearLoaderTalon.configMaxOutputVoltage(4.8);
        gearLoaderTalon.configNominalOutputVoltage(4.8, 4.8);
        
        //liftTalon.enableBrakeMode(true);
        
        // Initialize RobotDrive
        driveTrainMecanumDrive = new RobotDrive(driveTrainLeftFront, driveTrainLeftBack,
              driveTrainRightFront, driveTrainRightBack);
        
        // Configure RobotDrive
        driveTrainMecanumDrive.setSafetyEnabled(true);
        driveTrainMecanumDrive.setExpiration(0.1);
        driveTrainMecanumDrive.setMaxOutput(1.0);
        
        // Initialize NavX
        try {
        	driveTrainNavX = new AHRS(SPI.Port.kMXP); 
        	driveTrainNavX.reset();
        } catch (RuntimeException ex ) {
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
        SmartDashboard.putData("RotateController", gyroController);
        gyroController.setOutputRange(-1.0, 1.0);
        gyroController.setAbsoluteTolerance(2.0f);
        gyroController.setContinuous(true);
    	driveTrainNavX.reset();
    	    
    }
}
