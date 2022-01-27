package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.Constants.LiftConstants;

public class Lift {

    private final VictorSPX primaryLeft;
    private final VictorSPX primaryRight;

    private static Lift instance;

    private Lift() {
        primaryLeft = new VictorSPX(LiftConstants.LEFT_PRIMARY_LIFT);
        primaryRight = new VictorSPX(LiftConstants.RIGHT_PRIMARY_LIFT);

        configureVictors();
    }

    public static Lift getInstance() {
        if (instance == null) {
            instance = new Lift();
        }

        return instance;
    }

    private void configureVictors() {
        primaryRight.configFactoryDefault();
        primaryLeft.configFactoryDefault();
    }

    

}
