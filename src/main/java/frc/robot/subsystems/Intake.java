package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import frc.robot.Constants;

public final class Intake extends SubsystemBase {

    private final ColorSensorV3 colorSensor;
    private final ColorMatch colorMatch;
    private final Color RED_CARGO = new Color(0.439, 0.394, 0.165);
    private final Color BLUE_CARGO = new Color(0.139, 0.429, 0.377);

    private final CANSparkMax intakeMotor;



    private static Intake instance;

    private Intake() {
        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
        colorMatch = new ColorMatch();
        
        intakeMotor =  new CANSparkMax(Constants.IntakeConstants.INTAKE_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeMotor.restoreFactoryDefaults();

        configureColorMatches();
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;
    }

    private void configureColorMatches() {
      colorMatch.addColorMatch(RED_CARGO);
      colorMatch.addColorMatch(BLUE_CARGO);
  }

    public void getColorOnIntake() {
        SmartDashboard.putNumber("Red", colorSensor.getColor().red);
        SmartDashboard.putNumber("Green", colorSensor.getColor().green);
        SmartDashboard.putNumber("Blue", colorSensor.getColor().blue);
    }

    public void getColorString() {
        String colorString;
        ColorMatchResult match = colorMatch.matchClosestColor(colorSensor.getColor());
    
        if (match.color == RED_CARGO) {
          colorString = "Red";
        } else if (match.color == BLUE_CARGO) {
          colorString = "Blue";
        } else {
          colorString = "Unknown";
        }

        SmartDashboard.putString("Color", colorString);
    }


    public void setIntakeOpenLoop(double demand) {
      intakeMotor.set(demand);
    }

    @Override
    public void resetSensors() {
        
    }
}
