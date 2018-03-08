package robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Constants;
import robot.commands.TeleopHopperCommand;

public class HopperSubsystem extends Subsystem {

    private Solenoid shooterLoader = new Solenoid(Constants.SHOOTER_LOADER_PNEUMATIC_PORT);
    private DigitalInput frisbeeSensor = new DigitalInput(Constants.HOPPER_FRISBEE_SENSOR_DIO_PORT);

    double lastReleaseTime = 0.0;
    double startTime = 0.0;
    
    HopperState curState = HopperState.RESTING;

    private enum HopperState { RESTING, RELEASING, RETRACTING, FINISHING };

    public HopperSubsystem() {
    	reset();
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new TeleopHopperCommand());
    }

    public void reset() {
        shooterLoader.set(false);
    }

    public boolean hasFrisbee() {
        return !frisbeeSensor.get();
    }

    public void update(boolean requestShot) {        
        double now = Timer.getFPGATimestamp();

        switch (curState) {
            case RESTING:
                if (requestShot) {
                    curState = HopperState.RELEASING;
                }
                break;
            case RELEASING:
                //Fire the pneumatic
                startTime = now;
                shooterLoader.set(true);
                curState = HopperState.RETRACTING;
                break;
            case RETRACTING:
                if (now - startTime > Constants.HOPPER_PNEUMATIC_DELAY) {
                    //When we've waited long enough for the piston to push
                    lastReleaseTime = now;
                    shooterLoader.set(false);
                    curState = HopperState.FINISHING;
                }
                break;
            case FINISHING:
                if (now - lastReleaseTime > Constants.HOPPER_RELEASE_DELAY) {
                    //If we've waited long enough since last shot
                    curState = HopperState.RESTING;
                }
                break;
        }
    }

    public void updatePeriodic() {
        SmartDashboard.putString("Hopper State", curState.toString());
        SmartDashboard.putBoolean("Shooter Loader", shooterLoader.get());
        SmartDashboard.putBoolean("Frisbee Sensor", frisbeeSensor.get());
    }
}
