package org.usfirst.frc.team2415.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public final double ELEVATOR_MULTIPLIER = 0.25;
	public final double ELEVATOR_DEADBAND = 0.05;

	private Victor elev1, elev2;
	private DoubleSolenoid leftSolenoid, rightSolenoid;
	private static boolean pistonEngaged;

	public Elevator() {
		elev1 = new Victor(RobotMap.ELEVATOR1);
		elev2 = new Victor(RobotMap.ELEVATOR2);
		leftSolenoid = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.LEFT_SOLENOID_FRONT, RobotMap.LEFT_SOLENOID_BACK);
		//rightSolenoid = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.RIGHT_SOLENOID_FRONT, RobotMap.RIGHT_SOLENOID_BACK);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}

	public void engagePiston() {
		//senses whether pistons are engaged
		if (leftSolenoid.get() == DoubleSolenoid.Value.kForward) {
			pistonEngaged = true;
		} else if (leftSolenoid.get() != DoubleSolenoid.Value.kReverse) {
			pistonEngaged = false;
		} else {
			System.out.println("Error! Pistons are not in sync or reader error.");
		}	
		this.setPistons(!pistonEngaged); //set pistons to opposite of current state
	}
	private void setPistons(boolean pistonValue) {
		if (pistonValue) {
		leftSolenoid.set(DoubleSolenoid.Value.kForward);
		} else {
			leftSolenoid.set(DoubleSolenoid.Value.kReverse);
		}
		//rightSolenoid.set(pistonValue);
	}
	public void elevate(double leftTrigger, double rightTrigger) {
		if (leftTrigger > ELEVATOR_DEADBAND || rightTrigger > ELEVATOR_DEADBAND) {
			double elevatorMotorOutput = rightTrigger - leftTrigger;
			elev1.set(elevatorMotorOutput*ELEVATOR_MULTIPLIER);
			elev2.set(elevatorMotorOutput*ELEVATOR_MULTIPLIER);
		}
	}
}
