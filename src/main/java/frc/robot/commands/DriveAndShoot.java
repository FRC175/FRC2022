package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;


public final class DriveAndShoot extends SequentialCommandGroup {

    public DriveAndShoot(Drive drive, Shooter shoot, boolean strong) {
        addCommands(
                new Shoot(shoot, strong),
                new WaitCommand(2),
                new DriveTarmac(drive),
                new InstantCommand(() -> drive.setOpenLoop(0, 0))

        );
    }

}
