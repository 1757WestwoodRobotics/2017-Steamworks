package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
	private Joystick xbox360;
	private Button buttonA;

    private final int leftStickX = 0;
    private final int leftStickY = 1;
    private final int rightStickX = 4;
    private final int gamepadPort = 0;
    
    private final static double DEADBAND = 0.2;
    private final static double GAIN = 0.7;
    

    public OI() {
        xbox360 = new Joystick(gamepadPort);
        buttonA = new JoystickButton(xbox360, 1);
    }

    public Joystick getXbox360() {
        return xbox360;
    }
    
	public Button getButtonA() {
		return buttonA;
	}
    
    public int getAxisLeftStickX() {
    	return leftStickX;
    }
    
    public int getAxisLeftStickY() {
    	return leftStickY;
    }
    
    public int getxisRightStickX() {
    	return rightStickX;
    }
    
    public double getTranslateX() {
    	return inputControl(xbox360.getRawAxis(leftStickX));
    }
    
    public double getTranslateY() {
    	return inputControl(xbox360.getRawAxis(leftStickY));
    }
    
    public double getRotate() {
    	return inputControl(xbox360.getRawAxis(rightStickX));
    }
    
    
    public static double inputControl(double axis){
    	double output = 0;
    	if (axis > OI.DEADBAND) {
    		output = (Math.pow(axis - DEADBAND, 3) * GAIN) + ((axis - DEADBAND) * GAIN);
    	} else if (axis < -OI.DEADBAND) {
    		output = (Math.pow(axis + DEADBAND, 3) * GAIN) + ((axis + DEADBAND) * GAIN);
    	} else {
    		output = 0.0;
    	}
    	SmartDashboard.putNumber("Output", output);
    	SmartDashboard.putNumber("Axis", axis);
    	return output;
    	
    	
    }
}
