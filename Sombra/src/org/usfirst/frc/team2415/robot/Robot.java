//For Harvey, from Yash

package org.usfirst.frc.team2415.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	long startTime;

	final double DEADBAND = 0.05;

	final int FRONT_RIGHT_TALON = 14;
	final int BACK_RIGHT_TALON = 15;
	final int FRONT_LEFT_TALON = 0;
	final int BACK_LEFT_TALON = 1;

	final int FORWARD_SOLENOID = 4;
	final int BACKWARD_SOLENOID = 5;

	final int SWITCH_SWITCH = 6;
	final int SCALE_SWITCH = 7;
	final int TOP_SWITCH = 8;

	public XboxController gamepad;
	public WPI_TalonSRX frontLeftTal, frontRightTal, backLeftTal, backRightTal;
	public DigitalInput switchSwitch, scaleSwitch, topSwitch;

	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();



	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);


		gamepad = new XboxController(0);

		frontLeftTal = new WPI_TalonSRX(FRONT_LEFT_TALON);
		frontRightTal = new WPI_TalonSRX(FRONT_RIGHT_TALON);
		backLeftTal = new WPI_TalonSRX(BACK_LEFT_TALON);
		backRightTal = new WPI_TalonSRX(BACK_RIGHT_TALON);

		switchSwitch = new DigitalInput(SWITCH_SWITCH);
		scaleSwitch = new DigitalInput(SCALE_SWITCH);
		topSwitch = new DigitalInput(TOP_SWITCH);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		startTime = System.currentTimeMillis();
		/*autoSelected = chooser.getSelected();
		  autoSelected = SmartDashboard.getString("Auto Selector",
		  defaultAuto);
		System.out.println("Auto selected: " + autoSelected);*/
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {


		/*switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}*/
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		double leftY = -gamepad.getRawAxis(4);
		double rightX =  gamepad.getRawAxis(1);

<<<<<<< HEAD
		
=======
>>>>>>> branch 'Protobot2018' of https://github.com/WiredCatsRobotics2415/Kitbot2017.git
		if (Math.abs(rightX) < DEADBAND) {
			rightX = 0;
		}
		if (Math.abs(leftY) < DEADBAND) {
			leftY = 0;
		}
<<<<<<< HEAD
		
		
=======
>>>>>>> branch 'Protobot2018' of https://github.com/WiredCatsRobotics2415/Kitbot2017.git
		double right = -leftY + rightX;
		double left = -leftY - rightX;
<<<<<<< HEAD
		
		if (leftY > 0) {
			left *= 1.2;
		} else {
			right *= 2.7;
=======
		if (leftY > 0) {
			left = (-leftY - rightX)*1.5;
		} else {
			right = (-leftY + rightX)*1.5;

>>>>>>> branch 'Protobot2018' of https://github.com/WiredCatsRobotics2415/Kitbot2017.git
		}

		frontLeftTal.set(0.6 * left);
		backLeftTal.set(0.6 * left);
		frontRightTal.set(0.6 * right);
		backRightTal.set(0.6 * right);

		//		if (gamepad.getBumper(Hand.kLeft)) {
		//			solenoid.set(DoubleSolenoid.Value.kForward); // Forward
		//		} else {
		//			solenoid.set(DoubleSolenoid.Value.kReverse); // Reverse
		//		}

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}