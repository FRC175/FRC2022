// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final int PCM_PORT = 18;
    public static final class ControllerConstants {
        public static final int DRIVER_CONTROLLER_PORT = 0;
        public static final int OPERATOR_CONTROLLER_PORT = 1;

        public static final double CONTROLLER_DEADBAND = 0.1;
    }

    public static final class DriveConstants {
        public static final int RIGHT_MASTER_PORT = 2;
        public static final int RIGHT_SLAVE_PORT = 5;
        public static final int LEFT_MASTER_PORT = 17;
        public static final int LEFT_SLAVE_PORT = 14;

        public static final int SHIFTER_FORWARD_CHANNEL = 0;
        public static final int SHIFTER_REVERSE_CHANNEL = 1;
    }

    public static final class IntakeConstants {
        public static final int INTAKE_PORT = 11;

        public static final int INTAKE_ARM_FORWARD_CHANNEL = 2;
        public static final int INTAKE_ARM_REVERSE_CHANNEL = 3;
    }

    public static final class LiftConstants {
        public static final int RIGHT_PRIMARY_LIFT = 6;
        public static final int LEFT_PRIMARY_LIFT = 13;
        public static final int CENTRAL_LIFT = 19;

        public static final int LIFT_FORWARD_CHANNEL = 4;
        public static final int LIFT_REVERSE_CHANNEL = 5;
    }

    public static final class ShooterConstants {
        public static final int SHOOTER_INDEXER_PORT = 8;
        public static final int SHOOTER_WHEEL_PORT = 16;
        public static final int SHOOTER_WHEEL_SLAVE_PORT = 15;
    }

    public static final class LEDConstants {
        public static final int LED_PORT = 0;
    }
}
