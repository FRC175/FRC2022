package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.RevIndexer;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ColorSensor;


public final class ShootDelay extends SequentialCommandGroup {

    public ShootDelay(Shooter shoot, Limelight limelight, ColorSensor colorSensor, String hubToScore) {
        // addCommands(
        //         new Shoot(shoot, limelight, colorSensor, hubToScore, true),
        //         // new RunCommand(() -> shoot.shooterSetOpenLoop(.5), shoot),
        //         new WaitCommand(2),
        //         new RunCommand(() -> shoot.indexerSetOpenLoop(1), shoot),
        //         new WaitCommand(1),
        //         new Shoot(shoot, limelight, colorSensor, hubToScore, false)
        // );
    }

}
