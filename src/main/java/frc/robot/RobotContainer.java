// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.Shoot;
import frc.robot.commands.auto.DriveAndShoot;
import frc.robot.commands.auto.DriveTarmac;
import frc.robot.models.AdvancedXboxController;
import frc.robot.models.XboxButton;
// import frc.robot.positions.LEDPattern;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ColorSensor;
import edu.wpi.first.wpilibj2.command.Command;
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
  private final Intake intake;
  private final Lift lift;
  private final Limelight limelight;
  private final Shooter shooter;
  private final ColorSensor colorSensor; 
  private final AdvancedXboxController driverController;
  private final AdvancedXboxController operatorController;
  private final SendableChooser<Command> autoChooser;

  private static RobotContainer instance;

  private boolean inverse;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    drive = Drive.getInstance();
    intake = Intake.getInstance();
    lift = Lift.getInstance();
    shooter = Shooter.getInstance();
    limelight = Limelight.getInstance();
    colorSensor = ColorSensor.getInstance();

    autoChooser = new SendableChooser<>();
    inverse = false; 

    driverController = new AdvancedXboxController(ControllerConstants.DRIVER_CONTROLLER_PORT, ControllerConstants.CONTROLLER_DEADBAND);
    operatorController = new AdvancedXboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT, ControllerConstants.CONTROLLER_DEADBAND);

    // Configure the default commands
    configureDefaultCommands();

    // Configure the button bindings
    configureButtonBindings();

    // Configure auto mode
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
         
        // inverse / regular drive
        if (inverse) {
          drive.inverseDrive(Math.abs(throttle) > 0.15 ? throttle * 0.4 : 0, turn * 0.25);
        } else {
          drive.arcadeDrive(Math.abs(throttle) > 0.15 ? throttle * 0.4 : 0, turn * 0.25);
        }

      }, drive ).andThen(() -> drive.arcadeDrive(0, 0) , drive)
    );

    // shooter.setDefaultCommand(
    //   new RunCommand(() -> {
    //     double demand = operatorController.getRightY();
    //     shooter.shooterSetOpenLoop(-Math.abs(demand));
    //     shooter.getShooterRPM();

    //   }, shooter
    //   ).andThen(() -> shooter.shooterSetOpenLoop(0), shooter)
    // );

    colorSensor.setDefaultCommand(
      new RunCommand(() -> {
        colorSensor.getColorOnIntake();
        colorSensor.getColorString();
      }, colorSensor)
    );
  }

  public static double getVoltage() {
    return RobotController.getVoltage5V();
  }
  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //shift
    new XboxButton(driverController, AdvancedXboxController.Button.LEFT_STICK)
      .whileHeld(() -> drive.shift(true), drive)
      .whenReleased(() -> drive.shift(false), drive);

    //inverse drive
    new XboxButton(driverController, AdvancedXboxController.Button.B)
      .whileHeld(() -> inverse = inverse ? false : true);

    //intake spin
    new XboxButton(operatorController, AdvancedXboxController.Trigger.LEFT)
      .whenPressed(() -> intake.setIntakeOpenLoop(1), intake)
      .whenReleased(() -> intake.setIntakeOpenLoop(0), intake);

    // deploy intake
    new XboxButton(operatorController, AdvancedXboxController.DPad.DOWN)
      .whenPressed(() -> intake.deploy(true), intake);

    // retract intake
    new XboxButton(operatorController, AdvancedXboxController.DPad.UP)
      .whenPressed(() -> intake.deploy(false), intake);

    //lift up
    new XboxButton(operatorController, AdvancedXboxController.Button.RIGHT_BUMPER)
      .whileHeld(() -> lift.setLiftOpenLoop(0.5, 0.5), lift)
      .whenReleased(() -> lift.setLiftOpenLoop(0, 0), lift);

    //lift down
    new XboxButton(operatorController, AdvancedXboxController.Button.LEFT_BUMPER)
      .whileHeld(() -> lift.setLiftOpenLoop(-0.5, -0.5), lift)
      .whenReleased(() -> lift.setLiftOpenLoop(0, 0), lift);

    //shoot
    new XboxButton(operatorController, AdvancedXboxController.Trigger.RIGHT)
      .whenPressed(new Shoot(shooter, limelight, colorSensor, "upper", true))
      .whenReleased(() -> {
        shooter.shooterSetOpenLoop(0);
        shooter.indexerSetOpenLoop(0);
      }, shooter);

    //shooter wimpy shot manual
    new XboxButton(operatorController, AdvancedXboxController.Button.Y)
      .whileHeld(() -> {
        shooter.shooterSetOpenLoop(.25);
        shooter.indexerSetOpenLoop(.5);
      }, shooter)
      .whenReleased(() -> {
        shooter.shooterSetOpenLoop(0);
        shooter.indexerSetOpenLoop(0);
      }, shooter);
    }


  private void configureAutoChooser() {
    autoChooser.setDefaultOption("DriveTarmac", new DriveTarmac(drive));
    autoChooser.addOption("DriveAndShoot", new DriveAndShoot(drive, shooter, limelight, colorSensor, "upper"));
  }

  public Command getAutoMode() {
    return autoChooser.getSelected();
  } 
}
