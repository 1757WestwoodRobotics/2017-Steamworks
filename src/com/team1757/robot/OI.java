package com.team1757.robot;

import com.team1757.commands.CollectReverseWithPercentVoltage;
import com.team1757.commands.CollectWithPercentVoltage;
import com.team1757.commands.DriveStraight;
import com.team1757.commands.GearManualInput;
import com.team1757.commands.GearMatchStart;
import com.team1757.commands.GearReceive;
import com.team1757.commands.GearScore;
import com.team1757.commands.GyroPIDClear;
import com.team1757.commands.LiftUp;
import com.team1757.commands.ResetGyro;
import com.team1757.commands.RotateDegrees;
import com.team1757.commands.RotateDegreesShortest;
import com.team1757.commands.RotateToAngle;
import com.team1757.commands.RunIndexer;
import com.team1757.commands.ShootWithSpeed;
import com.team1757.commands.ShootWithVoltage;
import com.team1757.commands.StopIndexer;
import com.team1757.commands.StopLifter;
import com.team1757.commands.StopShooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	//Joystick Objects
	private Joystick xbox360;
	private Joystick buttonBox;

	//Xbox360 Button Objects
	private JoystickButton xboxButtonA;
	private JoystickButton xboxButtonB;
	private JoystickButton xboxButtonX;
	private JoystickButton xboxButtonY;
	
	//ButtonBox Button Objects
	private JoystickButton buttonBoxButton1;
	private JoystickButton buttonBoxButton2;
	private JoystickButton buttonBoxButton3;
	private JoystickButton buttonBoxButton4;
	private JoystickButton buttonBoxButton5;
	private JoystickButton buttonBoxButton6;
	
	//Joystick Ports
	private final int xbox360Port = 0;
	private final int buttonBoxPort = 0;
	
	//Xbox360 Axis Ports
	private final int xboxLeftStickX = 0;
	private final int xboxLeftStickY = 1;
	//
	private final int xboxRightStickX = 4;
	private final int xboxRightStickY = 5;
	
	//Xbox360 Button Ports
	private final int xboxButtonAPort = 1;
	private final int xboxButtonBPort = 2;
	
	//ButtonBox Button Ports
	private final int buttonBoxButton1Port = 1;
	private final int buttonBoxButton2Port = 2;
	private final int buttonBoxButton3Port = 3;
	private final int buttonBoxButton4Port = 4;
	private final int buttonBoxButton5Port = 5;
	private final int buttonBoxButton6Port = 6;
	
	//Input Control Constants
	private final static double DEADBAND = 0.2;
	private final static double GAIN = 0.7;

	//OI Constructor
	public OI() {
		
		//Initialize Joysticks
		xbox360 = new Joystick(xbox360Port);
		buttonBox = new Joystick(buttonBoxPort);
		
		//Initialize Xbox360 Buttons
		xboxButtonA = new JoystickButton(xbox360, xboxButtonAPort);
		xboxButtonB = new JoystickButton(xbox360, xboxButtonBPort);
		
		//Initialize ButtonBox Buttons
		buttonBoxButton1 = new JoystickButton(buttonBox, buttonBoxButton1Port);
		
		// Initialize commands
		// Gyro Systems
		SmartDashboard.putData(new ResetGyro());
		SmartDashboard.putData(new GyroPIDClear());

		// Gyro Functions
		SmartDashboard.putData(new RotateToAngle());
		SmartDashboard.putData(new RotateDegrees());
		SmartDashboard.putData(new RotateDegreesShortest());
		SmartDashboard.putData(new DriveStraight(.5));

		// Shooter Functions
		SmartDashboard.putData(new ShootWithSpeed());
		SmartDashboard.putData(new ShootWithVoltage());
		SmartDashboard.putData(new StopShooter());

		// Indexer Functions
		SmartDashboard.putData(new RunIndexer());
		SmartDashboard.putData(new StopIndexer());
		
		// Collector Functions
		SmartDashboard.putData(new CollectWithPercentVoltage());
		SmartDashboard.putData(new CollectReverseWithPercentVoltage());

		// GearLoader Functions
		SmartDashboard.putData(new GearManualInput());
		SmartDashboard.putData(new GearMatchStart());
		SmartDashboard.putData(new GearReceive());
		SmartDashboard.putData(new GearScore());

		// Lifter Functions
		SmartDashboard.putData(new LiftUp());
		SmartDashboard.putData(new StopLifter());

		// Configure LiveWindow 
		SmartDashboard.putNumber("targetAngle", 0.0);
		SmartDashboard.putNumber("angularDelta", 0.0);
		SmartDashboard.putNumber("angularDeltaShortest", 0.0);
		SmartDashboard.putNumber("Gear Manual Target Position", 618.0);
		
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
		return inputControlY(xbox360.getRawAxis(leftStickX));
	}

	public double getTranslateY() {
		return inputControlX(xbox360.getRawAxis(leftStickY));
	}

	public double getRotate() {
		// Use inputControlY because that model works for rotation
		return inputControlY(xbox360.getRawAxis(rightStickX));
	}

	public static double inputControlY(double axis) {
		// Modeled by -4.59x^4+10.027x^3-6.344x^2+1.909x-0.0002595
		double output = 0;
		if (axis > OI.DEADBAND) {
			//output = (Math.pow(axis - DEADBAND, 3) * GAIN) + ((axis - DEADBAND) * 0.802);
			//NEEDS TESTING!!!
			output = -4.59*(Math.pow(axis, 4)) + 10.027*(Math.pow(axis, 3)) - 6.322*(Math.pow(axis, 2)) + 1.909*axis - 0.0002595;
		} else if (axis < -OI.DEADBAND) {
			//output = (Math.pow(axis + DEADBAND, 3) * GAIN) + ((axis + DEADBAND) * 0.802);
			//NEEDS TESTING
			axis = -axis;
			output = -(-4.59*(Math.pow(axis, 4)) + 10.027*(Math.pow(axis, 3)) - 6.322*(Math.pow(axis, 2)) + 1.909*axis - 0.0002595);
		} else {
			output = 0.0;
		}
		return output;
	}
	
	public static double inputControlYOld(double axis) {
		// Model by G(X-D)^3 + GX
		double output = 0;
		if (axis > OI.DEADBAND) {
			output = (Math.pow(axis - DEADBAND, 3) * GAIN) + ((axis - DEADBAND) * GAIN);
		} else if (axis < -OI.DEADBAND) {
			output = (Math.pow(axis + DEADBAND, 3) * GAIN) + ((axis + DEADBAND) * GAIN);
		} else {
			output = 0.0;
		}
		return output;
	}

	public static double inputControlX(double axis) {
		// Model by 1.5X + .2
		double output = 0;
		if (axis > OI.DEADBAND) {
			output = 1.5 * (axis - DEADBAND) + .2;
		} else if (axis < -OI.DEADBAND) {
			output = 1.5 * (axis + DEADBAND) - .2;
		} else {
			output = 0.0;
		}
		return output;
	}

}
