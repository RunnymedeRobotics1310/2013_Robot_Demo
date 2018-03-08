package robot.subsystems;

import com.torontocodingcollective.pid.TSpeedPID;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Constants;
import robot.commands.TeleopShooterCommand;

public class ShooterSubsystem extends Subsystem {

    Victor vicShooter = new Victor(Constants.SHOOTER_MOTOR_PWM_CHANNEL);

    Counter encShooter = new Counter(Constants.SHOOTER_ENCODER_DIO_PORT);
    
    TSpeedPID pidShooter = new TSpeedPID(.05);
    //ParsablePIDController pidShooter = new ParsablePIDController("pidshooter", 0.05, 0.0, 0.0, 0.0059, encShooter, vicShooter); //0.0048

    //Pneumatics are initialized in CommandBase.java
    private Solenoid shooterLifterPneumatic = new Solenoid(Constants.SHOOTER_LIFTER_PNEUMATIC_PORT);

    ShooterState shooterState = ShooterState.LOAD;
    double setpoint = 0.0;
    double percentTolerance = Constants.SHOOTER_SPEED_PERCENT_TOLERANCE;

    public enum ShooterState { LOAD, LOW, HIGH };

    public ShooterSubsystem() {
    	disable();
    }
    
    public void disable() {
    	pidShooter.disable();
    }
    
    public void disablePID() {
        pidShooter.disable();
    }

    public void enablePID() {
        pidShooter.enable();
    }
    
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopShooterCommand());
    }
    
    public void setPercentTolerance(double percentTolerance) {
        this.percentTolerance = percentTolerance;
    }
    
    public void setSetpoint(double setpoint) {

    	this.setpoint = setpoint;
    	
    	double normalizedSetpoint = setpoint / Constants.MAX_SHOOTER_ENCODER_RATE;
    	
    	// Enable the pids if required
    	if (!pidShooter.isEnabled() && setpoint > 0) {
    		pidShooter.setSetpoint(normalizedSetpoint);
    		pidShooter.enable();
		}
    	
    	if (setpoint > 0) {
    		pidShooter.setSetpoint(normalizedSetpoint);
    	}
    	else {
    		pidShooter.disable();
    	}
    }

    public void setShooterState(ShooterState state) {
        shooterState = state;
    }

    public ShooterState getShooterState() {
        return shooterState;
    }

    public boolean onTarget() {
        return Math.abs((encShooter.getRate() - setpoint)/setpoint) <= percentTolerance;
    }

    public boolean aboveShootThreshold() {
        return encShooter.pidGet() > Constants.MAX_SHOOTER_ENCODER_RATE * Constants.SHOOTER_MIN_SHOOT_THRESHOLD;
    }
    
    public void updatePeriodic() {
    	
    	// Update all PIDs
    	if (pidShooter.isEnabled()) {
        	pidShooter.calculate(encShooter.getRate() / Constants.MAX_SHOOTER_ENCODER_RATE);
        	vicShooter.set(pidShooter.get());
    	}
    	else {
    		vicShooter.set(0);
    	}

    	// Update the shooter based on the state
        switch (shooterState) {
        case LOAD:
            shooterLifterPneumatic.set(false);
            break;
        case LOW:
            shooterLifterPneumatic.set(false);
            break;
        case HIGH:
            shooterLifterPneumatic.set(true);
            break;
        }
        
        SmartDashboard.putString("Shooter Setpoint", setpoint + "(" + pidShooter.getSetpoint() + ")");
        SmartDashboard.putNumber("Shooter Motor", vicShooter.get());
        SmartDashboard.putNumber("Shooter Speed", encShooter.getRate());
        SmartDashboard.putBoolean("Shooter PID ontarget", onTarget());
        
        SmartDashboard.putString("Shooter State", shooterState.toString());
        SmartDashboard.putBoolean("ShooterLifterPneumatic",  shooterLifterPneumatic.get());
    }
}
