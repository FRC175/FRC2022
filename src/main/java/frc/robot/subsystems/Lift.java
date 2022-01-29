package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants.LiftConstants;

public final class Lift extends SubsystemBase {

    private final VictorSPX leftPrimary, rightPrimary;

    private static Lift instance;

    private Lift() {
        leftPrimary = new VictorSPX(LiftConstants.LEFT_PRIMARY_LIFT);
        rightPrimary = new VictorSPX(LiftConstants.RIGHT_PRIMARY_LIFT);

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

}
