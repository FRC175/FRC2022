package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.util.Color;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.ColorMatchResult;

public class ColorSensor extends SubsystemBase{
    private final ColorSensorV3 colorSensor;
    private final ColorMatch colorMatch;
    private final Color RED_CARGO = new Color(0.439, 0.394, 0.165);
    private final Color BLUE_CARGO = new Color(0.139, 0.429, 0.377);

    private static ColorSensor instance;

    public ColorSensor() {
        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
        colorMatch = new ColorMatch();

        configureColorMatches();
    }

    public static ColorSensor getInstance() {
        if (instance == null) {
            instance = new ColorSensor();
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

    public String getColorString() {
        String colorString;
        ColorMatchResult match = colorMatch.matchClosestColor(colorSensor.getColor());
    
        if (match.color == RED_CARGO) {
          colorString = "red";
        } else if (match.color == BLUE_CARGO) {
          colorString = "blue";
        } else {
          colorString = "Unknown";
        }

        SmartDashboard.putString("Color", colorString);
        return colorString;
    }

    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    }
}
