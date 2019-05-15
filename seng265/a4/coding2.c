#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>

#define BUFLEN 32

unsigned char  magic_number_1[] = {0xBA, 0x5E, 0xBA, 0x11, 0x00};
unsigned char  magic_number_2[] = {0xBA, 0x5E, 0xBA, 0x12, 0x00};

void *emalloc(size_t n){
	void *p = malloc(n);
	if(p == NULL) {
		fprintf(stderr, "malloc of %u bytes failed", n);
		exit(1);
	}
	return p;
}

typedef struct Vector Vector;
struct Vector {
	char *word;
	int hexval;
	Vector *next;
};

Vector *newitem(char *word, int hexval){
	Vector *newv;
	newv = (Vector *) emalloc(sizeof(Vector));
	newv->word = word;
	newv->hexval = hexval;
	newv->next = NULL;
	return newv;
}

Vector *addfront(Vector *listp, Vector *newp){
	newp->next = listp;
	return newp;
}

Vector *addend(Vector *listp, Vector *newp){
	Vector *p;
	if(listp == NULL) return newp;
	for(p=listp; p->next != NULL; p=p->next)
		;
	p->next = newp;
	return listp;
}

char *retrieve_word(Vector *dict, int index){
	int count;
	for(count = 0; count < index; dict=dict->next, count++)
		;
	return (dict->word);
}

Vector *remove_item(Vector *listp, char *word){
	Vector *curr, *prev;
	prev = NULL;
	for(curr = listp; curr != NULL; curr = curr->next){
		if(strcmp(word, curr->word) == 0){
			if(prev == NULL){
				listp = curr->next;
			} else {
				prev->next = curr->next;
			}
			free(curr->word);
			free(curr);
			return listp;
		}
		prev = curr;
	}
	fprintf(stderr, "remove: %s not in list\n", word);
	exit(1);
}

void freall(Vector *listp){
	Vector *next;
	for(; listp != NULL; listp = next){
		next = listp->next;
		free(listp->word);
		free(listp);
	}
}

int vector_length(Vector *vec){
	int count = 0;
	Vector *p;
	if(vec == NULL){
	       	return count;
	} else {
		count++;
	}
	for(p=vec; p->next != NULL; p=p->next) count++;
	return count;
}

char *string_duplicator(char *intake){
	char *copy;
	assert(intake != NULL);
	copy = (char *) emalloc(sizeof(char) * strlen(intake) + 1);
	strncpy(copy, intake, strlen(intake)+1);
	return copy;
}

void convert_input(FILE *input, Vector *list, Vector *indices){
	Vector *new;
	char *space = " ";
	char *word_dup;
	// While not at end of file, read bytes:
	// 	If byte == 10 --> Turn into newline representation
	// 	If byte >= 128 --> Turn into code value
	// 	If byte < 128 --> Turn into word
	do {
		unsigned int c = getc(input);
		if(feof(input)) return;
		// ----- NEW LINE
		if(c == 10){
			Vector *new = newitem(string_duplicator(space), -1);
			indices = addend(indices, new);
			continue;
		}
		// ----- WORD
		if(c < 128){
			char word[BUFLEN] = "";
			int count = 0;
			word[count++] = (char) c;

			while(!feof(input) && (c = getc(input)) < 128){
				if(feof(input)) break;
				if(c == 10){
					Vector *new = newitem(string_duplicator(space), -1);
					indices = addend(indices, new);
					break;
				}
				word[count++] = (char) c;
			}
			word_dup = string_duplicator(word);
			list = addend(list, newitem(word_dup, 0));
		}
		// ----- CODE
		if(c >= 128){
			if(c == 249){
				Vector *new = newitem(string_duplicator(space), getc(input)+120);
				indices = addend(indices, new);
			} else if(c == 250){
				Vector *new = newitem(string_duplicator(space), (getc(input)*256) + 375 + getc(input));
				indices = addend(indices, new);
			} else {
				indices = addend(indices, newitem(string_duplicator(space), c-129));
			}
		}
	} while(!feof(input));
}

void decode_algo(FILE *outfile, Vector *word_list, Vector *indices){
	// Move from initialization
	word_list = word_list->next;
	indices = indices->next;

	// Create dictionary
	Vector *dictionary = NULL;
	
	// Algorithm
	for(; indices->next != NULL; indices = indices->next){
		if(indices->hexval == -1){
			fprintf(outfile, "\n");
			continue;
		}
		// New word, add to front of dictionary, write to file
		if(word_list != NULL && indices->hexval >= vector_length(dictionary)){
			char *temp = string_duplicator(word_list->word);
			char *temp2 = string_duplicator(temp);
			free(temp);
			word_list = word_list->next;
			dictionary = addfront(dictionary, newitem(temp2, 0));

			fprintf(outfile, "%s", temp2);
			if((indices->next)->hexval != -1) fprintf(outfile, " ");
		// Old word, find in dictionary, move to front, write to file
		} else {
			char *d_temp = string_duplicator(retrieve_word(dictionary, indices->hexval));
			dictionary = remove_item(dictionary, d_temp);
			dictionary = addfront(dictionary, newitem(d_temp, 0));

			fprintf(outfile, "%s", d_temp);
			if((indices->next)->hexval != -1) fprintf(outfile, " ");
		}
	}
	freall(dictionary);
}

int magic_check(FILE *input) {
	unsigned int c, i;
	for(i=0; i<4; i++){
		c = getc(input);
		if(c == magic_number_1[i] || c == magic_number_2[i]) continue;
		return -1;
	}
	return 0;
}

int encode(FILE *input, FILE *output) {
	return 0;
}

int decode(FILE *input, FILE *output) {
	unsigned int c;

	// Magic Number Check
	if(magic_check(input) != 0){
		fprintf(stderr, "Magic numbers check failed");
		return -1;
	}

	// Initialize word_list and indices vectors
	char *w_l = string_duplicator("init");
	Vector *word_list = newitem(w_l, -111);
	char *i = string_duplicator("init");
	Vector *indices = newitem(i, -111);

	// Convert the input to something usable
	convert_input(input, word_list, indices);
	
	// DECODE
	decode_algo(output, word_list, indices);
	fprintf(output, "\n");

	// Free the bytes
	freall(word_list);
	freall(indices);

    return 0;
}
