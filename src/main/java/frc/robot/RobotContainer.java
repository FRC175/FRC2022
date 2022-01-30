// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.models.AdvancedXboxController;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lift;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.models.XboxButton;
import frc.robot.models.AdvancedXboxController.Button;

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
  private final Intake intake;
  private final Lift lift;

  private final AdvancedXboxController driverController;
  private final AdvancedXboxController operatorController;

  private static RobotContainer instance;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    drive = Drive.getInstance();
    intake = Intake.getInstance();
    lift = Lift.getInstance();

    driverController = new AdvancedXboxController(ControllerConstants.DRIVER_CONTROLLER_PORT, ControllerConstants.CONTROLLER_DEADBAND);
    operatorController = new AdvancedXboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT, ControllerConstants.CONTROLLER_DEADBAND);

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
        double turn = driverController.getLeftX();
        drive.arcadeDrive(throttle, turn);
        drive.camRotate();
      }, 
      drive
      ).andThen(() -> drive.arcadeDrive(0, 0), drive)
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
    new XboxButton(driverController, AdvancedXboxController.Button.A)
        .whileHeld(() -> intake.setIntakeOpenLoop(0.25), intake)
        .whenReleased(() -> intake.setIntakeOpenLoop(0), intake);

    new XboxButton(driverController, AdvancedXboxController.Button.B)
        .whileHeld(() -> lift.setLiftOpenLoop(0.5, 0.5), lift)
        .whenReleased(() -> lift.setLiftOpenLoop(0, 0), lift);

    new XboxButton(operatorController, AdvancedXboxController.Button.RIGHT_BUMPER)
        .whenPressed(() -> drive.camAngle(true), drive);

    new XboxButton(operatorController, AdvancedXboxController.Button.LEFT_BUMPER)
        .whenPressed(() -> drive.camAngle(false), drive);

    new XboxButton(driverController, AdvancedXboxController.Button.X)
        .whileHeld(() -> drive.shift(true), drive)
        .whenReleased(() -> drive.shift(false), drive);
  }

  // /**
  //  * Use this to pass the autonomous command to the main {@link Robot} class.
  //  *
  //  * @return the command to run in autonomous
  //  */
  // public Command getAutonomousCommand() {}
}
