package com.team1757.commands;

import com.team1757.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GearWheelReverse extends Command {

	private static final double PVBUS = -1;

    public GearWheelReverse() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    protected void initialize() {
    	Robot.gearLoader.initGearWheel();
    	Robot.gearLoader.enableGearWheelTalon();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.gearLoader.setGearWheelTalon(PVBUS);
    	Robot.gearLoader.setGearWheelTalon(SmartDashboard.getNumber("GearWheelTalonRev", -1));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gearLoader.disableGearWheelTalon();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
