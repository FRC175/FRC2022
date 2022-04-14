package frc.robot.commands.shooter;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class ShootAuto extends SequentialCommandGroup {

    public ShootAuto(Drive drive, Shooter shooter, Limelight limelight, double speed, boolean staticRPM) {
        addCommands(
            new InstantCommand(() -> System.out.println("Sent Speed: " + speed)),
            new RevShooter(shooter, limelight, speed, staticRPM),
            new RevIndexer(shooter),
            new WaitCommand(2),
            new TurnOffShooter(shooter)
        );
    }
}
