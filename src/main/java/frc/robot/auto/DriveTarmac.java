package frc.robot.auto;

import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveTarmac extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive m_subsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveTarmac(Drive subsystem) {
    m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      m_subsystem.resetSensors();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      while (m_subsystem.rightCounts() <= 40) {
          m_subsystem.accelDrive(0.5, 0);
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    return true;
  }
}
