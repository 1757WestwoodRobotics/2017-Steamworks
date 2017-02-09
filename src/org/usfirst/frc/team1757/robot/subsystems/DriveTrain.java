package org.usfirst.frc.team1757.robot.subsystems;

import org.usfirst.frc.team1757.robot.RobotMap;
import org.usfirst.frc.team1757.robot.commands.ManualDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDController;

/**
 * @author ACabey
 */
public class DriveTrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	//TODO negative setpoint and edge case of 359 don't work as intended
	
	private final AHRS driveTrainNavX = RobotMap.driveTrainNavX;
	private final PIDController gyroController = RobotMap.gyroController;
	private final RobotDrive driveTrainMecanumDrive = RobotMap.driveTrainMecanumDrive;
	private double targetAngle;

    public void initDefaultCommand() {
        setDefaultCommand(new ManualDrive());
    }
    
    /**
     * Motor
     */
    
    public void manualDrive(double translateX, double translateY, double rotate) {
    	driveTrainMecanumDrive.mecanumDrive_Cartesian(translateX, translateY, rotate, 0);
    }
    
    public void moveToTargetAngle() {
    	driveTrainMecanumDrive.mecanumDrive_Cartesian(0, 0, gyroController.get(), 0);
    }
    
    public void stop() {
    	driveTrainMecanumDrive.stopMotor();
    }
    
    /** 
     * Gyro
     */
    public void resetGyro() {
    	driveTrainNavX.reset();
    }
    
    public double getCurrentBoundedAngle() {
    	return driveTrainNavX.getAngle() % 360;
    }
    
    public double getCurrentRawAngle() {
    	return driveTrainNavX.getAngle();
    }
    
    /** 
     * Gyro PID Controller
     */
    
    public void enableGyroPID() {
    	gyroController.enable();
    }
    
    public void disableGyroPID() {
    	gyroController.disable();
    }
    
    public double getTargetAngle() {
    	return targetAngle;
    }
    
    public boolean reachedSetpoint() {
    	return (Math.abs(gyroController.getError()) <= .3);
    }
    
    public void setTargetAngle(double angle) {
    	targetAngle = angle % 360;
    	gyroController.setSetpoint(targetAngle);
    }
}

