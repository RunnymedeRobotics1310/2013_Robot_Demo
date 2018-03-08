/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import robot.commands.CommandBase;
import robot.commands.TeleopDriveCommand;
import robot.commands.TeleopHopperCommand;
import robot.commands.TeleopPickupCommand;
import robot.commands.TeleopShooterCommand;

public class Robot extends IterativeRobot {

    double lastPrintTime = 0;

    public void robotInit() {
        // Initialize all subsystems
        CommandBase.init();
    }

    private void disableSubsystems() {
        CommandBase.shooterSubsystem.disable();
        CommandBase.pickupSubsystem.disable();
    }

    //This function is called at the start of disabled
    public void disabledInit() {
        disableSubsystems();
    }

    //This function is called periodically during disabled
    public void disabledPeriodic() {
        updatePeriodic("Disabled");
    }

    //This function is called at the start of autonomous
    public void autonomousInit() {
    	// No auto in the demo robot
    }

    //This function is called periodically during autonomous
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        updatePeriodic("Autonomous");
    }

    //This function is called at the start of teleop
    public void teleopInit() {
    }

    //This function is called periodically during teleop
    public void teleopPeriodic() {

    	// Run the command before updating the subsystems
    	// which allows any PID values to be set before
    	// updating the motor speeds
        Scheduler.getInstance().run();
        
        updatePeriodic("Teleop");
    }

    //This function is called periodically during test
    public void testPeriodic() {
        LiveWindow.run();
    }

    void updatePeriodic(String mode) {

        CommandBase.chassisSubsystem.updatePeriodic();
    	CommandBase.hopperSubsystem.updatePeriodic();
    	CommandBase.shooterSubsystem.updatePeriodic();
    	CommandBase.pickupSubsystem.updatePeriodic();
    	
    }
}
