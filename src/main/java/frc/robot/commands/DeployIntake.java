package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;


public class DeployIntake extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Intake intake;

  /**
   * Creates a new RevIndexer command.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DeployIntake(Intake intake) {
      this.intake = intake;

      addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      intake.resetSensors();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      intake.deploy(false);
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
