// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.RevShooter;
import frc.robot.commands.Shoot;
import frc.robot.commands.TurnOffShooter;
import frc.robot.commands.auto.DriveTarmac;
import frc.robot.commands.auto.LowGoalAndDrive;
import frc.robot.commands.auto.HighGoalAndDrive;
import frc.robot.models.AdvancedXboxController;
import frc.robot.models.XboxButton;
import frc.robot.models.AdvancedXboxController.Button;
// import frc.robot.positions.LEDPattern;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shuffleboard;
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
  private final Shuffleboard shuffleboard;
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
    shuffleboard = Shuffleboard.getInstance();

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
          drive.inverseDrive(throttle, turn);
        } else {
          drive.arcadeDrive(throttle, turn);
        }

      }, drive ).andThen(() -> drive.arcadeDrive(0, 0) , drive)
    );

    // shooter.setDefaultCommand(
    //   new RunCommand(() -> {
    //     double demand = operatorController.getRightY();
    //     shooter.shooterSetOpenLoop(Math.abs(demand));
    //   }, shooter
    //   ).andThen(() -> shooter.shooterSetOpenLoop(0), shooter)
    // );

    colorSensor.setDefaultCommand(
      new RunCommand(() -> {
        colorSensor.getColorOnIntake();
        colorSensor.getColorString();
      }, colorSensor)
    );

    shuffleboard.setDefaultCommand(
      new RunCommand(() -> shuffleboard.logShooter(), shuffleboard)
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

    // //inverse drive
    // new XboxButton(driverController, AdvancedXboxController.Button.B)
    //   .whileHeld(() -> inverse = inverse ? false : true);

    //intake spin
    new XboxButton(operatorController, AdvancedXboxController.Trigger.LEFT)
      .whenPressed(() -> intake.setIntakeOpenLoop(-0.5), intake)
      .whenReleased(() -> intake.setIntakeOpenLoop(0), intake);

    // deploy intake
    new XboxButton(operatorController, AdvancedXboxController.DPad.DOWN)
      .whenPressed(() -> intake.deploy(false), intake)
      .whenReleased(() -> intake.setIntakeOpenLoop(0), intake);

    // retract intake
    new XboxButton(operatorController, AdvancedXboxController.DPad.UP)
      .whenPressed(() -> intake.deploy(true), intake)
      .whenReleased(() -> intake.setIntakeOpenLoop(0), intake);

    //lift up
    new XboxButton(operatorController, AdvancedXboxController.Button.RIGHT_BUMPER)
      .whileHeld(() -> lift.setLiftOpenLoop(1), lift)
      .whenReleased(() -> lift.setLiftOpenLoop(0), lift);

    //lift down
    new XboxButton(operatorController, AdvancedXboxController.Button.LEFT_BUMPER)
      .whileHeld(() -> lift.setLiftOpenLoop(-1), lift)
      .whenReleased(() -> lift.setLiftOpenLoop(0), lift);

    //shoot
    // new XboxButton(operatorController, AdvancedXboxController.Trigger.RIGHT)
    //   .whileHeld(new Shoot(shooter))
    //   .whenReleased(new TurnOffShooter(shooter));

    // Upper hub
    // new XboxButton(operatorController, AdvancedXboxController.Button.Y)
    //   .whileHeld(() -> shooter.shooterSetOpenLoop(0.6), shooter)
    //   .whenReleased(() -> shooter.shooterSetOpenLoop(0), shooter);

    // new XboxButton(operatorController, AdvancedXboxController.Button.B)
    //   .whileHeld(() -> shooter.shooterSetOpenLoop(0.4))
    //   .whenReleased(() -> shooter.turnOffShooter(), shooter);
    
    // Lower hub
    // new XboxButton(operatorController, AdvancedXboxController.Button.A)
    //   .whileHeld(() -> shooter.shooterSetOpenLoop(0.33))
    //   .whenReleased(() -> shooter.turnOffShooter(), shooter);

    // shooter wimpy shot manual
    new XboxButton(operatorController, AdvancedXboxController.Button.X)
      .whileHeld(() -> {
        shooter.shooterSetOpenLoop(0.25);
        shooter.indexerSetOpenLoop(0.5);
      }, shooter)
      .whenReleased(() -> shooter.turnOffShooter(), shooter);

    new XboxButton(driverController, AdvancedXboxController.Button.X)
      .whenPressed(() -> {
        shooter.shooterSetOpenLoop(-0.5);
        shooter.indexerSetOpenLoop(-0.5);
      }, shooter)
      .whenReleased(() -> shooter.turnOffShooter(), shooter);

    new XboxButton(operatorController, AdvancedXboxController.Trigger.RIGHT)
      .whenPressed(new Shoot(shooter, (limelight.calculateRPM(limelight.distance(), "upper") / 6000), limelight.calculateRPM(limelight.distance(), "upper")))
      .whenReleased(new TurnOffShooter(shooter));
      
    new XboxButton(operatorController, AdvancedXboxController.Button.A)
      .whenPressed(() -> limelight.turnOnLED(), limelight);

      new XboxButton(operatorController, AdvancedXboxController.Button.B)
      .whenPressed(new Shoot(shooter, 0.33, 2000))
      .whenReleased(new TurnOffShooter(shooter));
    }


  private void configureAutoChooser() {
    autoChooser.setDefaultOption("HighGoalAndShoot", new HighGoalAndDrive(drive, shooter, intake));
    autoChooser.addOption("LowGoalAndShoot", new LowGoalAndDrive(drive, shooter, intake));
    autoChooser.addOption("DriveTarmac", new DriveTarmac(drive));

    SmartDashboard.putData(autoChooser);
  }

  public Command getAutoMode() {
    return autoChooser.getSelected();
  } 
}
