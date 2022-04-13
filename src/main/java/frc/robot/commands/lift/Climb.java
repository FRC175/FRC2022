package frc.robot.commands.lift;

import frc.robot.subsystems.Lift;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Climb extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Lift lift;
  private double maxCounts;

  /**
   * Creates a new Climb command.
   *
   * @param subsystem The subsystem used by this command.
   */
  public Climb(Lift lift) {
      this.lift = lift;

      maxCounts = 5.7;

      addRequirements(lift);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      lift.setLiftOpenLoop(1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
      lift.setLiftOpenLoop(0);
  }

  // Returns true when the command should end.
  //@Override
  //public void isFinished() {
    // return Math.abs(lift.getLeftEncoderCounts().doubleValue()) >= maxCounts && lift.getRightEncoderCounts().doubleValue() >= maxCounts;
    //return true
  //}
}
