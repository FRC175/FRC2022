package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import frc.robot.Constants.LiftConstants;
import frc.robot.Constants.SolenoidConstants;

public final class Lift extends SubsystemBase {

    private final VictorSPX leftPrimary, rightPrimary;

    private static Lift instance;

    private final DoubleSolenoid liftExtend;

    private Lift() {
        leftPrimary = new VictorSPX(LiftConstants.LEFT_PRIMARY_LIFT);
        rightPrimary = new VictorSPX(LiftConstants.RIGHT_PRIMARY_LIFT);

        liftExtend = new DoubleSolenoid(SolenoidConstants.PCM_PORT, PneumaticsModuleType.CTREPCM, SolenoidConstants.FORWARD_CHANNEL, SolenoidConstants.REVERSE_CHANNEL);

        configureVictors();
    }

    public static Lift getInstance() {
        if (instance == null) {
            instance = new Lift();
        }

        return instance;
    }

    private void configureVictors() {
        leftPrimary.configFactoryDefault();
        rightPrimary.configFactoryDefault();
    }

    public void setLiftOpenLoop(double leftDemand, double rightDemand) {
        leftPrimary.set(ControlMode.PercentOutput, leftDemand);
        rightPrimary.set(ControlMode.PercentOutput, rightDemand);
    }

    public void extend(boolean extend) {
        liftExtend.set(extend ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

}
