package frc.robot.subsystems;

import frc.robot.Constants.DriveConstants;
import frc.robot.utils.DriveHelper;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax;

/**
 * Drive represents the drivetrain. It is composed of 4 CIM motors (all controlled with Talon SRXs), a Pigeon gyro, and
 * two pneumatic pistons. This class is packed with documentation to better understand design choices and robot
 * programming in general.
 */
public final class Drive extends SubsystemBase {

    // These variables are final because they only need to be instantiated once (after all, you don't need to create a
    // new left master TalonSRX).
    private final CANSparkMax leftMaster, leftSlave, rightMaster, rightSlave;
    private final DriveHelper driveHelper;

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
        configureMotors();
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
    private void configureMotors() {

        //IDK WHat the config lines do Parker. But all i know is theyre not in Spark Max.

        boolean leftInverted = true;
        boolean rightInverted = false;
        // leftMaster.configFactoryDefault();
        leftMaster.setInverted(leftInverted);

        // leftSlave.configFactoryDefault();
        leftSlave.follow(leftMaster);
        leftSlave.setInverted(leftInverted);

        // rightMaster.configFactoryDefault();
        rightMaster.setInverted(rightInverted);

        // rightSlave.configFactoryDefault();
        rightSlave.follow(rightMaster);
        rightSlave.setInverted(rightInverted);
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

    /**
     * Controls the drive motor using arcade controls - with a throttle and a turn.
     *
     * @param throttle The throttle from the controller
     * @param turn The turn from the controller
     */
    public void arcadeDrive(double throttle, double turn) {
        //Parker what the frick i had to re write arcade drive for Spark Max
        driveHelper.arcadeDrive(throttle, turn);
    }

    @Override
    public void resetSensors() {
        
    }
}