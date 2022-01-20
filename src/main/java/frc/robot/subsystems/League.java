package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import frc.robot.Constants.DriveConstants;

public final class League extends SubsystemBase{
    
    private final TalonSRX leagueMotor;
    private final CANSparkMax fortniteMotor;

    public static League instance;
    

    private League() {
        leagueMotor = new TalonSRX(DriveConstants.LEAGUE_PORT);
        fortniteMotor = new CANSparkMax(DriveConstants.FORTNITE_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public static League getInstance() {
        if (instance == null) {
            instance = new League();
        }

        return instance;
    }


    public void spinBaby(double speedY, double speedX) {
        leagueMotor.set(ControlMode.PercentOutput, speedX);
        fortniteMotor.set(speedY);
    }

    @Override
    public void resetSensors() {
        
        
    }
}
