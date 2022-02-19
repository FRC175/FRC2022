package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import frc.robot.Constants.ShooterConstants;


import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends SubsystemBase{
    private final CANSparkMax indexer;
    private final CANSparkMax shooterMaster;
    private final CANSparkMax shooterSlave;
    private final RelativeEncoder shooterMasterE;

    private static Shooter instance;


    private Shooter() {
        indexer = new CANSparkMax(ShooterConstants.INDEXER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterMaster = new CANSparkMax(ShooterConstants.SHOOT_MASTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterSlave = new CANSparkMax(ShooterConstants.SHOOT_SLAVE_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        configureSparks();

        shooterMasterE = shooterMaster.getEncoder();

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

        shooterMaster.restoreFactoryDefaults();
        shooterMaster.setInverted(true);

        shooterSlave.restoreFactoryDefaults();
        shooterSlave.follow(shooterMaster);
        shooterSlave.setInverted(false);
    }

    public void indexerSetOpenLoop(double demand) {
        indexer.set(demand);   
    }

    public void shooterSetOpenLoop(double demand) {
        SmartDashboard.putNumber("Demand", demand);
        shooterMaster.set(demand);
    }

    public double getShooterRPM() {
        SmartDashboard.putNumber("Shooter RPM", -shooterMasterE.getVelocity());
        return -shooterMasterE.getVelocity();
    }


    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    } 
}