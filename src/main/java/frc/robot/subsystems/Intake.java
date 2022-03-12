package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
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
