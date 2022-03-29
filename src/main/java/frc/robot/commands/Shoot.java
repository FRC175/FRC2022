package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class Shoot extends SequentialCommandGroup {

    public Shoot(Shooter shooter, double speed, double rpm) {
        addCommands(
            new RevShooter(shooter, speed, rpm),
            new RevIndexer(shooter),
            new WaitCommand(0.5),
            new TurnOffShooter(shooter)
        );
    }
}
