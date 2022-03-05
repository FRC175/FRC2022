package frc.robot.commands.auto;

import frc.robot.commands.DriveC;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DriveTarmac extends SequentialCommandGroup{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public DriveTarmac(Drive drive) {
      addCommands(
              new DriveC(drive, 40)
      );
  }
}