package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.commands.intake.DeployIntake;
import frc.robot.commands.intake.RunIntake;
import frc.robot.commands.shooter.LockOntoTarget;
import frc.robot.commands.shooter.RevIndexer;
import frc.robot.commands.shooter.RevShooter;
import frc.robot.commands.shooter.TurnOffShooter;


public final class TwoBall extends SequentialCommandGroup {

    public TwoBall(Drive drive, Shooter shooter, Intake intake, Limelight limelight) {

        addCommands(
            new DeployIntake(intake),
            new DriveAuto(drive, 20),
            new LockOntoTarget(drive, limelight),
            new RevShooter(shooter, limelight.getFinalRPM() / 6000, limelight.getFinalRPM()),
            new RevIndexer(shooter),
            new WaitCommand(1),
            new TurnOffShooter(shooter),
            new RunIntake(intake, true),
            new DriveAuto(drive, 45),
            new WaitCommand(1),
            new DriveAuto(drive, -30),
            new LockOntoTarget(drive, limelight),
            new RevShooter(shooter, limelight.getFinalRPM() / 6000, limelight.getFinalRPM()),
            new RevIndexer(shooter),
            new WaitCommand(1),
            new TurnOffShooter(shooter),
            new RunIntake(intake, false)
        );
    }

}
