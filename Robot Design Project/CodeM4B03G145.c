#pragma config(Sensor, in1,    IRsensor1,      sensorReflection)
#pragma config(Sensor, dgtl1,  button1,        sensorTouch)
#pragma config(Sensor, dgtl2,  button2,        sensorTouch)
#pragma config(Sensor, dgtl3,  sonar,          sensorSONAR_cm)
#pragma config(Sensor, dgtl7,  Complete,       sensorDigitalOut)
#pragma config(Sensor, dgtl9,  TEST,           sensorDigitalOut)
#pragma config(Sensor, dgtl12, StateLED,       sensorDigitalOut)
#pragma config(Motor,  port1,           LEFT,          tmotorVex393_HBridge, openLoop)
#pragma config(Motor,  port2,           MECHL,         tmotorVex393_MC29, openLoop)
#pragma config(Motor,  port3,           MECHR,         tmotorVex393_MC29, openLoop)
#pragma config(Motor,  port10,          RIGHT,         tmotorVex393_HBridge, openLoop)
//*!!Code automatically generated by 'ROBOTC' configuration wizard               !!*//

bool button1_pushed; //flag to store button1 input
bool button2_pushed; //flag to store button2 input
int connection; // Connection variable that changes when the connection has occured
int counter = 0; // Used when the robot is detecting the beacon a second time

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
// ^ ABOVE LINES FROM LAB 1 CODE

typedef enum T_State {
	STARTUPnDETECT = 0,
	REFIND,
	CONNECT,
	DRIVE
}; // Four possible robot states


void ChangeB(int x) {
	motor(LEFT) = x;
	motor(RIGHT) = x;
} // Changes the values of both motors

bool monitorLight(int light_threshold)
{
	static int minLevelIR1 = 4096;	// Minimum light level seen by IR sensor 1
	static int maxLevelIR1 = 0;			// Maximum light level seen by IR sensor 1
	static int diffLevelIR1 = 0;		// Delta between maximum and minimum seen in last 0.1 seconds

	int lightLevel1 = SensorValue[IRsensor1];
	bool returnValue;

	// Check if 100 msecs have elapsed.
	if ( time1[T1] > 100 )  {

		// 100 msecs have elapsed.  Compute delta of light level.
		diffLevelIR1 = maxLevelIR1 - minLevelIR1;

		// Reset calculation for next 100 msecs.
		maxLevelIR1 = 0;
		minLevelIR1 = 4096;
		clearTimer(T1);

		} else {

		// Check for new minimum/maximum light levels.
		if ( lightLevel1 < minLevelIR1 ) {
			minLevelIR1 = lightLevel1;
			} else if ( lightLevel1 > maxLevelIR1 ) {
			maxLevelIR1 = lightLevel1;
		}
	}
	// Check if light level difference over threshold.
	if ( diffLevelIR1 > light_threshold ) {
		returnValue = true;
		SensorValue[StateLED] = 1;
		} else {
		returnValue = false;
		SensorValue[StateLED] = 0;
	}

	return(returnValue);
} // Function given by TA's

void MechNFin() {
	if(connection == 0) { // <- Mechanism can only occur once
		wait1Msec(500);
		connection = 1;
		clearTimer(T1);
		while(time1[T1] < 675) {
			motor[LEFT] = -26;
			motor[RIGHT] = 28;
		}
		clearTimer(T1);
		wait1Msec(400);
		while(time1[T1] < 1000){
			motor[MECHL] = -15;
			motor[MECHR] = -15;
		}
		clearTimer(T1);
		while(time1[T1] < 2000) {
			motor[LEFT] = 26;
			motor[RIGHT] = -28;
		}
		ChangeB(0);
		motor[MECHL] = 0;
		motor[MECHR] = 0;
		SensorValue[Complete] = 1;
	} // MechnFin executes the series of motor functions that work the mechanism as well as marks completion of the task
}

task main()
{
	T_State robot_state = STARTUPnDETECT;
	while(true && connection == 0) { // While loop stops being true once connection has occured. Terminating program
		monitorInput();
		connection = 0;
		switch(robot_state) {
		case STARTUPnDETECT:
			SensorValue[StateLED] = 0;
			SensorValue[TEST] = 0; // Debugging LED's
			if(button1_pushed || button2_pushed){ // Program executes when a button is pushed
				if(counter == 0) {
					if(monitorLight(1060)) {
						ChangeB(0);
						robot_state = DRIVE;
						} else {
						ChangeB(-33);
						robot_state = STARTUPnDETECT;
					} // Robot continuously spins until it detects the beacon. NOTE: This threshold is only used once, for the first detection
				} // Once beacon is detected the robot stops moving and goes to DRIVE case
				if (counter == 1) {
					if(monitorLight(1095)) {
						ChangeB(0);
						SensorValue[TEST] = 1;
						if(SensorValue[sonar] >= 35) {
							robot_state = DRIVE;
							} else {
							robot_state = CONNECT;
							break; // If the beacon is detected and within 35 centimeters, go to CONNECT case
							}
						} else {
						ChangeB(-33);
						SensorValue[StateLED] = 1;
						robot_state = STARTUPnDETECT;
					} // Counter now equals 1 so the second threshold is used to detect the beacon a second time
				} // Robot spins until the beacon is detected with the new threshold
			}
			break;
		case DRIVE:
			clearTimer(T1);
			SensorValue[StateLED] = 0;
			wait1Msec(300);
			while(SensorValue[sonar] >= 35){
				if(connection == 0) {
					motor[LEFT] = -41;
					motor[RIGHT] = 43;
				} // While the robot is not within 35 centimeters of anything, drive forward at a steady pace. This cannot occur once the connection has happened
				if(SensorValue[sonar] <= 35){
					if(connection == 0) {
						ChangeB(0);
						wait1Msec(1000);
						robot_state = REFIND;
					} // If the connection has not occured and the robot is within 35 centimeters go to REFIND case
				}
			}
			break;
		case REFIND:
			ChangeB(-33);
			wait1Msec(1500);
			ChangeB(0);
			counter = 1;
			SensorValue[StateLED] = 0;
			robot_state =STARTUPnDETECT;
			break;
			// The refind case purposely throws the robot off the beacon by spinning it for one and a half seconds
			// After it has been thrown off the counter increases so that the second threshold can be used when the program returns to the STARTUPnDETECT state
		case CONNECT:
			MechNFin();
			// CONNECT case executes the mechanism function which also contains the signal for completion
			break;
		default:
			// This should never happen.
			robot_state = STARTUPnDETECT;

		} // switch( robot_state)
	}  // while(true)
}
