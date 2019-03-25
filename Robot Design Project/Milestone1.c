#pragma config(Sensor, dgtl1,  button1,        sensorTouch)
#pragma config(Sensor, dgtl2,  button2,        sensorTouch)
#pragma config(Motor,  port1,           LEFT,          tmotorVex393_HBridge, openLoop)
#pragma config(Motor,  port10,           RIGHT,          tmotorVex393_HBridge, openLoop)
//*!!Code automatically generated by 'ROBOTC' configuration wizard               !!*//

bool button1_pushed; //flag to store button1 input
bool button2_pushed; //flag to store button2 input

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

void ChangeB(int x) {
	motor(LEFT) = x;
	motor(RIGHT) = x;
} //Changes both motors to integer x

enum T_MotorState {
	stop,
	METRE,
	DEG
}; // Three possible states. Stop. Move 1m. Turn 90degrees.

task main()
{
	button1_pushed = false;
	button2_pushed = false;
	T_MotorState MotorState = stop;
	while(true) {
		monitorInput();
		switch(MotorState) {
		case stop:
			ChangeB(0);
			if (button1_pushed) {
				MotorState = METRE;
				button1_pushed = false;
			}
			if (button2_pushed) {
				MotorState = DEG;
				button2_pushed = false;
			}
			break;
		case(METRE):
			motor(RIGHT) = 63;
			motor(LEFT) = -65;
			wait1Msec(1900);
			button1_pushed = false;
			MotorState = stop;
			break;
		case(DEG):
			motor(RIGHT) = 50;
			motor(LEFT) = 50;
			wait1Msec(1150);
			button2_pushed = false;
			MotorState = stop;
		default:
		}
	}
}