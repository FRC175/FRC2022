package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Limelight represents the vision processing unit. It is composed of 1 Limelight camera.
 */
public final class Limelight extends SubsystemBase {

    private final NetworkTable table;

    private double rotation;
    private boolean isAtTarget;

    private static Limelight instance;

    private Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public static Limelight getInstance() {
        if (instance == null) {
            instance = new Limelight();
        }

        return instance;
    }

    private void setPipeline(int pipelineNum) {
        table.getEntry("pipeline").setNumber(pipelineNum);
    }

    public void setLED(boolean enable) {
        table.getEntry("ledMode").setNumber(enable ? 3 : 1);
    }

    public void turnOnLED() {
        setLED(true);
    }

    public void turnOffLED() {
        setLED(false);
    }

    public void blinkLED() {
        table.getEntry("ledMode").setNumber(2);
    }

    public void defaultLED() {
        table.getEntry("ledMode").setNumber(0);
    }

    public void setDriverMode() {
        setPipeline(1);
    }

    public void setTrackingMode() {
        // 1x Tracking
        setPipeline(0);
        // 2x Tracking
        // setPipeline(2);
    }

    public boolean isTargetDetected() {
        SmartDashboard.putBoolean("Detected?", table.getEntry("tv").getDouble(0) == 1);
        return table.getEntry("tv").getDouble(0) == 1;
    }

    public double getHorizontalOffset() {
        return table.getEntry("tx").getDouble(0);
    }

    public double getVerticalOffset() {
        return table.getEntry("ty").getDouble(0);
    }

    public double getTargetArea() {
        return table.getEntry("ta").getDouble(0);
    }

    public double getSkew() {
        return table.getEntry("ts").getDouble(0);
    }

    public double getLatency() {
        // ~ 15 ms latency
        return table.getEntry("tl").getDouble(0) + 11; // Add 11 ms for image capture latency
    }

    public double getRotation() {
        return rotation;
    }

    public boolean isAtTarget() {
        return isAtTarget;
    }

    public double distance() {
        //height from ground camera
        double h1 = 18;
        //height from ground tape
        double h2 = 102;
        //Mounting angle
        double a1 = 40;
        //Angle from camera to tape
        double a2 = getVerticalOffset();
        // distance equation results in inches
        double d = (h2 - h1) / Math.tan((a1 + a2) * (3.14159 / 180.0));
        SmartDashboard.putNumber("Distance to Tape", d);
        return d;
    }

    public double calculateRPM(double distance, String hubToScore) {
        // Distance Constants
        double d1 = 123;
        double d2 = 113;
        double d3 = 103;
        double d4 = 93;
        // CHANGE THESE VALUES
        double RPMupper1 = 3600;
        double RPMupper2 = 3600;
        double RPMupper3 = 3500;
        double RPMupper4 = 3700;
        double RPMlower1 = 2000;
        double RPMlower2 = 3000;
        double RPMlower3 = 3200;
        double RPMlower4 = 3400;

        double estimateRPM;

        if (hubToScore.toLowerCase() == "upper") {
            estimateRPM = RPMupper1 * (distance - d2) / (d1 - d2) * (distance - d3) / (d1 - d3) * (distance - d4) / (d1 - d4) +
                RPMupper2 * (distance - d1) / (d2 - d1) * (distance - d3) / (d2 - d3) * (distance - d4) / (d2 - d4) +
                RPMupper3 * (distance - d1) / (d3 - d1) * (distance - d2) / (d3 - d2) * (distance - d4) / (d3 - d4) +
                RPMupper4 * (distance - d1) / (d4 - d1) * (distance - d2) / (d4 - d2) * (distance - d3) / (d4 - d3);
        } else if (hubToScore.toLowerCase() == "lower") {
            estimateRPM = RPMlower1 * (distance - d2) / (d1 - d2) * (distance - d3) / (d1 - d3) * (distance - d4) / (d1 - d4) +
                RPMlower2 * (distance - d1) / (d2 - d1) * (distance - d3) / (d2 - d3) * (distance - d4) / (d2 - d4) +
                RPMlower3 * (distance - d1) / (d3 - d1) * (distance - d2) / (d3 - d2) * (distance - d4) / (d3 - d4) +
                RPMlower4 * (distance - d1) / (d4 - d1) * (distance - d2) / (d4 - d2) * (distance - d3) / (d4 - d3);
        } else {
            estimateRPM = 2000;
        }

        return estimateRPM;
    }


    @Override
    public void resetSensors() {
        setPipeline(0);
        turnOffLED();
        setDriverMode();
    }
}
