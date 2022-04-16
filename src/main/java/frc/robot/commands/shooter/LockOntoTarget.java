package frc.robot.commands.shooter;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class LockOntoTarget extends CommandBase {

  private final Drive drive;
  private final Limelight limelight;

  /**
   * Creates a new LockOntoTarget command.
   *
   * @param subsystem The subsystem used by this command.
   */
  public LockOntoTarget(Drive drive, Limelight limelight) {
    this.drive = drive;
    this.limelight = limelight;

    addRequirements(drive, limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.arcadeDrive(0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double horizontalOffset = limelight.getHorizontalOffset();
    if (horizontalOffset > 3 || horizontalOffset < 0) {
      drive.arcadeDrive(0, horizontalOffset < 0 ? 0.08 : -0.08);
    } else {
      cancel();
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
    return limelight.getHorizontalOffset() <= 3 && limelight.getHorizontalOffset() >= 0;
  }
}
