package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.commands.intake.DeployIntake;
import frc.robot.commands.shooter.ModifiedShoot;

public final class HighGoalAndDrive extends SequentialCommandGroup {

    public HighGoalAndDrive(Drive drive, Shooter shooter, Intake intake, Limelight limelight) {

        addCommands(
            new DeployIntake(intake),
            new DriveAuto(drive, 25),
            new WaitCommand(2),
            new ModifiedShoot(shooter, limelight, 3600, true),
            new DriveAuto(drive, 50)
        );
    }

}
