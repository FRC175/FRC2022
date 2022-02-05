package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import frc.robot.Constants.ShootConstants;


import com.revrobotics.CANSparkMaxLowLevel;

public class Shooter extends SubsystemBase{
    private final CANSparkMax indexer;
    private final CANSparkMax shooterMaster;
    private final CANSparkMax shooterSlave;

    private static Shooter instance;


    private Shooter() {
        indexer = new CANSparkMax(ShootConstants.INDEXER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterMaster = new CANSparkMax(ShootConstants.SHOOT_MASTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterSlave = new CANSparkMax(ShootConstants.SHOOT_SLAVE_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        configureSparks();

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
        shooterMaster.setInverted(false);

        shooterSlave.restoreFactoryDefaults();
        shooterSlave.follow(shooterMaster);
        shooterSlave.setInverted(true);
    }

    public void indexerSetOpenLoop(double demand) {
        indexer.set(demand);   
    }

    public void shooterSetOpenLoop(double demand) {
        shooterMaster.set(demand);
    }


    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    } 
}
