package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import frc.robot.Constants.ShooterConstants;


import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends SubsystemBase {
    private final CANSparkMax indexer;
    private final CANSparkMax shooterWheel;
    private final CANSparkMax shooterWheelSlave;
    private final RelativeEncoder shooterWheelEncoder, shooterWheelSlaveEncoder;

    private static Shooter instance;

    private double output;

    private Shooter() {
        indexer = new CANSparkMax(ShooterConstants.SHOOTER_INDEXER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterWheel = new CANSparkMax(ShooterConstants.SHOOTER_WHEEL_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterWheelSlave = new CANSparkMax(ShooterConstants.SHOOTER_WHEEL_SLAVE_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        configureSparks();

        shooterWheelEncoder = shooterWheel.getEncoder();
        shooterWheelSlaveEncoder = shooterWheelSlave.getEncoder();

        output = 0;
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
        shooterWheel.setInverted(false);

        shooterWheelSlave.restoreFactoryDefaults();
        shooterWheelSlave.setInverted(true);
    }

    public void indexerSetOpenLoop(double demand) {
        indexer.set(-demand);   
    }

    public void shooterSetOpenLoop(double demand) {
        SmartDashboard.putNumber("Demand", demand);
        shooterWheel.set(demand);
        shooterWheelSlave.set(demand);
    }

    public double getShooterRPM() {
        return shooterWheelEncoder.getVelocity();
    }

    public double getShooterSlaveRPM() {
        return shooterWheelSlaveEncoder.getVelocity();
    }

    public double getAverageShooterRPM() {
        return (getShooterRPM() + getShooterSlaveRPM()) / 2;
    }

    public double getOutput() {
        return output;
    }
    
    public void updateOutput(double amount) {
        output += amount;
    }

    public void turnOffShooter() {
        shooterSetOpenLoop(0);
        indexerSetOpenLoop(0);
    }

    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    } 
}
