package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import frc.robot.Constants.ShootConstants;
import frc.robot.commands.Shoot;

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
        indexer = new CANSparkMax(ShootConstants.INDEXER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterMaster = new CANSparkMax(ShootConstants.SHOOT_MASTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterSlave = new CANSparkMax(ShootConstants.SHOOT_SLAVE_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
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

    public enum ShootType {
        HIGH, LOW, WEAK;
    }

    public double estimatePower(double distance, String type) {
        double output;
        double d1, d2, d3, d4;
        double RPMupper1, RPMupper2, RPMupper3, RPMupper4;
        double RPMlower1, RPMlower2, RPMlower3, RPMlower4;

        switch (type) {
            case "high":
                // output = RPMupper1 * (distance - d2) / (d1 - d2) * (distance - d3) / (d1 - d3) * (distance - d4) / (d1 - d4) + 
                // RPMupper2 * (distance - d1) / (d2 - d1) * (distance - d3) / (d2 - d3) * (distance - d4) / (d2 - d4) + 
                // RPMupper3 * (distance - d1) / (d3 - d1) * (distance - d2) / (d3 - d2) * (distance - d4) / (d3 - d4) + 
                // RPMupper4 * (distance - d1) / (d4 - d1) * (distance - d2) / (d4 - d2) * (distance - d3) / (d4 - d3);
            case "low":
                // output = RPMlower1 * (distance - d2) / (d1 - d2) * (distance - d3) / (d1 - d3) * (distance - d4) / (d1 - d4) + 
                // RPMlower2 * (distance - d1) / (d2 - d1) * (distance - d3) / (d2 - d3) * (distance - d4) / (d2 - d4) + 
                // RPMlower3 * (distance - d1) / (d3 - d1) * (distance - d2) / (d3 - d2) * (distance - d4) / (d3 - d4) + 
                // RPMlower4 * (distance - d1) / (d4 - d1) * (distance - d2) / (d4 - d2) * (distance - d3) / (d4 - d3);
            default:
                output = 1500;
        }
        return output / 6000;
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
