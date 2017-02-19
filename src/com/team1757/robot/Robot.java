package com.team1757.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.team1757.commands.CollectReverseWithPercentVoltage;
import com.team1757.commands.CollectWithPercentVoltage;
import com.team1757.commands.DriveStraight;
import com.team1757.commands.GearManualInput;
import com.team1757.commands.GearMatchStart;
import com.team1757.commands.GearReceive;
import com.team1757.commands.GearScore;
import com.team1757.commands.GetStatus;
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
import com.team1757.subsystems.BallCollector;
import com.team1757.subsystems.DriveTrain;
import com.team1757.subsystems.GearLoader;
import com.team1757.subsystems.Lifter;
import com.team1757.subsystems.Shooter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static DriveTrain driveTrain;
	public static Shooter shooter;
	public static BallCollector ballCollector;
	public static GearLoader gearLoader;
	public static Lifter lifter;
	public static OI oi;

	Command autonomousCommand;
	Command getStatus;
	SendableChooser<Command> chooser;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		// Initialize hardware
		RobotMap.init();
		
		// Initialize subsystems and default ManualDrive
		driveTrain = new DriveTrain();
		shooter = new Shooter();
		ballCollector = new BallCollector();
		gearLoader = new GearLoader();
		lifter = new Lifter();
		
		// Initialize OI
		oi = new OI();

		// Initial other commands
		SmartDashboard.putData(new ResetGyro());
		SmartDashboard.putData(new GyroPIDClear());
		
		SmartDashboard.putData(new RotateToAngle());
		SmartDashboard.putData(new RotateDegrees());

		SmartDashboard.putData(new RotateDegreesShortest());
		SmartDashboard.putData(new DriveStraight(.5));
		
		SmartDashboard.putData(new ShootWithSpeed());
		SmartDashboard.putData(new ShootWithVoltage());
		SmartDashboard.putData(new StopShooter());
		
		SmartDashboard.putData(new CollectWithPercentVoltage());
		SmartDashboard.putData(new CollectReverseWithPercentVoltage());

		SmartDashboard.putData(new GearManualInput());
		SmartDashboard.putData(new GearMatchStart());
		SmartDashboard.putData(new GearReceive());
		SmartDashboard.putData(new GearScore());
		
		SmartDashboard.putData(new RunIndexer());
		SmartDashboard.putData(new StopIndexer());
		
		SmartDashboard.putData(new LiftUp());
		SmartDashboard.putData(new StopLifter());
		
		// Configure LiveWindow 
		SmartDashboard.putNumber("targetAngle", 0.0);
		SmartDashboard.putNumber("angularDelta", 0.0);
		SmartDashboard.putNumber("angularDeltaShortest", 0.0);
		SmartDashboard.putNumber("Gear Manual Target Position", 618.0);
		
		getStatus = new GetStatus();
		getStatus.setRunWhenDisabled(true);
		
		
		chooser = new SendableChooser<>();
		// TODO chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		getStatus.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
