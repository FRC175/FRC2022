package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;


public class DeployIntake extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Intake intake;

  /**
   * Creates a new DeployIntake command.
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
      intake.deploy(true);
      System.out.println("deploy intake");
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
