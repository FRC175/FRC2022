// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class IntakeSensor extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Intake intake;
  private final ColorSensor colorSensor;
  private String color;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public IntakeSensor(Intake intake, ColorSensor colorSensor) {
    this.intake = intake;
    this.colorSensor = colorSensor;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake, colorSensor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      color = SmartDashboard.getString("Team Color", "null");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      if (color.toLowerCase() == "blue") {
          if (colorSensor.getColorString().toLowerCase() == "blue") {
              intake.setIntakeOpenLoop(1);
          } else {
              intake.setIntakeOpenLoop(-1);
          }
      } else {
          if (colorSensor.getColorString().toLowerCase() == "red") {
              intake.setIntakeOpenLoop(1);
          } else {
              intake.setIntakeOpenLoop(-1);
          }
      }
  }
//chicken butt
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
