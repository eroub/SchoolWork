/*
 * one.c
 *
 * Demonstrating how input text from keyboard can be
 * stored in a 2D-array of char.
 */

#include <stdio.h>
#include <string.h>

#define MAX_WORDS 10
#define MAX_WORD_LEN 20

char words[MAX_WORDS][MAX_WORD_LEN];
int  num_words;


void init() {
    int i;

    for (i = 0; i < MAX_WORDS; i++) {
        words[i][0] = '\0';
    }

    num_words = 0;
}


int find(char *w) {
    int i;

    for (i = 0; i < num_words; i++) {
        if (strcmp(words[i], w) == 0) {
            return i;
        }
    }

    if (i < MAX_WORDS) {
        strncpy(words[i], w, MAX_WORD_LEN);
        num_words++;
        return i;
    }

    return -1;
}


void finish() {
    int i;

    for (i = 0; i < num_words; i++) {
        printf("%02d [%s]\n", i, words[i]);
    }
}

int main(int argc, char *argv[]) {
    char input[MAX_WORD_LEN];
    int pos;

    init();
   
    printf("Word? ");
    while (fgets(input, MAX_WORD_LEN, stdin)) {
        /* Get rid of trailing newline */
        input[strlen(input)-1] = '\0';
       
        /* Call find() with the input */
        pos = find(input); 
        printf("%d\n", pos);
    }

    printf("done\n\n");

    finish();
}
