package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants.LiftConstants;

public final class Lift extends SubsystemBase {

    private final TalonSRX leftPrimary, rightPrimary;

    private final DutyCycleEncoder leftEncoder, rightEncoder;

    private static Lift instance;

    private Lift() {
        leftPrimary = new TalonSRX(LiftConstants.LEFT_PRIMARY_LIFT);
        rightPrimary = new TalonSRX(LiftConstants.RIGHT_PRIMARY_LIFT);
        // centralPrimary = new TalonSRX(LiftConstants.CENTRAL_LIFT);

        leftEncoder = new DutyCycleEncoder(2);
        rightEncoder = new DutyCycleEncoder(1);
        // centralEncoder = new CANCoder(LiftConstants.CENTRAL_LIFT);

        configureTalons();
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

        leftPrimary.setInverted(true);
        rightPrimary.setInverted(true);
    }

    public void setLiftOpenLoop(double demand) {
        if (Math.abs(leftEncoder.get()) <= 5.7) {
            leftPrimary.set(ControlMode.PercentOutput, demand);
        } else {
            leftPrimary.set(ControlMode.PercentOutput, 0);
        }
        
        if (rightEncoder.get() <= 5.7) {
            rightPrimary.set(ControlMode.PercentOutput, demand);
        } else {
            rightPrimary.set(ControlMode.PercentOutput, 0);
        }

        SmartDashboard.putNumber("Left Encoder", getLeftEncoderCounts().doubleValue());
        SmartDashboard.putNumber("Right Encoder", getRightEncoderCounts().doubleValue());
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

    public Number getLeftEncoderCounts() {
        return leftEncoder.get();
    }

    public Number getRightEncoderCounts() {
        return rightEncoder.get();
    }

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
        // centralEncoder.setPosition(0);
    }

}
