package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.OI;
import robot.subsystems.ChassisSubsystem;
import robot.subsystems.HopperSubsystem;
import robot.subsystems.PickupSubsystem;
import robot.subsystems.ShooterSubsystem;

public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static ChassisSubsystem chassisSubsystem = new ChassisSubsystem();
    public static HopperSubsystem hopperSubsystem = new HopperSubsystem();
    public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    public static PickupSubsystem pickupSubsystem = new PickupSubsystem();

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
