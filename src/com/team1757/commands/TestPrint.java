package com.team1757.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestPrint extends Command {

	public TestPrint() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("Initalizing test command");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.println("Executing test command");
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		System.out.println("Test command is finished");
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Ending test command");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("Test command has been interrupted");
	}
}
