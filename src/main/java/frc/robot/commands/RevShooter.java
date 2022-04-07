package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class RevShooter extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter shooter;

  private double speed;
  private double rpm;

  /**
   * Creates a new RevShooter command.
   *
   * @param subsystem The subsystem used by this command.
   */
  public RevShooter(Shooter shooter, double speed, double rpm) {
      this.shooter = shooter;
      this.speed = speed;
      this.rpm = rpm;

      addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      shooter.resetSensors();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  shooter.indexerSetOpenLoop(0);
	shooter.shooterSetOpenLoop(speed);

    //   if (colorSensor.isRightBall(SmartDashboard.getString("Team Color", "null"))) {
    //     double estimateRPM = limelight.calculateRPM(limelight.distance(), hubToScore);
    //     double percentDemandToSend = Math.abs(estimateRPM / 6000);

    //     shooter.shooterSetOpenLoop(percentDemandToSend);

    //     SmartDashboard.putString("Shooter Status", "Reving Indexer. Firing...");
    //     shooter.indexerSetOpenLoop(0.5);
    //   } else {
    //     //wimpy shot
    //     shooter.shooterSetOpenLoop(0.25);
    //     shooter.indexerSetOpenLoop(0.5);
    //   }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    System.out.println(rpm);
    System.out.println("isFinished: " + Math.abs(shooter.getShooterRPM() - rpm));
    return Math.abs(shooter.getShooterRPM() - rpm) < 230;
  }
}
