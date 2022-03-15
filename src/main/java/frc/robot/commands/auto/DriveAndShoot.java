package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Shoot;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ColorSensor;


public final class DriveAndShoot extends SequentialCommandGroup {

    public DriveAndShoot(Drive drive, Shooter shoot, Limelight limelight, ColorSensor colorSensor, String hubToScore) {
        addCommands(
                new Shoot(shoot, limelight, colorSensor, hubToScore, true),
                new WaitCommand(0.5),
                new Shoot(shoot, limelight, colorSensor, hubToScore, false),
                new DriveTarmac(drive)
        );
    }

}
