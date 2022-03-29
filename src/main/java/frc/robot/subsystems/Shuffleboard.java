package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shuffleboard extends SubsystemBase {

    private static Shuffleboard instance;

    private Shooter shooter;
    private Limelight limelight;

    private Shuffleboard() {
        shooter = Shooter.getInstance();
        limelight = Limelight.getInstance();
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

    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    } 
}
