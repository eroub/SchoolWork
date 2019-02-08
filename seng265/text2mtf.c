#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_WORD 120
#define MAX_LENGTH 20
#define BUFLEN 10000
int num_words, num_lines, num_dic_words, count;
char word_list[BUFLEN][BUFLEN], basis[BUFLEN][BUFLEN], final[BUFLEN][BUFLEN], line[BUFLEN][BUFLEN];
int bascount;

void insert_front(char arr[1]){
	int k;
	if(count >= 1){
		for(k=count; k>0; k--) strncpy(final[k], final[k-1], MAX_LENGTH);
		strncpy(final[0], arr, MAX_LENGTH);
	} else {
		strncpy(final[count], word_list[count], MAX_LENGTH);
	}
}

void move_to_front(int index){
	int k;
	char temp[1][MAX_LENGTH];
	strncpy(temp[0], final[index], MAX_LENGTH);
	for(k=index; k>0; k--) strncpy(final[k], final[k-1], MAX_LENGTH);
	strncpy(final[0], temp[0], MAX_LENGTH);
}

void mtf(FILE *outfile){
	count = 0;
	int printed_lines = 0;
	int i;
	for(i=0; i<num_words; i++){
		if(strncmp(basis[i], word_list[count], MAX_LENGTH) == 0){
			fputc(0x81+count, outfile);
			fprintf(outfile, "%s", basis[i]);
			insert_front(word_list[count++]);
			fprintf(outfile, "%s", line[printed_lines++]);
		} else {
			int j;
			for(j=0; j<count; j++){
				if(strncmp(basis[i], final[j], MAX_LENGTH) == 0){
			 		fputc(0x81+j, outfile);
					fprintf(outfile, "%s", line[printed_lines++]);
					move_to_front(j);
				}
			}
		}
		while(strncmp(line[printed_lines], "\n", MAX_LENGTH) == 0) fprintf(outfile, "%s", line[printed_lines++]);
	}
}

void dictionary(){
	int i;
	num_dic_words = 0;
	for(i=0; i<num_words; i++){
		if(strncmp(basis[i], "\n", MAX_LENGTH) != 0);{
			int j;
			int count = 0;
			for(j=0; j<=i; j++) if(strcmp(basis[i], word_list[j]) == 0) count++;
			if(count == 0) strncpy(word_list[num_dic_words++], basis[i], MAX_LENGTH);
		}
	}
}

void tokenize(char *input_line){
	char *token;
	token = strtok(input_line, " ");
	while(token && num_words < BUFLEN){
		strncpy(basis[num_words++], token, MAX_LENGTH);
		strncpy(line[num_lines++], "\0", MAX_LENGTH);
		token = strtok(NULL, " ");
		bascount++;
	}
}

void magic_numbers(FILE *outfile){
	fputc(0xba, outfile); fputc(0x5e, outfile);
	fputc(0xba, outfile); fputc(0x11, outfile);
}

int main(int argc, char *argv[]){
	char *in_file_name = argv[1];
	char out_file_name[strlen(in_file_name)+1];
	FILE *infile; FILE *outfile;
	num_lines = 0;

	/* Check to see if file arguement exists */
	if(argc < 2){
		fprintf(stderr, "usage: ./text2mtf <filename>\n");
		exit(1);
	}
	/* Open input file & check if it exists */
	infile = fopen(in_file_name, "r");
	if(infile == NULL){
		fprintf(stderr, "Error opening %s for input\n", in_file_name);
		exit(1);
	}
	/* Create output file */
	strncpy(out_file_name, in_file_name, strlen(in_file_name)-4);
	strncat(out_file_name, ".mtf", 5);
	outfile = fopen(out_file_name, "w");
	/* Insert magic numbers :) */
	magic_numbers(outfile);
	/* Tokenize input */
	int i; char input[BUFLEN];
	while(fgets(input, BUFLEN, infile) != NULL){
		input[strlen(input)-1] = '\0';
		tokenize(input);
		strncpy(line[num_lines++], "\n", MAX_LENGTH);
	}
	/* Put tokenized words into word_list (no repeats) */
	dictionary();
	/* Compression time baby */
	mtf(outfile);
	/* Close files & exit */
	fclose(infile);
	fclose(outfile);
	exit(0);
}
