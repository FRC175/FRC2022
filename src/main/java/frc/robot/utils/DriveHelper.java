package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public final class DriveHelper {

    // Talon SRXs
    private final TalonSRX left, right;

    /**
     * Constructs a new DriveHelper.
     *
     * @param left  The master Talon SRX for the left drive motors
     * @param right The master Talon SRX for the right drive motors
     */
    public DriveHelper(TalonSRX left, TalonSRX right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Arcade drive using arbitrary feed forward.
     */
    public void arcadeDrive(double throttle, double turn) {
        left.set(ControlMode.PercentOutput, throttle, DemandType.ArbitraryFeedForward, +turn);
        right.set(ControlMode.PercentOutput, throttle, DemandType.ArbitraryFeedForward, -turn);
    }
}