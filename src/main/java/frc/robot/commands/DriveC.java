package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveC extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive drive;
  private final int counts;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveC(Drive drive, int counts) {
    this.drive = drive;
    this.counts = counts;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      drive.resetSensors();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      while (drive.rightCounts() <= counts) {
        // if (drive.getRange() > 30) {
          drive.accelDrive(0.5, 0);
        // }
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    return true;
  }
}
