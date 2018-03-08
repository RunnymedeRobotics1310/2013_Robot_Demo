package robot;

import com.torontocodingcollective.oi.TAxis;
import com.torontocodingcollective.oi.TButton;
import com.torontocodingcollective.oi.TGameController;
import com.torontocodingcollective.oi.TGameController_Logitech;
import com.torontocodingcollective.oi.TStick;
import com.torontocodingcollective.oi.TTrigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public static double STICK_DEAD_ZONE = .07;
	
	TGameController driverController = new TGameController_Logitech(0);
	
    //***************************************************************************************************
    // Driver Controller
    //***************************************************************************************************

	public double getDrive() {
        double axis = driverController.getAxis(TStick.LEFT, TAxis.Y);
        if (Math.abs(axis) < STICK_DEAD_ZONE) {
            axis = 0.0;
        }
        // Square the axis to reduce sensitivity
        return axis * axis * Math.signum(axis);
    }

    public double getRotation() {
    	double axis = driverController.getAxis(TStick.RIGHT, TAxis.X);
        if (Math.abs(axis) < STICK_DEAD_ZONE) {
            axis = 0.0;
        }
        // Square the axis to reduce sensitivity
        return axis * axis * Math.signum(axis);
    }
    
    public boolean getShiftHighGear() {
        return driverController.getButton(TButton.LEFT_BUMPER);
    }

    public boolean getPickupLower() {
        return driverController.getButton(TTrigger.LEFT);
    }

    public boolean getReversePickup() {
        return driverController.getButton(TTrigger.RIGHT);
    }

    public boolean getRequestShot() {
        return driverController.getButton(TButton.RIGHT_BUMPER);
    }

    public boolean getShooterLoad() {
        return driverController.getButton(TButton.X);
    }

    public boolean getShooterHighAngle() {
        return driverController.getButton(TButton.Y);
    }

    public boolean getShooterLowAngle() {
        return driverController.getButton(TButton.B);
    }

	public double getshooterAdjust() {
		int pov = driverController.getPOV();
		if (pov == 0) {
			return 1.0;
		}
		if (pov == 180) {
			return -1.0;
		}
		return 0.0;
	}

}
