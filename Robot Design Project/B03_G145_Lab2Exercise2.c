#pragma config(Sensor, in1,    motor_angle,    sensorPotentiometer)
#pragma config(Motor,  port1,           motor1,        tmotorVex393_HBridge, openLoop, reversed)
#define DEBUG   1
//*!!Code automatically generated by 'ROBOTC' configuration wizard               !!*//

/**********************************************************
** bool set_motor( int target )
** Runs motor to try and hit target angle
** Inputs:
**   target  - target reading from potentiometer.
** Output:
**    true   - motor has reached desired position
**    false  - motor has not hit target position
**
** Sets motor output.
** Reads motor from port motor_angle
**********************************************************/
bool set_motor( int target )
{
	//int result = target - SensorValue[motor_angle];
  int position = SensorValue(motor_angle);
	int delta = target - position;
	int acc = 12;
	int max = 90;
	if ( delta > max ) {
		delta = max;
	}
	if ( delta < max ) {
		delta = -max;
	}
	if ( delta >= acc || delta <= -acc ) {
		motor[motor1] = delta;
	} else {
		motor[motor1] = 0;
		return(true);
	}
}

task main()
{
	// Target value to move motor towards.
	int target_value = 1500;

	// Time unitl motor hits target value.
	int first_time;
	// Maximum error after first time motor hits target value.
	int max_error;
	// Noted if motor hit target value.
	bool hit_target = false;

	int delta;

	// Reset timer.
	clearTimer(T1);

	// Run for either  seconds or until we get to target value
	while( time1(T1) < 5000 ) {

	  // Implements one iteration of control algorithm.
		set_motor( target_value );

		// If we haven't hit target value yet...
		if ( ! hit_target ) {
			// Check if we encounter target_value again.
			if ( SensorValue[motor_angle] == target_value ) {
				// Note hit target value.
				hit_target = true;

				// Record first time.
				first_time = time1(T1);

				max_error = 0;
			}
		} else {
			delta = SensorValue[motor_angle] - target_value;

			if ( delta < 0 ) {
				delta = -1 * delta;
			}

			if ( delta > max_error ) {
				max_error = delta;
			}

		} // if (! hit_target) else
	}  // match while( time1(T1) < 5000 )

	// Turn off motor
	motor[motor1] = 0;

	// Record results.
	// Record results.
	if ( hit_target ) {
		writeDebugStream("First Encounter: %d\tMax Error: %d\n",first_time,max_error);

		} else {
		writeDebugStream("No convergence.\n");
	}


	// Infinite loop
	while(1);
}
