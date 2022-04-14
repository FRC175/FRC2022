package frc.robot.commands.shooter;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class RevShooter extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter shooter;
  private final Limelight limelight;

  private double rpm;
  private boolean staticRPM;

  /**
   * Creates a new RevShooter command.
   *
   * @param subsystem The subsystem used by this command.
   */
  public RevShooter(Shooter shooter, Limelight limelight, double rpm, boolean staticRPM) {
      this.shooter = shooter;
      this.limelight = limelight;
      this.rpm = rpm;
      this.staticRPM = staticRPM;
      System.out.println("DEFINED RPM: " + rpm);

      addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    rpm = !staticRPM ? limelight.getFinalRPM() : rpm;
    shooter.indexerSetOpenLoop(0);
	  shooter.shooterSetOpenLoop(rpm / 6000);
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
