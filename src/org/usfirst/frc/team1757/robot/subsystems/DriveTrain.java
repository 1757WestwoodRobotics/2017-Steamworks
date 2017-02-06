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
	
	private final AHRS driveTrainNavX = RobotMap.driveTrainNavX;
	private final PIDController gyroController = RobotMap.gyroController;
	private final RobotDrive driveTrainMecanumDrive = RobotMap.driveTrainMecanumDrive;

    public void initDefaultCommand() {
        setDefaultCommand(new ManualDrive());
    }
    
    public void manualDrive(double translateX, double translateY, double rotate) {
    	driveTrainMecanumDrive.mecanumDrive_Cartesian(translateX, translateY, rotate, 0);
    }
    
    public void enablePID() {
    	gyroController.enable();
    }
    
    public void disablePID() {
    	gyroController.disable();
    }
    
    public double getCurrentBoundedAngle() {
    	return driveTrainNavX.getAngle() % 360;
    }
    
    public double getCurrentRawAngle() {
    	return driveTrainNavX.getAngle();
    }
    
    public double getTargetAngle() {
    	return gyroController.getSetpoint();
    }
    
    public void setTargetAngle(double angle) {
    	gyroController.setSetpoint(angle);
    	driveTrainMecanumDrive.mecanumDrive_Cartesian(0, 0, gyroController.get(), 0);
    }
    
    public void stop() {
    	driveTrainMecanumDrive.stopMotor();
    }
}

