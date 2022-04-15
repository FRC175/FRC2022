package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.utils.InterpolatingDouble;
import frc.robot.utils.InterpolatingTreeMap;

/**
 * Limelight represents the vision processing unit. It is composed of 1 Limelight camera.
 */
public final class Limelight extends SubsystemBase {

    private final NetworkTable table;

    private double rotation;
    private boolean isAtTarget;
    private double offset;
    private boolean ledsOn;

    private static Limelight instance;

    private InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> mappedInterpolationPoints;

    private Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        offset = 0;
        ledsOn = true;

        configureInterpolationTable();
    }

    public static Limelight getInstance() {
        if (instance == null) {
            instance = new Limelight();
        }

        return instance;
    }

    private void configureInterpolationTable() {
        mappedInterpolationPoints = new InterpolatingTreeMap<>();

        mappedInterpolationPoints.put(new InterpolatingDouble(76.0), new InterpolatingDouble(3300.0));
        mappedInterpolationPoints.put(new InterpolatingDouble(93.0), new InterpolatingDouble(3425.0));
        mappedInterpolationPoints.put(new InterpolatingDouble(105.0), new InterpolatingDouble(3500.0));
        mappedInterpolationPoints.put(new InterpolatingDouble(113.0), new InterpolatingDouble(3500.0));
        mappedInterpolationPoints.put(new InterpolatingDouble(141.0), new InterpolatingDouble(3800.0));
        mappedInterpolationPoints.put(new InterpolatingDouble(152.0), new InterpolatingDouble(3960.0));
    }

    private void setPipeline(int pipelineNum) {
        table.getEntry("pipeline").setNumber(pipelineNum);
    }

    public void setLED(boolean enable) {
        ledsOn = enable;
        table.getEntry("ledMode").setNumber(enable ? 3 : 1);
    }

    public void turnOnLED() {
        setLED(true);
    }

    public void turnOffLED() {
        setLED(false);
    }

    public boolean areLEDsOn() {
        return ledsOn;
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
        //og 18
        double h1 = 30;
        //height from ground tape
        double h2 = 102;
        //Mounting angle
        //og 40
        double a1 = 34;
        //Angle from camera to tape
        double a2 = getVerticalOffset();
        // distance equation results in inches
        double d = (h2 - h1) / Math.tan((a1 + a2) * (3.14159 / 180.0));
        SmartDashboard.putNumber("Distance to Tape", d);
        return d;
    }

    public double calculateRPM(double distance) {
        return mappedInterpolationPoints.getInterpolated(new InterpolatingDouble(distance)).value + getCurrentOffset();
    }

    public double getFinalRPM() {
        return calculateRPM(distance());
    }

    public double getCurrentOffset() {
        return offset;
    }

    public void updateOffset(double amount) {
        offset += amount;
    }


    @Override
    public void resetSensors() {
        setPipeline(0);
        turnOffLED();
        setDriverMode();
    }
}
