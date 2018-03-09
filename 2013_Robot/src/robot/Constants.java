package robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class Constants {

    //Hopper subsystem
    public static double HOPPER_PNEUMATIC_DELAY = 0.1;
    public static double HOPPER_RELEASE_DELAY   = 0.2;
    
    //Pickup subsystem
    public static final double PICKUP_SPEED = -0.8;
    public static final double ELEVATOR_SPEED = -0.8;
    public static final double PICKUP_DELAY_AFTER_FRISBEE = 1.0;
    public static final double PICKUP_FRISBEE_JAM_TIME = 5.0;
    
    //Shooter subsystem
    public static final double MAX_SHOOTER_ENCODER_RATE = 170; //RPS
    public static final double SHOOTER_MIN_SHOOT_THRESHOLD = 0.3;
    public static final double SHOOTER_SPEED_PERCENT_TOLERANCE = .95;
    public static final double SHOOTER_LOW_ANGLE_SPEED_SETPOINT = 147;
    public static final double SHOOTER_HIGH_ANGLE_SPEED_SETPOINT = 135;
    
    //PWM outputs
    public static final int LEFT_MOTOR_PWM_CHANNEL            = 1;
    public static final int RIGHT_MOTOR_PWM_CHANNEL           = 0;
    public static final int PICKUP_ROLLER_MOTOR_PWM_CHANNEL   = 3;
    public static final int SHOOTER_MOTOR_PWM_CHANNEL         = 2;
    public static final int ELEVATOR_ROLLER_MOTOR_PWM_CHANNEL = 4;
    
    //Pneumatic Solenoid outputs
    public static final int SHIFTER_PNEUMATIC_PORT        	= 0;
    public static final int SHOOTER_LOADER_PNEUMATIC_PORT 	= 2;
    public static final int PICKUP_PNEUMATIC_PORT         	= 4;
    public static final int SHOOTER_LIFTER_PNEUMATIC_PORT 	= 6;
    
    //Digital inputs
    public static final int SHOOTER_ENCODER_DIO_PORT       = 5;
    public static final int HOPPER_FRISBEE_SENSOR_DIO_PORT = 6;
    public static final int PICKUP_FRISBEE_SENSOR_DIO_PORT = 7;
}
