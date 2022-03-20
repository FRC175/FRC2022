package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;

public final class Intake extends SubsystemBase {

    private final CANSparkMax intakeMotor;

    private final DoubleSolenoid deployer;

    private static Intake instance;

    private Intake() {
        intakeMotor =  new CANSparkMax(Constants.IntakeConstants.INTAKE_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeMotor.setInverted(true);
        intakeMotor.restoreFactoryDefaults();

        deployer = new DoubleSolenoid(Constants.PCM_PORT, PneumaticsModuleType.CTREPCM, IntakeConstants.INTAKE_ARM_FORWARD_CHANNEL, IntakeConstants.INTAKE_ARM_REVERSE_CHANNEL);
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;
    }


    public void setIntakeOpenLoop(double demand) {
      intakeMotor.set(demand);
    }

    public void deploy(boolean deploy) {
      deployer.set(deploy ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void resetSensors() {
        
    }
}
