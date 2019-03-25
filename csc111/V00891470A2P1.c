/*
Author: Evan Roubekas
UVicID: V0089170
Date: September 14th, 2017
Assignment: A2 Part 1
File name: V00891470A2P1.c
Description: Celsius to Fahrenheit conversion table
 */

#include <stdio.h>
#include <stdlib.h>

int main(void) {
    const float maxcels = 60.0;
    const float mincels = -40.0;
    float cels = mincels;
    float fahr;
    while (cels <= maxcels) {
        fahr = ((cels * 9) / 5) + 32;
        printf("%6.1f degs C = %6.1f degs F\n", cels, fahr);
        cels = cels + 5;
    } /* while loops */
    return EXIT_SUCCESS;
} /* main */

