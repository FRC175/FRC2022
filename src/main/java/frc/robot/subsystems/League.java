package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


import frc.robot.Constants.DriveConstants;

public final class League extends SubsystemBase{
    
    private final TalonSRX leagueMotor;

    public static League instance;
    

    private League() {
        leagueMotor = new TalonSRX(DriveConstants.LEAGUE_PORT);
    }

    public static League getInstance() {
        if (instance == null) {
            instance = new League();
        }

        return instance;
    }


    public void spinBaby(double speed) {
        leagueMotor.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public void resetSensors() {
        
        
    }
}
