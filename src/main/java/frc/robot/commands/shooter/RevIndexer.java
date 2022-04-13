package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class RevIndexer extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter shooter;

  /**
   * Creates a new RevIndexer command.
   *
   * @param subsystem The subsystem used by this command.
   */
  public RevIndexer(Shooter shooter) {
      this.shooter = shooter;

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

		shooter.indexerSetOpenLoop(0.4);

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
    return true;
  }
}
