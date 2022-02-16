package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public final class DriveHelper {

    // Spark Maxes
    private final TalonSRX left, right;

    /**
     * Constructs a new DriveHelper.
     *
     * @param left  The master Spark Max for the left drive motors
     * @param right The master Spark Max for the right drive motors
     */
    public DriveHelper(TalonSRX left, TalonSRX right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Arcade drive using arbitrary feed forward.
     */
    public void arcadeDrive(double throttle, double turn) {
        double leftOut = throttle + turn;
        double rightOut = throttle - turn;
        left.set(ControlMode.PercentOutput, leftOut);
        right.set(ControlMode.PercentOutput, rightOut);
    }

    public void inverseDrive(double throttle, double turn) {
        arcadeDrive(-throttle, -turn);
    }
}