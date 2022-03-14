package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import com.ctre.phoenix.sensors.CANCoder;

import frc.robot.Constants.LiftConstants;
import frc.robot.Constants;

public final class Lift extends SubsystemBase {

    private final TalonSRX leftPrimary, rightPrimary, centralPrimary;

    private final CANCoder leftEncoder, rightEncoder, centralEncoder;

    private static Lift instance;

    private final DoubleSolenoid liftExtend;

    private Lift() {
        leftPrimary = new TalonSRX(LiftConstants.LEFT_PRIMARY_LIFT);
        rightPrimary = new TalonSRX(LiftConstants.RIGHT_PRIMARY_LIFT);
        centralPrimary = new TalonSRX(LiftConstants.CENTRAL_LIFT);

        leftEncoder = new CANCoder(LiftConstants.LEFT_PRIMARY_LIFT);
        rightEncoder = new CANCoder(LiftConstants.RIGHT_PRIMARY_LIFT);
        centralEncoder = new CANCoder(LiftConstants.CENTRAL_LIFT);

        liftExtend = new DoubleSolenoid(Constants.PCM_PORT, PneumaticsModuleType.CTREPCM, LiftConstants.LIFT_FORWARD_CHANNEL, LiftConstants.LIFT_REVERSE_CHANNEL);

        configureTalons();

        resetSensors();
    }

    public static Lift getInstance() {
        if (instance == null) {
            instance = new Lift();
        }

        return instance;
    }

    private void configureTalons() {
        leftPrimary.configFactoryDefault();
        rightPrimary.configFactoryDefault();
        centralPrimary.configFactoryDefault();

        leftEncoder.configFactoryDefault();
        rightEncoder.configFactoryDefault();
        centralEncoder.configFactoryDefault();
    }

    double maxCounts = 20.75;
    public void setLiftOpenLoop(double leftDemand, double rightDemand) {
        if (leftEncoder.getPosition() > maxCounts) {
            leftPrimary.set(ControlMode.PercentOutput, -1);
            rightPrimary.set(ControlMode.PercentOutput, -1);
        } else if (leftEncoder.getPosition() < 0) {
            leftPrimary.set(ControlMode.PercentOutput, 1);
            rightPrimary.set(ControlMode.PercentOutput, 1);
        } else {
            leftPrimary.set(ControlMode.PercentOutput, leftDemand);
            rightPrimary.set(ControlMode.PercentOutput, rightDemand);
        }
    }

    public void setCentralOpenLoop(double centralDemand) {
        if (centralEncoder.getPosition() > maxCounts) {
            centralPrimary.set(ControlMode.PercentOutput, -1);
        } else if (leftEncoder.getPosition() < 0) {
            centralPrimary.set(ControlMode.PercentOutput, 1);
        } else {
            centralPrimary.set(ControlMode.PercentOutput, centralDemand);
        }
    }

    public void extend(boolean extend) {
        liftExtend.set(extend ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public void resetSensors() {
        rightEncoder.setPosition(0);
        leftEncoder.setPosition(0);
        centralEncoder.setPosition(0);
    }

}
