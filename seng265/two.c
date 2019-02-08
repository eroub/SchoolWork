/*
 * two.c
 *
 * Demonstrating the use of tokenize, and why simply assigning
 * a "char *" value to some variable is *not* the same thing
 * as copying a string!
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>  /* Defines NULL, amongst other things */

#define MAX_LINE_LEN 128
#define MAX_CHAR_POINTERS 4


int main(int argc, char *argv[]) {
    char input[MAX_LINE_LEN];
    int  num;
    int  i;
    char *t;

    /* Note: The array below is going to be
     * used in a way that will *not* accomplish
     * what a newbie / naive C programmer may
     * be trying to accomplish.
     */
    char *word[MAX_CHAR_POINTERS];
    for (i = 0; i < MAX_CHAR_POINTERS; i++) {
        word[i] = NULL;
    }
    num = 0;

    printf("Sentence? ");
    while (fgets(input, MAX_LINE_LEN, stdin)) {
        /* Get rid of trailing newline */
        input[strlen(input)-1] = '\0';

        t = strtok(input, " ");
        while (t != NULL) {
            printf("[%s]\n", t);
            if (num < MAX_CHAR_POINTERS) {
                word[num] = t;
                num++;
            }
            t = strtok(NULL, " ");
        }
    }

    printf("Done. Here is what is in the 'word[]' array.\n");
    for (i = 0; i < num; i++) {
        printf("%s\n", word[i]);
    }
}

/* Can you explain why the output after "Done" is often incorrect
 * when we input two or more lines of text?
 */
