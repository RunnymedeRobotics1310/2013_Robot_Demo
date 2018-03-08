package robot.commands;

public class TeleopDriveCommand extends CommandBase {

    int precisionRotationIterations = 0;
    
    public TeleopDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(chassisSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	// Set the gear shifter
    	chassisSubsystem.shifter.set(oi.getShiftHighGear());

    	// Set the robot speed and turn
    	chassisSubsystem.drive(oi.getDrive(), oi.getRotation());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
