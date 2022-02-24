// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.DriveTarmac;
import frc.robot.models.AdvancedXboxController;
import frc.robot.models.XboxButton;
import frc.robot.positions.LEDColor;
// import frc.robot.positions.LEDPattern;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.LED;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.DriveAndShoot;


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
  private final Limelight limelight;
  private final LED led;
  private final Shooter shooter;
  private boolean inverse; 
  private final AdvancedXboxController driverController;
  private final AdvancedXboxController operatorController;


  private final SendableChooser<Command> autoChooser;

  private static RobotContainer instance;


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    drive = Drive.getInstance();
    intake = Intake.getInstance();
    lift = Lift.getInstance();
    shooter = Shooter.getInstance();
    limelight = Limelight.getInstance();
    led = LED.getInstance();

    autoChooser = new SendableChooser<>();
    inverse = false; 

    driverController = new AdvancedXboxController(ControllerConstants.DRIVER_CONTROLLER_PORT, ControllerConstants.CONTROLLER_DEADBAND);
    operatorController = new AdvancedXboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT, ControllerConstants.CONTROLLER_DEADBAND);

    // Configure the default commands
    configureDefaultCommands();

    // Configure the button bindings
    configureButtonBindings();

    configureAutoChooser();
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
        drive.inverseDrive(Math.abs(throttle) > 0.15 ? throttle * 0.5 : 0, turn * 0.75);
      } else {
        drive.arcadeDrive(Math.abs(throttle) > 0.15 ? throttle * 0.5 : 0, turn * 0.75);
      }

        // System.out.println(drive.dist());

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
        // drive.camRotate();
      }
      // System.out.println(drive.getRightRPM());
      // limelight.isTargetDetected();
      // limelight.distance();
      
      },
     
      drive
      ).andThen(() -> drive.arcadeDrive(0, 0) , drive)
  
  );
    
    intake.setDefaultCommand(
      new RunCommand(() -> {
        intake.getColorOnIntake();
        intake.getColorString();
        lift.getAngle();
      }, intake)
    );

    shooter.setDefaultCommand(
      new RunCommand(() -> {
        double demand = operatorController.getRightY();
        shooter.shooterSetOpenLoop(demand);
        // System.out.println(shooter.getShooterRPM());

      }, shooter
      ).andThen(() -> shooter.shooterSetOpenLoop(0), shooter)
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
      .whileHeld(() -> drive.updateCamAngle(true), drive);

    new XboxButton(operatorController, AdvancedXboxController.Button.LEFT_BUMPER)
      .whileHeld(() -> drive.updateCamAngle(false), drive);

    new XboxButton(driverController, AdvancedXboxController.Button.X)
      .whileHeld(() -> drive.shift(true), drive)
      .whenReleased(() -> drive.shift(false), drive);

    new XboxButton(driverController, AdvancedXboxController.Button.Y)
      .whenPressed(() -> inverse = inverse ? false : true);

    new XboxButton(operatorController, AdvancedXboxController.Button.X)
      .whileHeld(() -> intake.deploy(true), intake)
      .whenReleased(() -> intake.deploy(false), intake);

    new XboxButton(operatorController, AdvancedXboxController.Button.Y)
      .whileHeld(() -> lift.extend(true), lift)
      .whenReleased(() -> lift.extend(false), lift);

    // new XboxButton(operatorController, AdvancedXboxController.Button.A)
    //   .whileHeld(() -> shooter.indexerSetOpenLoop(0.25), shooter)
    //   .whenReleased(() -> shooter.indexerSetOpenLoop(0), shooter);
    }


  private void configureAutoChooser() {
    autoChooser.setDefaultOption("DriveAndShoot", new DriveAndShoot(drive, shooter, true));
  }

  public Command getAutoMode() {
    return autoChooser.getSelected();
  } 
}
