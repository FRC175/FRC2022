package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shuffleboard extends SubsystemBase {

    private static Shuffleboard instance;

    private Shooter shooter;

    private Shuffleboard() {
        shooter = Shooter.getInstance();
    }

    public static Shuffleboard getInstance() {
        if (instance == null) {
            instance = new Shuffleboard();
        }

        return instance;
    }

    public void logShooter() {
        SmartDashboard.putNumber("Shooter RPM", shooter.getShooterRPM());
    }

    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    } 
}
