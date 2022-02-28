package frc.robot.subsystems;

import frc.robot.Constants.DriveConstants;
import frc.robot.utils.DriveHelper;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;

import frc.robot.Constants.ServoConstants;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.AnalogInput;



/**
 * Drive represents the drivetrain. It is composed of 4 CIM motors (all controlled with Talon SRXs), a Pigeon gyro, and
 * two pneumatic pistons. This class is packed with documentation to better understand design choices and robot
 * programming in general.
 */
public final class Drive extends SubsystemBase {

    // These variables are final because they only need to be instantiated once (after all, you don't need to create a
    // new left master TalonSRX).
    private final CANSparkMax leftMaster, leftSlave, rightMaster, rightSlave;
    private final RelativeEncoder leftMasterE, leftSlaveE, rightMasterE, rightSlaveE;
    private final DriveHelper driveHelper;

    private final Servo camServo;
    private int camServoRotation = 90;

    private final DoubleSolenoid shifter;

    private final AnalogInput ultra;

    

    /**
     * The single instance of {@link Drive} used to implement the "singleton" design pattern. A description of the
     * singleton design pattern can be found in the JavaDoc for {@link Drive::getInstance()}.
     */
    private static Drive instance;

    /**
     * The constructor, which is private in order to implement the "singleton" design pattern. A description of the
     * singleton design pattern can be found in the JavaDoc for {@link Drive::getInstance()}.
     */
    private Drive() {
        leftMaster = new CANSparkMax(DriveConstants.LEFT_MASTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSlave = new CANSparkMax(DriveConstants.LEFT_SLAVE_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMaster = new CANSparkMax(DriveConstants.RIGHT_MASTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSlave = new CANSparkMax(DriveConstants.RIGHT_SLAVE_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        driveHelper = new DriveHelper(leftMaster, rightMaster);
        configureSparks();

        leftMasterE = leftMaster.getEncoder();
        leftSlaveE = leftSlave.getEncoder();
        rightMasterE = rightMaster.getEncoder();
        rightSlaveE = rightSlave.getEncoder();

        ultra = new AnalogInput(0);
        

        camServo = new Servo(ServoConstants.CAM_SERVO_PORT);

        shifter = new DoubleSolenoid(Constants.PCM_PORT, PneumaticsModuleType.CTREPCM, DriveConstants.SHIFTER_FORWARD_CHANNEL, DriveConstants.SHIFTER_REVERSE_CHANNEL);

        resetSensors();
    }

    /**
     * <code>getInstance()</code> is a crucial part of the "singleton" design pattern. This pattern is used when there
     * should only be one instance of a class, which makes sense for Robot subsystems (after all, there is only one
     * drivetrain). The singleton pattern is implemented by making the constructor private, creating a static variable
     * of the type of the object called <code>instance</code>, and creating this method, <code>getInstance()</code>, to
     * return the instance when the class needs to be used.
     *
     * Usage:
     * <code>Drive drive = Drive.getInstance();</code>
     *
     * @return The single instance of {@link Drive}
     */
    public static Drive getInstance() {
        if (instance == null) {
            instance = new Drive();
        }

        return instance;
    }

    /**
     * Helper method that configures the Spark Max motor controllers.
     */
    private void configureSparks() {

        leftMaster.restoreFactoryDefaults();
        leftMaster.setInverted(false);

        leftSlave.restoreFactoryDefaults();
        leftSlave.follow(leftMaster);
        leftSlave.setInverted(false);

        rightMaster.restoreFactoryDefaults();
        rightMaster.setInverted(true);

        rightSlave.restoreFactoryDefaults();
        rightSlave.follow(rightMaster);
        rightSlave.setInverted(true);
    }

    /**
     * Sets the drive motors to a certain percent output (demand) using open loop control (no sensors in feedback loop).
     *
     * @param leftDemand The percent output for the left drive motors
     * @param rightDemand The percent output for the right drive motors
     */
    public void setOpenLoop(double leftDemand, double rightDemand) {
        leftMaster.set(leftDemand);
        rightMaster.set(rightDemand);
    }

    public double dist() {
        double distance = (5 * (ultra.getVoltage() / 4.883));
        SmartDashboard.putNumber("Sonic Dist", distance);
        return distance;
    }

    /**
     * Controls the drive motor using arcade controls - with a throttle and a turn.
     * 
     * @param throttle The throttle from the controller
     * @param turn The turn from the controller
     */
    public void arcadeDrive(double throttle, double turn) {
        driveHelper.arcadeDrive(throttle, turn);
    }

    public void accelDrive(double throttle, double turn) {
        driveHelper.accelDrive(throttle, turn);
    }

    public void inverseDrive(double throttle, double turn) {
        driveHelper.inverseDrive(throttle, turn);
    }

    public void camRotate() {
        camServo.setAngle(camServoRotation);
    }

    public void updateCamAngle(boolean increase) {
        if (increase) {
            camServoRotation = (camServoRotation == 180) ? camServoRotation : camServoRotation++;
        } else {
            camServoRotation = (camServoRotation == 0) ? camServoRotation : camServoRotation--;
        }
    }

    public void shift(boolean shift) {
        shifter.set(shift ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public double getRightRPM() {
        SmartDashboard.putNumber("Right RPM", rightMasterE.getVelocity());
        return rightMasterE.getVelocity();
    }

    public double rightCounts() {
        return rightMasterE.getPosition();
    }

    @Override
    public void resetSensors() {
        rightMasterE.setPosition(0);
        leftMasterE.setPosition(0);
        rightSlaveE.setPosition(0);
        leftSlaveE.setPosition(0);  
    }
}