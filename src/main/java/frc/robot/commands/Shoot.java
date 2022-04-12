package frc.robot.commands;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class Shoot extends SequentialCommandGroup {

    public Shoot(Drive drive, Shooter shooter, Limelight limelight, double speed, double rpm) {
        addCommands(
            new LockOntoTarget(drive, limelight),
            new RevShooter(shooter, speed, rpm),
            new RevIndexer(shooter),
            new WaitCommand(1),
            new TurnOffShooter(shooter)
        );
    }
}
