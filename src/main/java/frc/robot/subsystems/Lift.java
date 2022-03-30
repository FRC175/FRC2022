package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import com.ctre.phoenix.sensors.CANCoder;

import frc.robot.Constants.LiftConstants;

public final class Lift extends SubsystemBase {

    private final TalonSRX leftPrimary, rightPrimary;

    private final CANCoder leftEncoder, rightEncoder;

    private static Lift instance;

    private Lift() {
        leftPrimary = new TalonSRX(LiftConstants.LEFT_PRIMARY_LIFT);
        rightPrimary = new TalonSRX(LiftConstants.RIGHT_PRIMARY_LIFT);
        // centralPrimary = new TalonSRX(LiftConstants.CENTRAL_LIFT);

        leftEncoder = new CANCoder(LiftConstants.LEFT_PRIMARY_LIFT);
        rightEncoder = new CANCoder(LiftConstants.RIGHT_PRIMARY_LIFT);
        // centralEncoder = new CANCoder(LiftConstants.CENTRAL_LIFT);

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
        // centralPrimary.configFactoryDefault();

        rightPrimary.follow(leftPrimary);

        leftPrimary.setInverted(true);
        rightPrimary.setInverted(true);

        leftEncoder.configFactoryDefault();
        rightEncoder.configFactoryDefault();
        // centralEncoder.configFactoryDefault();
    }

    // double maxCounts = 20.75;-+
    public void setLiftOpenLoop(double demand) {
        // if (leftEncoder.getPosition() > maxCounts) {
        //     leftPrimary.set(ControlMode.PercentOutput, -0.5);
        //     rightPrimary.set(ControlMode.PercentOutput, -0.5);
        // } else if (leftEncoder.getPosition() < 0) {
        //     leftPrimary.set(ControlMode.PercentOutput, 0.5);
        //     rightPrimary.set(ControlMode.PercentOutput, 0.5);
        // } else {
            leftPrimary.set(ControlMode.PercentOutput, demand);
        // }
    }

    // public void setCentralOpenLoop(double centralDemand) {
    //     if (centralEncoder.getPosition() > maxCounts) {
    //         centralPrimary.set(ControlMode.PercentOutput, -1);
    //     } else if (leftEncoder.getPosition() < 0) {
    //         centralPrimary.set(ControlMode.PercentOutput, 1);
    //     } else {
    //         centralPrimary.set(ControlMode.PercentOutput, centralDemand);
    //     }
    // }

    public void resetSensors() {
        rightEncoder.setPosition(0);
        leftEncoder.setPosition(0);
        // centralEncoder.setPosition(0);
    }

}
