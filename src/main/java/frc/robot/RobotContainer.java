// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.auto.DriveTarmac;
import frc.robot.commands.auto.LowGoalAndDrive;
import frc.robot.commands.auto.TwoBall;
import frc.robot.commands.lift.Climb;
import frc.robot.commands.shooter.ModifiedShoot;
import frc.robot.commands.shooter.Shoot;
import frc.robot.commands.shooter.TurnOffShooter;
import frc.robot.commands.auto.HighGoalAndDrive;
import frc.robot.models.AdvancedXboxController;
import frc.robot.models.XboxButton;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shuffleboard;
import frc.robot.subsystems.Limelight;
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
  private final Shuffleboard shuffleboard;
  // private final LED led;
  private final AdvancedXboxController driverController;
  private final AdvancedXboxController operatorController;
  private final SendableChooser<Command> autoChooser;

  private static RobotContainer instance;

  private boolean shift;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    drive = Drive.getInstance();
    intake = Intake.getInstance();
    lift = Lift.getInstance();
    shooter = Shooter.getInstance();
    limelight = Limelight.getInstance();
    shuffleboard = Shuffleboard.getInstance();
    // led = LED.getInstance();

    autoChooser = new SendableChooser<>();

    shift = false;

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

        drive.arcadeDrive(throttle, turn);

        if (shift) {
          drive.shift(true);
        } else {
          drive.shift(false);
        }

        SmartDashboard.putBoolean("Is Shift?", shift);

      }, drive).andThen(() -> drive.arcadeDrive(0, 0) , drive)
    );

    shuffleboard.setDefaultCommand(
      new RunCommand(() -> {
        shuffleboard.logShooter();
        shuffleboard.logClimb();
        shuffleboard.logIntake();
        // led.setColor(LEDColor.RED);
        // led.setPattern(LEDPattern.SINELON_OCEAN);
      }, shuffleboard)
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
    // ----------------------------------------------------------------------------------------------------
    // DRIVE
    // ----------------------------------------------------------------------------------------------------
    // shift gears
    new XboxButton(driverController, AdvancedXboxController.Button.A)
      .whenPressed(() -> shift = !shift);

    // ----------------------------------------------------------------------------------------------------
    // INTAKE
    // ----------------------------------------------------------------------------------------------------
    //intake spin
    new XboxButton(operatorController, AdvancedXboxController.Trigger.LEFT)
      .whenPressed(() -> intake.setIntakeOpenLoop(-0.65), intake)
      .whenReleased(() -> intake.setIntakeOpenLoop(0), intake);

    // reverse intake
    new XboxButton(driverController, AdvancedXboxController.Button.Y)
      .whileHeld(() -> intake.setIntakeOpenLoop(0.65), intake)
      .whenReleased(() -> intake.setIntakeOpenLoop(0), intake);

    // deploy intake
    new XboxButton(operatorController, AdvancedXboxController.DPad.DOWN)
      .whenPressed(() -> intake.deploy(true), intake)
      .whenReleased(() -> intake.setIntakeOpenLoop(0), intake);

    // retract intake
    new XboxButton(operatorController, AdvancedXboxController.DPad.UP)
      .whenPressed(() -> intake.deploy(false), intake)
      .whenReleased(() -> intake.setIntakeOpenLoop(0), intake);

    // ----------------------------------------------------------------------------------------------------
    // CLIMB
    // ----------------------------------------------------------------------------------------------------
    // Auto climb
    new XboxButton(driverController, AdvancedXboxController.DPad.UP)
      .whenPressed(new Climb(lift));

    //lift up
    new XboxButton(operatorController, AdvancedXboxController.Button.RIGHT_BUMPER)
      .whileHeld(() -> lift.setLiftOpenLoop(-0.9), lift)
      .whenReleased(() -> lift.setLiftOpenLoop(0), lift);

    //lift down
    new XboxButton(operatorController, AdvancedXboxController.Button.LEFT_BUMPER)
      .whileHeld(() -> lift.setLiftOpenLoop(0.9), lift)
      .whenReleased(() -> lift.setLiftOpenLoop(0), lift);

    // Manually lift up
    new XboxButton(driverController, AdvancedXboxController.Button.X)
      .whileHeld(() -> lift.resetLeftLift(0.4), lift)
      .whenReleased(() -> lift.resetLeftLift(0), lift);

    // Manually lift down
    new XboxButton(driverController, AdvancedXboxController.Button.B)
      .whileHeld(() -> lift.resetRightLift(0.4), lift)
      .whenReleased(() -> lift.resetRightLift(0), lift);

    // ----------------------------------------------------------------------------------------------------
    // SHOOTER
    // ----------------------------------------------------------------------------------------------------
    // Shoot upper hub (with limelight calculations)
    new XboxButton(operatorController, AdvancedXboxController.Trigger.RIGHT)
      .whenPressed(new Shoot(drive, shooter, limelight, 0, false))
      .whenReleased(new TurnOffShooter(shooter));

    // Add 50 RPM to offset for limelight calculations
    new XboxButton(operatorController, AdvancedXboxController.DPad.RIGHT)
      .whenPressed(() -> limelight.updateOffset(50), limelight);

    // Subtract 50 RPM from offset for limelight calculations
    new XboxButton(operatorController, AdvancedXboxController.DPad.LEFT)
      .whenPressed(() -> limelight.updateOffset(-50), limelight);

    // Manual Upper hub shot
    new XboxButton(operatorController, AdvancedXboxController.Button.Y)
      .whileHeld(new ModifiedShoot(shooter, limelight, 3600, true))
      .whenReleased(new TurnOffShooter(shooter));
    
    // Manual Lower hub shot
    new XboxButton(operatorController, AdvancedXboxController.Button.B)
      .whileHeld(new ModifiedShoot(shooter, limelight, 2000, true))
      .whenReleased(new TurnOffShooter(shooter));

    // Reverse shooter motors
    new XboxButton(operatorController, AdvancedXboxController.Button.X)
      .whenPressed(() -> {
        shooter.shooterSetOpenLoop(-0.5);
        shooter.indexerSetOpenLoop(-0.5);
      }, shooter)
      .whenReleased(new TurnOffShooter(shooter));

    new XboxButton(operatorController, AdvancedXboxController.Button.RIGHT_STICK)
      .whenPressed(() -> shooter.indexerSetOpenLoop(0.4))
      .whenReleased(() -> shooter.indexerSetOpenLoop(0));


    // ----------------------------------------------------------------------------------------------------
    // LIMELIGHT
    // ----------------------------------------------------------------------------------------------------
    // Toggle Limelight LEDs
    new XboxButton(operatorController, AdvancedXboxController.Button.A)
      .whenPressed(() -> {
        if (limelight.areLEDsOn()) {
          limelight.turnOffLED();
        } else {
          limelight.turnOnLED();
        }
      }, limelight);
  }

  private void configureAutoChooser() {
    autoChooser.setDefaultOption("TwoBall", new TwoBall(drive, shooter, intake, limelight));
    autoChooser.addOption("HighGoalAndShoot", new HighGoalAndDrive(drive, shooter, intake, limelight));
    autoChooser.addOption("LowGoalAndShoot", new LowGoalAndDrive(drive, shooter, intake, limelight));
    autoChooser.addOption("DriveTarmac", new DriveTarmac(drive));

    SmartDashboard.putData(autoChooser);
  }

  public Command getAutoMode() {
    return autoChooser.getSelected();
  } 
}
