// GROUP MEMBERS: Evan Roubekas, Alex Hagarty, Leif Berg
// LAB SECTION: B03
// GROUP NUMBER : T10


#pragma config(I2C_Usage, I2C1, i2cSensors)
#pragma config(Sensor, dgtl1,  button1,        sensorTouch)
#pragma config(Sensor, dgtl2,  button2,        sensorTouch)
#pragma config(Sensor, I2C_1,  ,               sensorQuadEncoderOnI2CPort,    , AutoAssign )
#pragma config(Motor,  port1,           motor1,        tmotorVex393_HBridge, openLoop, encoderPort, I2C_1)
//*!!Code automatically generated by 'ROBOTC' configuration wizard               !!*//

// WHEN YOU FINISH EACH EXERCISE, INCREMENT THIS VALUE
#define EXERCISE_NUMBER 	3

bool button1_pushed; //flag to store button1 input
bool button2_pushed; //flag to store button2 input


/* monitorInput()
*
*  Used to flag button inputs
*       - this avoids errors caused by program recognizing input, taking action, and
*         reading input again before button is released
*/
void monitorInput()
{
	if(SensorValue(button1) && !button1_pushed)
	{
		button1_pushed = true;
	}

	if(SensorValue(button2) && !button2_pushed)
	{
		button2_pushed = true;
	}
}



void exercise_1()
{
	enum T_exercise1_state {
		MOTOR_STOP = 0,
		MOTOR_RUNNING
	};

	T_exercise1_state exercise1_state = MOTOR_STOP;

	while(true)
	{
		// This function updates the button1_pushed and button2_pushed flags.
		monitorInput();

		// Switch the states.
		switch(exercise1_state) {

			// Code for MOTOR_STOP state:
		case MOTOR_STOP:
			// Turn motor off.
			motor[motor1] = 0;
			if ( button1_pushed ) {
				// If button1 pushed, change to the MOTOR_RUNNING state.
				exercise1_state = MOTOR_RUNNING;
				// Clear flag to indicate button 1 processed.
				button1_pushed = false;
			}
			break;
		case MOTOR_RUNNING:
			// Turn motor on.
			motor[motor1] = 50;
			if (button2_pushed) {
				// If button 2 pushed, transition to MOTOR_STOP state.
				exercise1_state = MOTOR_STOP;
				// Clear flag to indicate that button 2 processed.
				button2_pushed = false;
			}
			break;
		default:
		}

	}//end while
}



void exercise_2()
{
	enum T_exercise2_state {
		MOTOR_STOP = 0,
		MOTOR_RUNNING
	};

	T_exercise2_state exercise2_state = MOTOR_STOP;

	while(true)
	{
		// Monitor buttons
		monitorInput();

		switch(exercise2_state) {
		case MOTOR_STOP:
			// MOTOR_STOP state, turn motor off.
			motor[motor1] = 0;
			if ( button1_pushed ) {
				// If button 1 pushed, switch to MOTOR_RUNNING state.
				exercise2_state = MOTOR_RUNNING;
				// Reset encoder to get proper count.
				resetMotorEncoder(motor1);

			}
			break;
		case MOTOR_RUNNING:
			// MOTOR_RUNNING state, turn motor on.
			motor[motor1] = 50;

			if ( getMotorEncoder(motor1) > 627 ) {
				// If we have passed 627 ticks, transition to MOTOR_STOP state.
				exercise2_state = MOTOR_STOP;

				// Clear the button1_pushed flag. This stops double rotations.
				button1_pushed = false;
			}
			break;
		default:
		}  // end switch(exercise2_state)
	}  // end while(1)
} // end exercise_2



void exercise_3()
{
enum T_exercise3_state {
		MOTOR_STOP = 0,
		MOTOR_FORWARDS,
		MOTOR_BACKWARDS
	};
	T_exercise3_state exercise3_state = MOTOR_STOP;
		while(true)
	{
		monitorInput();
		switch(exercise3_state) {

		case MOTOR_STOP:
			motor[motor1] = 0;
			if ( button1_pushed ) {
				exercise3_state = MOTOR_FORWARDS;
				resetMotorEncoder(motor1);
			}
			if ( button2_pushed ) {
			  exercise3_state = MOTOR_BACKWARDS;
			  resetMotorEncoder(motor1);
			} break;

		case MOTOR_FORWARDS:
			motor[motor1] = 50;
			if ( getMotorEncoder(motor1) > 3000 ) {
				exercise3_state = MOTOR_STOP;
				button1_pushed = false;
		} break;

		case MOTOR_BACKWARDS:
		  motor[motor1] = -50;
		  if ( getMotorEncoder(motor1) < -3000 ) {
				exercise3_state = MOTOR_STOP;
				button2_pushed = false;
	  } break;

		default:
		}

	}//end while

}  // end exercise 3

task main()
{
	button1_pushed = button2_pushed = false;

	switch (EXERCISE_NUMBER)
	{
	case 1:
		exercise_1();
		break;
	case 2:
		exercise_2();
		break;
	case 3:
		exercise_3();
		break;
	default: //should never get here.
	} // end switch
}