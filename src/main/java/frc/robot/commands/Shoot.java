package frc.robot.commands;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class Shoot extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter shooter;
  private final Limelight limelight;
  private String hubToScore;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public Shoot(Shooter shooter, Limelight limelight, String hubToScore) {
      this.shooter = shooter;
      this.limelight = limelight;
	  
	  this.hubToScore = hubToScore;

      addRequirements(shooter, limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      shooter.resetSensors();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
	  double estimateRPM = limelight.calculateRPM(limelight.distance(), hubToScore);
	  shooter.shooterSetOpenLoop(estimateRPM / 6000);
	  shooter.indexerSetOpenLoop(1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    new WaitCommand(0.3);
    shooter.shooterSetOpenLoop(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
