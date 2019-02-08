/*
 * three.c
 *
 * Demonstrates the opening up of a file, and writing out
 * individual ASCII values -- first 16 copies of 127 (0x7F)
 * are output, then the values from 0 up to and including
 * 255, and lastly 16 copies of 129 (0x81) are output.
 *
 */
#include <stdio.h>
#include <stdlib.h>   /* defines NULL, amongst other things */


int main(int argc, char *argv[]) {
    FILE *outfile;
    int i;

    if (argc < 2) {
        fprintf(stderr, "usage: ./three <filename>\n");
        exit(1);
    }

    outfile = fopen(argv[1], "w");
    if (outfile == NULL) {
        fprintf(stderr, "Problems opening %s for output\n",
            argv[1]);
        exit(1);
    }

    for (i = 0; i < 16; i++) {
        fputc(127, outfile);
    }

    for (i = 0; i < 256; i++) {
        fputc(i, outfile);
    }

    for (i = 0; i < 16; i++) {
        fputc(129, outfile);
    }

    fclose(outfile);
}
