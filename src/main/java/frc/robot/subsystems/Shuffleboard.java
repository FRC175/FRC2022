package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shuffleboard extends SubsystemBase {

    private static Shuffleboard instance;

    private Shooter shooter;
    private Limelight limelight;
    private Lift lift;
    private Intake intake;

    private Shuffleboard() {
        shooter = Shooter.getInstance();
        limelight = Limelight.getInstance();
        lift = Lift.getInstance();
        intake = Intake.getInstance();
    }

    public static Shuffleboard getInstance() {
        if (instance == null) {
            instance = new Shuffleboard();
        }

        return instance;
    }

    public void logShooter() {
        SmartDashboard.putNumber("Average Shooter RPM", shooter.getAverageShooterRPM());
        SmartDashboard.putNumber("Shooter RPM", shooter.getShooterRPM());
        SmartDashboard.putNumber("Shooter Slave RPM", shooter.getShooterSlaveRPM());
        SmartDashboard.putNumber("Distance", limelight.distance());
        SmartDashboard.putNumber("Desired RPM", limelight.getFinalRPM());
        SmartDashboard.putNumber("Current Offset", limelight.getCurrentOffset());
    }

    public void logClimb() {
        SmartDashboard.putNumber("Left Position", lift.getLeftPosition());
        SmartDashboard.putNumber("Right Position", lift.getRightPosition());
    }

    public void logIntake() {
        SmartDashboard.putNumber("Intake RPM", Math.abs(intake.getIntakeMotorRPM()));
    }

    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    } 
}
