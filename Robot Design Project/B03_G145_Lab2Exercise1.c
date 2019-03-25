#define DEBUG   1
// Basic simulate imprecise measurement code in RobotC
int min_value = 0;
int max_value = 100;

// Experiment parameters
int total_trials = 1000;

// Random number storage.
int true_value;
int total_meas = 0;

/**********************************************************
** void generate_new_value
** Generates new true_value
**********************************************************/
void generate_new_value()
{
	// Random number parameter

	int temp_number = rand();

	int range = max_value - min_value;

	// Kludge to handle fact that RobotC does not have unsigned ints.
	temp_number = ( temp_number < 0 ) ? -1 * temp_number : temp_number;

	true_value = temp_number % range + min_value;
}

/**********************************************************
** int resolve_measurement( int meas_point )
** Resolve Measurement at set point.
** Inputs:
**   meas_point	Set point of measurment
** Outputs:
**   0						If true value is equal to meas_point
**   -1          If true value is greater then meas_point
**   1           If true value is less than meas_pont
** Global Interaction:
**   Keeps track of number of measurements in total_meas
**
**********************************************************/
int resolve_measurement( int meas_point ) {

	// Increment total number of guesses.
	total_meas++;

	if ( meas_point > true_value ) {
		// Return 1 if measurement point is too large.
		return(1);
		} else if ( meas_point < true_value ) {
		// Return -1 if measurment point is too small.
		return(-1);
		} else {
		// Return 0 if measurement point matches
		return(0);
	}
}

task main()
{
	// Run multiple trials
	int current_trial;

	// Range to search for true value
	int min_point = min_value;
	int max_point = max_value;
	// For each trial
	for ( current_trial = 0; current_trial < total_trials ; current_trial++ ) {

		// Generate new value to measure
		generate_new_value();
		min_point = min_value;
		max_point = max_value;
		// Modify the code starting here.

		// Current measurement point set to lowest value.
		int a = (min_point + max_point) / 2;
		int VAL = resolve_measurement(a);
		// Initialization here.

		// Estimation code here.
		// Sweep through range from lowest to highest possible value.
		while (VAL != 0) {
			 if (VAL > 0) {
				max_point = a - 1;
				a = (min_point + max_point) / 2;
			}
			 if (VAL < 0) {
				min_point = a + 1;
				a = (min_point + max_point) / 2;
			}
			VAL = resolve_measurement(a);
		}
		min_point = min_value;
		max_point = max_value;
		// Stop when measurment_point matches true value.
		VAL = resolve_measurement(a);
		// All modified code should be above this line.

		// Write output to debug stream.
		// Keep this line for evaluation of performance.
		writeDebugStream("%d trials and %d guesses\n",current_trial,total_meas);

	}

	// Wait for an infinite amount of time.
	// Keeps the Debug stream open so we can read the output of the program.
	while(1);
}
