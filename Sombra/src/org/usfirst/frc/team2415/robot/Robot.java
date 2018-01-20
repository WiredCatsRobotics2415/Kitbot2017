package org.usfirst.frc.team2415.robot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
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
	boolean recordMode = true;
	BufferedWriter bw = null;
	BufferedReader br = null;
	
	long startTime;
	
	final double DEADBAND = 0.05;
	
	final int RIGHT_TALON = 0;
	final int LEFT_TALON = 1;
	
	final int FORWARD_SOLENOID = 0;
	final int BACKWARD_SOLENOID = 1;

	public XboxController gamepad;
	public CANTalon leftTal, rightTal;
	
	public DoubleSolenoid solenoid;
	public Compressor compressor;
	
	final String defaultAuto = "Default";
	final String replayAuto = "Replay Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	public boolean bool;
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("Replay Auto", replayAuto);
		SmartDashboard.putData("Auto choices", chooser);
		
		bool = false;
		
		compressor = new Compressor();

		gamepad = new XboxController(0);
		
		leftTal = new CANTalon(LEFT_TALON);
		rightTal = new CANTalon(RIGHT_TALON);
		
		leftTal.set(0);
		rightTal.set(0);

		solenoid = new DoubleSolenoid(FORWARD_SOLENOID, BACKWARD_SOLENOID);
		
		solenoid.set(DoubleSolenoid.Value.kReverse);
		
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
		autoSelected = chooser.getSelected();
		System.out.println("Auto selected: " + autoSelected);
		if (autoSelected.equals(replayAuto)) {
			try {
				br = new BufferedReader(new FileReader("/home/lvuser/test.auto"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SmartDashboard.putBoolean("File loaded", br != null);
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		switch (autoSelected) {
		case replayAuto:
			if (br != null) {
				try {
					String line = br.readLine();
					String[] stuff = line.split(",");
					double a = Double.parseDouble(stuff[0]);
					double b = Double.parseDouble(stuff[1]);
					boolean c = Boolean.parseBoolean(stuff[2]);
					arcadeDrive(a, b);
					solenoidSwitch(c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
		
	}
	
	public void teleopInit(){
		if (recordMode) {
			try {
				bw = new BufferedWriter(new FileWriter("/home/lvuser/test.auto"));
				System.out.println("Buffered Writer Created!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		if (bw != null && gamepad.getRawButton(6)) {
			try {
				if (solenoid.get() == DoubleSolenoid.Value.kForward) {
					bool = true;
				} else {
					bool = false;
				}
				bw.write(gamepad.getRawAxis(1) + "," + gamepad.getRawAxis(4) + "," + bool + "\n");
				System.out.println("WRITING!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		arcadeDrive(gamepad.getRawAxis(1), gamepad.getRawAxis(4));
		
		if (gamepad.getAButton()) {
			solenoidSwitch(true);
		} else {
			solenoidSwitch(false);
		}
//		SmartDashboard.putNumber("Encoder Ticks", encoder.getDistance());
	
		/*double leftY = gamepad.getRawAxis(1);
		double rightX = gamepad.getRawAxis(4);

		if (rightX < DEADBAND) {
			rightX = 0;
		}
		if (leftY < DEADBAND) {
			leftY = 0;
		}
		
		double left = leftY + rightX;
		double right = leftY - rightX;

		leftTal.set(0.67 * left);
		rightTal.set(0.67 * right);

		if (gamepad.getAButton()) {
			solenoid.set(DoubleSolenoid.Value.kForward); // Forward
		} else {
			solenoid.set(DoubleSolenoid.Value.kReverse); // Reverse
		}*/
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
	
	public void arcadeDrive(double leftY, double rightX) {
		if (rightX < DEADBAND) {
			rightX = 0;
		}
		if (leftY < DEADBAND) {
			leftY = 0;
		}
		
		double left = leftY + rightX;
		double right = leftY - rightX;
		
		SmartDashboard.putString("motorspeed ", "L: " + left + " R: " + right);
		SmartDashboard.putNumber("Left Y", leftY);
		SmartDashboard.putNumber("Right X", rightX);

		leftTal.set(0.67 * left);
		rightTal.set(0.67 * right);
	}
	
	public void solenoidSwitch(boolean extend) {
		if (extend) {
			solenoid.set(DoubleSolenoid.Value.kForward);
		} else {
			solenoid.set(DoubleSolenoid.Value.kReverse);
		}
	}
	
}