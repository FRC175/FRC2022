package frc.robot.utils;

import com.revrobotics.CANSparkMax;

public final class DriveHelper {

    // Talon SRXs
    private final CANSparkMax left, right;

    /**
     * Constructs a new DriveHelper.
     *
     * @param left  The master Talon SRX for the left drive motors
     * @param right The master Talon SRX for the right drive motors
     */
    public DriveHelper(CANSparkMax left, CANSparkMax right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Arcade drive using arbitrary feed forward.
     */
    public void arcadeDrive(double throttle, double turn) {
        double leftOut = throttle + turn;
        double rightOut = throttle - turn;
        left.set(leftOut);
        right.set(rightOut);
    }
}