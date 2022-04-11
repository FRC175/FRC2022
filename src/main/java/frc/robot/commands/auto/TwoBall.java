package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.DeployIntake;
import frc.robot.commands.DriveAuto;
import frc.robot.commands.RevIndexer;
import frc.robot.commands.RevShooter;
import frc.robot.commands.RunIntake;
import frc.robot.commands.TurnOffShooter;


public final class TwoBall extends SequentialCommandGroup {

    public TwoBall(Drive drive, Shooter shooter, Intake intake, Limelight limelight) {

        addCommands(
            new DeployIntake(intake),
            new DriveAuto(drive, 20),
            new WaitCommand(2),
            new RevShooter(shooter, limelight.calculateRPM(limelight.distance()) / 6000, limelight.calculateRPM(limelight.distance())),
            new RevIndexer(shooter),
            new WaitCommand(1),
            new TurnOffShooter(shooter),
            new RunIntake(intake, true),
            new DriveAuto(drive, 45),
            new WaitCommand(1),
            new DriveAuto(drive, -30),
            new RevShooter(shooter, limelight.calculateRPM(limelight.distance()) / 6000, limelight.calculateRPM(limelight.distance())),
            new RevIndexer(shooter),
            new WaitCommand(1),
            new TurnOffShooter(shooter),
            new RunIntake(intake, false)

            // new DeployIntake(intake),
            // new RunIntake(intake, true),
            // new DriveAuto(drive, 70),
            // new WaitCommand(0.5),
            // new RunIntake(intake, false),
            // new RevShooter(shooter, limelight.calculateRPM(limelight.distance()) / 6000, limelight.calculateRPM(limelight.distance())),
            // new RevIndexer(shooter),
            // new RunIntake(intake, true),
            // new WaitCommand(0.2),
            // new TurnOffShooter(shooter),
            // new RunIntake(intake, false)
        );
    }

}
