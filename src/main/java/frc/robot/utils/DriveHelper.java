package frc.robot.utils;

import com.revrobotics.CANSparkMax;

public final class DriveHelper {

    // Spark Maxes
    private final CANSparkMax left, right;

    /**
     * Constructs a new DriveHelper.
     *
     * @param left  The master Spark Max for the left drive motors
     * @param right The master Spark Max for the right drive motors
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

    public void inverseDrive(double throttle, double turn) {
        arcadeDrive(-throttle, -turn);
    }

    public void accelDrive(double throttle, double turn) {
            double leftV = left.getAppliedOutput();
            double rightV = right.getAppliedOutput();
            double div = 20;
            double accelerationL = (throttle - leftV) / div;
            double accelerationR = (throttle - leftV) / div;
            double leftOut;
            double rightOut;
            if (turn != 0) {
                // velocity + acceleration will only ever total to 1. ± turn maxes from -2 to 2.
                leftOut = leftV + accelerationL + turn;
                rightOut = rightV + accelerationR - turn;
            } else {
                //When turning ends, the weaker motor becomes equivilant to the stronger one.
                leftV = (leftV < rightV) ? rightV : leftV;
                rightV = (rightV < leftV) ? leftV : rightV;
                leftOut = leftV + accelerationL;
                rightOut = rightV + accelerationR;
            }
            left.set(leftOut);
            right.set(rightOut);
    }
}