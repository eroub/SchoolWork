/*
 * Author: Evan Roubekas
 * UVicID: V00891470
 * Date: September 14th, 2017
 * Assignment: A2 Part 3
 * File Name: V00891470A2P3.
 */

#include <stdio.h>
#include <stdlib.h>

int main(void) {
    int count, u;
    count = 11;
    double x, y;
    x = 1.0;
    printf("Computing square roots using Newton's method\n");
    for(u = 1; u < count; u++){
        printf("Iteration %d: %14.10f\n", u, x);
        y = x - (( (x * x) - 117) / (2 * x));
        x = y;
    }
    printf("The square root of 117.00 with 10 iterations is %14.10f\n", x);
    printf("The square of %14.10f is %.20f", x, (x * x));
    return EXIT_SUCCESS;
}
/* NOTE: When I calculate (10.8166738264)^2 = 1.421085x10^-14 is returned. I have tried to get  rid of the
 * extra 0.421085x10^-14 but I have not found a solution and cannot figure out why it gets returned.*/