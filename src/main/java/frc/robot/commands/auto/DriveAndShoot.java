package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Shoot;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;


public final class DriveAndShoot extends SequentialCommandGroup {

    public DriveAndShoot(Drive drive, Shooter shoot, Limelight limelight, String hubToScore) {
        addCommands(
                new Shoot(shoot, limelight, hubToScore, true),
                new WaitCommand(0.5),
                new Shoot(shoot, limelight, hubToScore, false),
                new DriveTarmac(drive)
        );
    }

}
