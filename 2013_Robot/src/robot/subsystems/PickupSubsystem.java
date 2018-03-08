package robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Constants;
import robot.commands.TeleopPickupCommand;

public class PickupSubsystem extends Subsystem {

    Victor pickupRoller = new Victor(Constants.PICKUP_ROLLER_MOTOR_PWM_CHANNEL);
    Victor elevatorRoller = new Victor(Constants.ELEVATOR_ROLLER_MOTOR_PWM_CHANNEL);

    DigitalInput pickupFrisbeeSensor = new DigitalInput(Constants.PICKUP_FRISBEE_SENSOR_DIO_PORT);

    //Pneumatics are initialized in CommandBase.java
    private Solenoid pickupPneumatic = new Solenoid(Constants.PICKUP_PNEUMATIC_PORT);

    boolean lastFrisbeeSensor = false;
    double lastFrisbeeSensorTime = 0.0;
    double lastNoFrisbeeSensorTime = 0.0;
    boolean disablePickupRoller = false;

    public PickupSubsystem() {
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopPickupCommand());
        pickupPneumatic.set(false); //Raise the pickup
    }

    public void disable() {
    }

    public void setPneumatic(boolean value) {        

    	double now = Timer.getFPGATimestamp();
        if (!value) {
            //If we're trying to raise the pickup
            if (now - lastFrisbeeSensorTime > Constants.PICKUP_DELAY_AFTER_FRISBEE) {
                //If we've waited enough time since the last time we saw a frisbee
                pickupPneumatic.set(false); //Raise the pickup
            }
        } else {
            pickupPneumatic.set(true); //Lower the pickup
        }

        //There is a frisbee in the pickup when frisbeeSensor.get() is false
        lastFrisbeeSensorTime = !pickupFrisbeeSensor.get() ? now : lastFrisbeeSensorTime;
        //The amount of time since our pickup was clear
        lastNoFrisbeeSensorTime = pickupFrisbeeSensor.get() ? now : lastNoFrisbeeSensorTime;
    }

    public void runRoller(boolean value, boolean reverse) {
        double pickupSpeed = 0.0;
        double elevatorSpeed = 0.0;
        double now = Timer.getFPGATimestamp();
        if (pickupDown() && (value || now - lastFrisbeeSensorTime < Constants.PICKUP_DELAY_AFTER_FRISBEE)) {
            //Keep running the roller if we havent waited long enough since the last frisbee
            pickupSpeed = Constants.PICKUP_SPEED;
            elevatorSpeed = Constants.ELEVATOR_SPEED;
        }
        
        //THIS CODE WAS JUST ADDED, MUST TEST
        //DONT DOWNLOAD UNLESS SUFFICIENT TIME TO TEST
        //If we've seen a frisbee for more than this amount of time, reverse the roller
        /*if(pickupDown() && now - lastNoFrisbeeSensorTime > Constants.PICKUP_FRISBEE_JAM_TIME.get()) {
            //Run the roller in reverse
            pickupSpeed = Constants.PICKUP_SPEED.get();
            reverse = true;
        }*/

        if (!disablePickupRoller) {
            pickupRoller.set(reverse ? -pickupSpeed : pickupSpeed);
        } else {
            pickupRoller.set(0);
        }
        elevatorRoller.set(elevatorSpeed);//reverse ? -elevatorSpeed : elevatorSpeed);
    }
    
    public void setDisablePickupRoller(boolean value) {
        disablePickupRoller = value;
    }

    public boolean pickupDown() {
        return pickupPneumatic.get();
    }

    public void updatePeriodic() {
    	
        System.out.println("pickupRoller: " + pickupRoller.get() + " pickupPneumatic: " + pickupPneumatic.get());
        System.out.println("elevatorRoller: " + elevatorRoller.get());
        
        SmartDashboard.putBoolean("Pickup Frisbee Sensor", pickupFrisbeeSensor.get());
    }
}
