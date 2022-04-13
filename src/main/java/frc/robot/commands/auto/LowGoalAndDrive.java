package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.commands.intake.DeployIntake;
import frc.robot.commands.shooter.RevIndexer;
import frc.robot.commands.shooter.RevShooter;
import frc.robot.commands.shooter.TurnOffShooter;


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
