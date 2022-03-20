package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class Shoot extends SequentialCommandGroup {

    public Shoot(Shooter shooter) {
        addCommands(
            new RevShooter(shooter, 0.33, 2000),
            new RevIndexer(shooter)
        );
    }
}
