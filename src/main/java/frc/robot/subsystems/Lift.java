package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import frc.robot.Constants.LiftConstants;
import frc.robot.Constants;

public final class Lift extends SubsystemBase {

    private final TalonSRX leftPrimary, rightPrimary, centralPrimary;

    private static Lift instance;

    private final DoubleSolenoid liftExtend;

    private Lift() {
        leftPrimary = new TalonSRX(LiftConstants.LEFT_PRIMARY_LIFT);
        rightPrimary = new TalonSRX(LiftConstants.RIGHT_PRIMARY_LIFT);
        centralPrimary = new TalonSRX(LiftConstants.CENTRAL_LIFT);

        liftExtend = new DoubleSolenoid(Constants.PCM_PORT, PneumaticsModuleType.CTREPCM, LiftConstants.LIFT_FORWARD_CHANNEL, LiftConstants.LIFT_REVERSE_CHANNEL);

        configureTalons();
    }

    public static Lift getInstance() {
        if (instance == null) {
            instance = new Lift();
        }

        return instance;
    }

    private void configureTalons() {
        leftPrimary.configFactoryDefault();
        rightPrimary.configFactoryDefault();
        centralPrimary.configFactoryDefault();
    }

    public void setLiftOpenLoop(double leftDemand, double rightDemand) {
        leftPrimary.set(ControlMode.PercentOutput, leftDemand);
        rightPrimary.set(ControlMode.PercentOutput, rightDemand);
    }

    public void setCentralOpenLoop(double centralDemand) {
        centralPrimary.set(ControlMode.PercentOutput, centralDemand);
    }

    public void extend(boolean extend) {
        liftExtend.set(extend ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

}
