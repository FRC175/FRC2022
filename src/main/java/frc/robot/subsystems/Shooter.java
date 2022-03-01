package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import frc.robot.Constants.ShooterConstants;


import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends SubsystemBase {
    private final CANSparkMax indexer;
    private final CANSparkMax shooterWheel;
    private final RelativeEncoder shooterWheelEncoder;

    private static Shooter instance;


    private Shooter() {
        indexer = new CANSparkMax(ShooterConstants.SHOOTER_INDEXER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterWheel = new CANSparkMax(ShooterConstants.SHOOTER_WHEEL_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        configureSparks();

        shooterWheelEncoder = shooterWheel.getEncoder();
    }

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }

        return instance;
    }

    private void configureSparks() {
        indexer.restoreFactoryDefaults();
        indexer.setInverted(false);

        shooterWheel.restoreFactoryDefaults();
        shooterWheel.setInverted(true);
    }

    public void indexerSetOpenLoop(double demand) {
        indexer.set(-demand);   
    }

    public void shooterSetOpenLoop(double demand) {
        SmartDashboard.putNumber("Demand", demand);
        shooterWheel.set(demand);
    }

    public double getShooterRPM() {
        SmartDashboard.putNumber("Shooter RPM", -shooterWheelEncoder.getVelocity());
        return -shooterWheelEncoder.getVelocity();
    }


    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    } 
}
