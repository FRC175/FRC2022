package frc.robot.subsystems;

import frc.robot.Constants.DriveConstants;
import frc.robot.utils.DriveHelper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Drive represents the drivetrain. It is composed of 4 CIM motors (all controlled with Talon SRXs), a Pigeon gyro, and
 * two pneumatic pistons. This class is packed with documentation to better understand design choices and robot
 * programming in general.
 */
public final class Drive extends SubsystemBase {

    // These variables are final because they only need to be instantiated once (after all, you don't need to create a
    // new left master TalonSRX).
    private final TalonSRX leftMaster, leftSlave, rightMaster, rightSlave;
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
        leftMaster = new TalonSRX(DriveConstants.LEFT_MASTER_PORT);
        leftSlave = new TalonSRX(DriveConstants.LEFT_SLAVE_PORT);
        rightMaster = new TalonSRX(DriveConstants.RIGHT_MASTER_PORT);
        rightSlave = new TalonSRX(DriveConstants.RIGHT_SLAVE_PORT);
        driveHelper = new DriveHelper(leftMaster, rightMaster);
        configureTalons();
    }

    /**
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
    private void configureTalons() {

        leftMaster.configFactoryDefault();
        leftMaster.setInverted(false);

        leftSlave.configFactoryDefault();
        leftSlave.follow(leftMaster);
        leftSlave.setInverted(false);

        rightMaster.configFactoryDefault();
        rightMaster.setInverted(true);

        rightSlave.configFactoryDefault();
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
        leftMaster.set(ControlMode.PercentOutput, leftDemand);
        rightMaster.set(ControlMode.PercentOutput, rightDemand);
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

    public void inverseDrive(double throttle, double turn) {
        driveHelper.inverseDrive(throttle, turn);
    }

    @Override
    public void resetSensors() {
        
    }
}