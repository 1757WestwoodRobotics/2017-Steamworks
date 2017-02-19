package com.team1757.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team1757.commands.StopLifter;
import com.team1757.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author ACabey
 */
public class Lifter extends Subsystem {
	
	private final CANTalon liftTalon = RobotMap.liftTalon;

    public void initDefaultCommand() {
        setDefaultCommand(new StopLifter());
    }
    
    public void enableLifter() {
    	liftTalon.enable();
    }
    
    public void disableLifter() {
    	liftTalon.disable();
    }
    
    public void stopLifter() {
    	liftTalon.set(0);
    }
    
    public void setModePercentVoltage() {
    	liftTalon.changeControlMode(TalonControlMode.PercentVbus);
    }
    
    public void setLiftTarget(double target) {
    	liftTalon.set(target);
    }
}
