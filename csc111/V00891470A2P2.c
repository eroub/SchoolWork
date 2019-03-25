/*
 * Author: Evan Roubekas
 * UVicID: V00891470
 * Date: September 14th, 2017
 * Assignment: A2 Part 2
 * File Name: V00891470A2P2.
 */

#include <stdio.h>
#include <stdlib.h>

/* ArithmeticSequence(start, increment, count)
   Print an arithmetic sequence with the provided properties:
    - start: first value in the sequence
    - increment: the increment value between each pair of values in the sequence
    - count: number of terms to generate
*/
void ArithmeticSequence(int start, int increment, int count){
    printf("Arithmetic sequence: ");
    int y, a;
    a = start;
    for(y=0; y<count; y++){
        printf("%5d", a);
        a = a + increment;
    };
    printf("\n");
}/*ArithmeticSequence*/

/* LeonardoSequence(k, count)
   Print count terms of the Leonardo sequence starting with the kth term
*/void LeonardoSequence(int k, int count){
    printf("LeonardoSequence: ");
    float u, L1, L2, LS;
    if (k == 2) {
        LS = k - 1;
        L1 = 1;
        L2 = 1;
        for (u = 0; u < count; u++) {
            printf("%3.0f ", LS);
            LS = L1 + L2 + 1;
            L1 = L2;
            L2 = LS;
        };
    };
    if (k == 5) {
        LS = 9;
        L1 = 5;
        L2 = 9;
        for (u = 0; u < count; u++) {
            printf("%3.0f ", LS);
            LS = L1 + L2 + 1;
            L1 = L2;
            L2 = LS;
        };
    };
    printf("\n");
}/*LeonardoSequence*/

/* HarmonicSequence(k, count)
   Print count terms of the harmonic sequence starting with the kth term
*/
void HarmonicSequence(float k, int count){
    printf("HarmonicSequence: ");
   int hs;
    for(hs = 0; hs<count; hs++){
        printf("%1.3f ", 1/k);
        k = k + 1;
    };
    printf("\n");
}/*HarmonicSequence*/

/* Do not change any code below this line */
int main(void){
    ArithmeticSequence(17, 6, 5);
    ArithmeticSequence(34, 6, 8);
    LeonardoSequence(2, 7);
    LeonardoSequence(5, 7);
    HarmonicSequence(1, 6);
    HarmonicSequence(3, 6);
    return EXIT_SUCCESS;
}/*main*/