package robot.commands;

public class TeleopHopperCommand extends CommandBase {

    double lastOffTargetTime = 0;

    public TeleopHopperCommand() {
        requires(hopperSubsystem);
    }

    protected void initialize() {
        hopperSubsystem.reset();
    }

    protected void execute() {
    	
        boolean shooterRunning = shooterSubsystem.aboveShootThreshold();

        //Shoot if requested
        hopperSubsystem.update(oi.getRequestShot() && shooterRunning);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
