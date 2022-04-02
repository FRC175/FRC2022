package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shuffleboard extends SubsystemBase {

    private static Shuffleboard instance;

    private Shooter shooter;
    private Limelight limelight;
    private Lift lift;

    private Shuffleboard() {
        shooter = Shooter.getInstance();
        limelight = Limelight.getInstance();
        lift = Lift.getInstance();
    }

    public static Shuffleboard getInstance() {
        if (instance == null) {
            instance = new Shuffleboard();
        }

        return instance;
    }

    public void logShooter() {
        SmartDashboard.putNumber("Shooter RPM", shooter.getShooterRPM());
        SmartDashboard.putNumber("Distance", limelight.distance());
    }

    public void logClimb() {
        // SmartDashboard.putNumber("Left Lift Offset", lift.getLeftEncoderOffset());
        // SmartDashboard.putNumber("Right Lift Offset", lift.getRightEncoderOffset());
        // SmartDashboard.putNumber("Left Abs Position", lift.getLeftEncoderCounts());
        // SmartDashboard.putNumber("Right Abs Position", lift.getRightEncoderCounts());
        SmartDashboard.putNumber("Left Position", lift.getLeftPosition());
        SmartDashboard.putNumber("Right Position", lift.getRightPosition());
    }

    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    } 
}
