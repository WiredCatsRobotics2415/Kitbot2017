//For Harvey, from Yash

package org.usfirst.frc.team2415.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
//import edu.wpi.first.wpilibj.DigitalInput;
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
	final double LEFT_SPEED_COEFFICIENT = 0.6;
	final double RIGHT_SPEED_COEFFICIENT = 0.9;
/*
	final int SWITCH_SWITCH = 6;
	final int SCALE_SWITCH = 7;
	final int TOP_SWITCH = 8;
*/
	public XboxController gamepad;
	public WPI_TalonSRX frontLeftTal, backLeftTal, backRightTal, frontRightTal, pivot;
	public DigitalInput highPiv, lowPiv;

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

		//compressor = new Compressor();

		gamepad = new XboxController(0);

		frontLeftTal = new WPI_TalonSRX(RobotMap.LEFT_TALON_FRONT);
		frontRightTal = new WPI_TalonSRX(RobotMap.RIGHT_TALON_FRONT);
		backLeftTal = new WPI_TalonSRX(RobotMap.LEFT_TALON_BACK);
		backRightTal = new WPI_TalonSRX(RobotMap.RIGHT_TALON_BACK);
		pivot = new WPI_TalonSRX(RobotMap.PIVOT);

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
		while (System.currentTimeMillis()+5000 > startTime) {
			/*autoSelected = chooser.getSelected();
			  autoSelected = SmartDashboard.getString("Auto Selector",
			  defaultAuto);
			System.out.println("Auto selected: " + autoSelected);*/
		}
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


		if (Math.abs(rightX) < DEADBAND) {
			rightX = 0;
		}
		if (Math.abs(leftY) < DEADBAND) {
			leftY = 0;
		}

		double right = -leftY + rightX;
		double left = -leftY - rightX; //need to make this a bit slower to compensate
		

		frontLeftTal.set(LEFT_SPEED_COEFFICIENT * left);
		backLeftTal.set(LEFT_SPEED_COEFFICIENT * left);
		frontRightTal.set(RIGHT_SPEED_COEFFICIENT * right);
		backRightTal.set(RIGHT_SPEED_COEFFICIENT * right);
		
		if (gamepad.getAButton() && !highPiv.get()) {
			pivot.set(0.5);
		} else if (gamepad.getBButton() && !lowPiv.get()) {
			pivot.set(-0.5);
		} else {
			pivot.set(0);
		}
		
		//elevator.elevate(gamepad.getRawAxis(2), gamepad.getRawAxis(3));
		
		
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