package frc.robot.commands.shooter;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class ModifiedShoot extends SequentialCommandGroup {

    public ModifiedShoot(Shooter shooter, Limelight limelight, double speed, boolean staticRPM) {
        addCommands(
            new RevShooter(shooter, limelight, speed, staticRPM),
            new RevIndexer(shooter),
            new WaitCommand(2),
            new TurnOffShooter(shooter)
        );
    }
}
