package robot.commands;

import robot.Constants;
import robot.subsystems.ShooterSubsystem;
import robot.subsystems.ShooterSubsystem.ShooterState;

public class TeleopShooterCommand extends CommandBase {

	double shooterSpeedSetpoint = 0;
	
    public TeleopShooterCommand() {
        requires(shooterSubsystem);
    }

    protected void initialize() {
    }

    protected void execute() {

    	
    	//Only run the shooter if the pickup is down
        if (pickupSubsystem.pickupDown()) {
        	
	    	// Check for a button pressed to reset the shooter speed
	        if (chassisSubsystem.isHighGearEnabled() || oi.getShooterLoad()) {
	            shooterSubsystem.setShooterState(ShooterState.LOAD);
	            shooterSpeedSetpoint = Constants.SHOOTER_LOW_ANGLE_SPEED_SETPOINT;

	        } else if (oi.getShooterLowAngle()) {
	            shooterSubsystem.setShooterState(ShooterState.LOW);
	        
	        } else if (oi.getShooterHighAngle()) {
	            shooterSubsystem.setShooterState(ShooterState.HIGH);
	        }

	        // Adjust the shooter speed with the POV
	        shooterSpeedSetpoint += oi.getshooterAdjust();

	        shooterSubsystem.setSetpoint(shooterSpeedSetpoint);

        } else {
            shooterSubsystem.disable();
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
