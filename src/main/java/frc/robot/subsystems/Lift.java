package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;

import frc.robot.Constants.LiftConstants;

public final class Lift extends SubsystemBase {

    private final TalonSRX leftPrimary, rightPrimary;

    private final DutyCycleEncoder leftEncoder, rightEncoder;

    private static Lift instance;

    private Lift() {
        leftPrimary = new TalonSRX(LiftConstants.LEFT_PRIMARY_LIFT);
        rightPrimary = new TalonSRX(LiftConstants.RIGHT_PRIMARY_LIFT);
        // centralPrimary = new TalonSRX(LiftConstants.CENTRAL_LIFT);

        // leftEncoder = new CANCoder(0);
        // rightEncoder = new CANCoder(1);

        leftEncoder = new DutyCycleEncoder(0);
        rightEncoder = new DutyCycleEncoder(1);
        // centralEncoder = new CANCoder(LiftConstants.CENTRAL_LIFT);
        // leftEncoder.setDutyCycleRange(0, 100);
        // rightEncoder.setDutyCycleRange(0, 1);

        resetEncoders();

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

    private double maxCounts = 30;

    public void setLiftOpenLoop(double demand) {
        // if (Math.abs(leftEncoder.get()) <= 5.7) {
        //     leftPrimary.set(ControlMode.PercentOutput, demand);
        // } else {
        //     leftPrimary.set(ControlMode.PercentOutput, 0);
        // }
        
        // if (rightEncoder.get() <= 5.7) {
        //     rightPrimary.set(ControlMode.PercentOutput, demand);
        // } else {
        //     rightPrimary.set(ControlMode.PercentOutput, 0);
        // }
        // if (demand < 0) {
        //     if (leftEncoder.getPosition() <= maxCounts) {
        //         leftPrimary.set(ControlMode.PercentOutput, 0);
        //     } else {
        //         leftPrimary.set(ControlMode.PercentOutput, demand);
        //     }
        //     if (rightEncoder.getPosition() <= maxCounts) {
        //         rightPrimary.set(ControlMode.PercentOutput, 0);
        //     } else {
        //         rightPrimary.set(ControlMode.PercentOutput, demand);
        //     }
        // } else if (demand > 0) {
        //     if (leftEncoder.getPosition() >= maxCounts) {
        //         leftPrimary.set(ControlMode.PercentOutput, 0);
        //     } else {
        //         leftPrimary.set(ControlMode.PercentOutput, demand);
        //     }
        //     if (rightEncoder.getPosition() >= maxCounts) {
        //         rightPrimary.set(ControlMode.PercentOutput, 0);
        //     } else {
        //         rightPrimary.set(ControlMode.PercentOutput, demand);
        //     }
        // } else {
        //     leftPrimary.set(ControlMode.PercentOutput, 0);
        // }

        if (getLeftPosition() > 0 && getLeftPosition() < 3) {
            System.out.print(demand);
        } else if (getLeftPosition() < 0) {
            if (demand <= 0) {
                System.out.println("0");
            } else {
                System.out.println("1");
            }
        } else if (getLeftPosition() > 3) {
            if (demand >= 0) {
                System.out.println("0");
        } else {
                System.out.println("-1");
            }
        }
        System.out.println("working");
        // leftPrimary.set(ControlMode.PercentOutput, demand);
        // System.out.println(demand);
        // rightPrimary.set(ControlMode.PercentOutput, demand);
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

    // public double getLeftEncoderCounts() {
    //     return leftEncoder.getAbsolutePosition();
    // }

    // public double getRightEncoderCounts() {
    //     return rightEncoder.getAbsolutePosition();
    // }

    // public double getLeftEncoderOffset() {
    //     return leftEncoder.getPositionOffset();
    // }

    // public double getRightEncoderOffset() {
    //     return rightEncoder.getPositionOffset();
    // }

    public double getRightPosition() {
        return rightEncoder.getDistance();
    }
    public double getLeftPosition() {
        return leftEncoder.getDistance();
    }

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
        // centralEncoder.setPosition(0);
    }

}
