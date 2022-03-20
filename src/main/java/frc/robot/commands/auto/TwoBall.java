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
import frc.robot.commands.RunIntake;
import frc.robot.commands.TurnOffShooter;


public final class TwoBall extends SequentialCommandGroup {

    public TwoBall(Drive drive, Shooter shooter, Intake intake) {

        addCommands(
            new DeployIntake(intake),
            new DriveAuto(drive, 25),
            new WaitCommand(2),
            new RevShooter(shooter, 0.6, 3600),
            new RevIndexer(shooter),
            new WaitCommand(1),
            new RunIntake(intake, true),
            new DriveAuto(drive, 45),
            new RevShooter(shooter, 0.65, 4000),
            new RevIndexer(shooter),
            new WaitCommand(1),
            new TurnOffShooter(shooter),
            new RunIntake(intake, false)
        );
    }

}
