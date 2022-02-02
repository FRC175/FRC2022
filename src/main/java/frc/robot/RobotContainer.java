// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.models.AdvancedXboxController;
import frc.robot.models.XboxButton;
import frc.robot.models.AdvancedXboxController.Button;
import frc.robot.positions.LEDColor;
import frc.robot.positions.LEDPattern;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.League;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.LED;
import edu.wpi.first.wpilibj2.command.RunCommand;


import static frc.robot.Constants.ControllerConstants;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drive drive;
  private final League league;
  private final Intake intake;
  private final Lift lift;
  private final LED led;
  private boolean inverse; 
  private final AdvancedXboxController driverController;
  private final AdvancedXboxController operatorController;

  private static RobotContainer instance;

  // private int gooda;
  private int degrees;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    drive = Drive.getInstance();
    league = League.getInstance();
    intake = Intake.getInstance();
    lift = Lift.getInstance();
    led = LED.getInstance();
    inverse = false; 

    driverController = new AdvancedXboxController(ControllerConstants.DRIVER_CONTROLLER_PORT, ControllerConstants.CONTROLLER_DEADBAND);
    operatorController = new AdvancedXboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT, ControllerConstants.CONTROLLER_DEADBAND);

    degrees = 90;
    // Configure the default commands
    configureDefaultCommands();

    // Configure the button bindings
    configureButtonBindings();
  }

  public static RobotContainer getInstance() {
    if (instance == null) {
        instance = new RobotContainer();
    }

    return instance;
  }

  private void configureDefaultCommands() {
    // Arcade Drive
    drive.setDefaultCommand(
      // While the drive subsystem is not called by other subsystems, call the arcade drive method using the
      // controller's throttle and turn. When it is called, set the motors to 0% power.
      new RunCommand(() -> {
        double throttle = driverController.getRightTriggerAxis() - driverController.getLeftTriggerAxis();
        double turn = -1 * driverController.getLeftX(); //-1 to turn in correct direction
         
      if (inverse) {
        drive.inverseDrive(throttle, turn);
      } else {
        drive.arcadeDrive(throttle, turn);
      }

      if (inverse) {
        if (throttle > 0) {
          led.setColor(LEDColor.RED);
        } else if (throttle < 0) {
          led.setColor(LEDColor.GREEN);
        } else {
          led.setColor(LEDColor.YELLOW);
        }
      } else {
        if (throttle > 0) {
          led.setColor(LEDColor.GREEN);
        } else if (throttle < 0) {
          led.setColor(LEDColor.RED);
        } else {
          led.setColor(LEDColor.YELLOW);
        }
        league.rotate(degrees);
      }
      },
     
      drive
      ).andThen(() -> drive.arcadeDrive(0, 0) , drive)
  
  );
    
    intake.setDefaultCommand(
      new RunCommand(() -> {
        intake.getColorOnIntake();
        intake.getColorString();
      }, intake)
    );

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new XboxButton(driverController, AdvancedXboxController.Button.RIGHT_BUMPER)
      .whileHeld(() -> {
        degrees = degrees == 180 ? degrees : degrees + 5;
      });

    new XboxButton(driverController, AdvancedXboxController.Button.LEFT_BUMPER)
      .whileHeld(() -> {
         degrees = degrees == 0 ? degrees : degrees - 5;
      });

    new XboxButton(driverController, AdvancedXboxController.Button.A)
      .whileHeld(() -> intake.setIntakeOpenLoop(0.25), intake)
      .whenReleased(() -> intake.setIntakeOpenLoop(0), intake);

    new XboxButton(driverController, AdvancedXboxController.Button.B)
      .whileHeld(() -> lift.setLiftOpenLoop(0.5, 0.5), lift)
      .whenReleased(() -> lift.setLiftOpenLoop(0, 0), lift);

    new XboxButton(operatorController, AdvancedXboxController.Button.RIGHT_BUMPER)
      .whileHeld(() -> drive.updateCamAngle(true), drive);

    new XboxButton(operatorController, AdvancedXboxController.Button.LEFT_BUMPER)
      .whileHeld(() -> drive.updateCamAngle(false), drive);

    new XboxButton(driverController, AdvancedXboxController.Button.X)
      .whileHeld(() -> drive.shift(true), drive)
      .whenReleased(() -> drive.shift(false), drive);

    new XboxButton(operatorController, AdvancedXboxController.Button.A)
      .whileHeld(() -> intake.deploy(true), intake)
      .whenReleased(() -> intake.deploy(false), intake);

    new XboxButton(operatorController, AdvancedXboxController.Button.B)
      .whileHeld(() -> lift.extend(true), lift)
      .whenReleased(() -> lift.extend(false), lift);
    }
}
