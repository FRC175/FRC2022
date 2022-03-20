package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.DeployIntake;
import frc.robot.commands.DriveAuto;
import frc.robot.commands.RevIndexer;
import frc.robot.commands.RevShooter;
import frc.robot.commands.TurnOffShooter;


public final class LowGoalAndDrive extends SequentialCommandGroup {

    public LowGoalAndDrive(Drive drive, Shooter shooter, Intake intake) {

        addCommands(
            new DeployIntake(intake),
            new WaitCommand(2),
            new RevShooter(shooter, 0.33, 2000),
            new RevIndexer(shooter),
            new WaitCommand(1),
            new TurnOffShooter(shooter),
            new DriveAuto(drive, 70)
        );
    }

}
