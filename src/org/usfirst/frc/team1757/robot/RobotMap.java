package org.usfirst.frc.team1757.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import com.team1757.utils.IllegalSourceException;
import com.team1757.utils.NavXAccelWrapper;
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
	private static int driveTrainLeftFrontPort = 1;
	private static int driveTrainLeftBackPort = 2;
	private static int driveTrainRightFrontPort = 3;
	private static int driveTrainRightBackPort = 4;
    
	
	public static CANTalon driveTrainLeftFront;
    public static CANTalon driveTrainLeftBack;
    public static CANTalon driveTrainRightFront;
    public static CANTalon driveTrainRightBack;
    
    
    public static RobotDrive driveTrainMecanumDrive;

    public static AHRS driveTrainNavX;
    
	public static PIDController gyroController;
	public static PIDController accelController;
	private static NavXGyroWrapper gyroInput;
	private static VariablePIDOutput gyroOutput;
	private static NavXAccelWrapper accelInput;
	private static VariablePIDOutput accelOutput;
    
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
        
        // Configure Talons
        //Invert talons to correct driving
        driveTrainLeftFront.setInverted(true);
        driveTrainLeftBack.setInverted(true);
        //Change to brake mode tighter steering and autonomous stopping 
        driveTrainLeftFront.enableBrakeMode(true);
        driveTrainLeftBack.enableBrakeMode(true);
        driveTrainRightFront.enableBrakeMode(true);
        driveTrainRightBack.enableBrakeMode(true);
        
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
        
        try {
			accelInput = new NavXAccelWrapper(driveTrainNavX);
		} catch (IllegalSourceException e) {
			DriverStation.reportError("Error instantiating NavXWAccelWrapper: " + e.getMessage(), true);
		}
        accelOutput = new VariablePIDOutput();
        
        // Initialize PIDController (gyroscope)
        gyroController = new PIDController(0.034, 0.0, 0.04, gyroInput, gyroOutput);
        
        // Configure PIDController (gyroscope)
        SmartDashboard.putData("RotateController", gyroController);
        gyroController.setOutputRange(-1.0, 1.0);
        gyroController.setAbsoluteTolerance(2.0f);
        gyroController.setContinuous(true);
    	driveTrainNavX.reset();
    	    
        // Initialize PIDController (accelerometer)
        accelController = new PIDController(.01,0.0,0.0, accelInput, accelOutput);
        
        // Configure PIDController (accelerometer)
        SmartDashboard.putData("DisplacementController", accelController);
        gyroController.setOutputRange(-1.0, 1.0);
        gyroController.setAbsoluteTolerance(2.0f);
        gyroController.setContinuous(false);
    }
}
