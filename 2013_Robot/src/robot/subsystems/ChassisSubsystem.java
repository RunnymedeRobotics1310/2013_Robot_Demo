package robot.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Constants;
import robot.Pneumatic;
import robot.commands.TeleopDriveCommand;

public class ChassisSubsystem extends Subsystem {

    Victor vicLeft = new Victor(Constants.LEFT_MOTOR_PWM_CHANNEL);
    Victor vicRight = new Victor(Constants.RIGHT_MOTOR_PWM_CHANNEL);
    
    Pneumatic shifterPneumatic; //Pneumatics are initialized in CommandBase.java

    
    public Solenoid shifter = new Solenoid(Constants.SHIFTER_PNEUMATIC_PORT); //Pneumatics are initialized in CommandBase.java
    
    RobotDrive robotDrive = new RobotDrive(vicLeft, vicRight);

    public ChassisSubsystem() {

    }

    public void initDefaultCommand() {
        setDefaultCommand(new TeleopDriveCommand());
    }

    public void drive(double speed, double rotation) {
        robotDrive.arcadeDrive(speed, -rotation);
    }

    /**
     * Set shifter gear
     * @param gear {@code true} = high gear, {@code false} = low gear
     */
    public void shift(boolean gear) {
    	shifter.set(gear);
    }

    public boolean isHighGearEnabled() {
    	return shifter.get();
    }
    
    public void updatePeriodic() {
    	SmartDashboard.putNumber("Left Motor", vicLeft.get());
    	SmartDashboard.putNumber("Right Motor", vicRight.get());
    	SmartDashboard.putBoolean("High Gear", shifter.get());
   	}

}
