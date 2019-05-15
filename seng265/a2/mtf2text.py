#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os, sys, re
from sys import argv

MAGIC_NUMBER = chr(0xba)+chr(0x5e)+chr(0xba)+chr(0x11)
magic_check = False
# ----------------------------------- ENCODING -----------------------------------
command = os.path.basename(__file__)
if __name__ == "__main__" and command == "text2mtf.py":
    script, infile = argv
    outfile = list(infile)
    outfile[-3] = "m"; outfile[-2] = "t"; outfile[-1] = "f"
    outfile = ''.join(outfile)

    with open(outfile, encoding="latin-1", mode='w') as o, open(infile) as f:
        o.write(MAGIC_NUMBER)
        word_list = []
        # Encoding algorithm
        for line in f:
            #token = re.sub("[^\w]", " ", line).split()
            token = line.split(" ")
            token[-1] = token[-1][:-1]
            if token[0] == '': token.pop(0)
            for n in token:
                # If word not in word_list, write hex+word and put in word_list
                if n not in word_list:
                    word_list.append(n)
                    o.write(chr(129+word_list.index(n)))
                    word_list.insert(0, word_list.pop())
                    o.write(n)
                else:
                # If word in word_list, write hex, then push to front
                    index = word_list.index(n)
                    o.write(chr(129+index))
                    word_list.insert(0, word_list.pop(index))
            o.write('\n')
# ----------------------------------- DECODING -----------------------------------
elif __name__ == "__main__" and command == "mtf2text.py":
    script, infile = argv
    outfile = list(infile)
    outfile[-3] = "t"; outfile[-2] = "x"; outfile[-1] = "t"
    outfile = ''.join(outfile)

    with open(outfile, 'w') as o, open(infile, encoding = 'latin-1', mode = 'r') as f:
        word_list = []
        for line in f:
            # Convert characters to decimal representations
            hex_list = []
            for c in line:
                hex_list.append(ord(c))
            # Magic number check
            if(not magic_check):
                if(hex_list[0] == 186 and hex_list[1] == 94 and hex_list[2] == 186 and hex_list[3] == 17):
                    magic_check = True
                    del hex_list[:4]
                else:
                    print("mtf file does not have correct magic numbers. Try again.")
                    exit(1)
            # If hex_list is empty write a line, else split hex_list into arr_values and words
            if hex_list:
                arr_val = []; word = []
                while hex_list:
                    if hex_list[0] >= 128:
                        arr_val.append(hex_list.pop(0)-129)
                    else:
                        char_list = []
                        while hex_list and hex_list[0] < 128:
                            if hex_list[0] != 10:
                                char_list.append(chr(hex_list.pop(0)))
                            else:
                                hex_list.pop(0)
                        word.append(''.join(char_list))
            # Decoding algorithm
                for n in range(len(arr_val)):
                    if arr_val[n] >= len(word_list):
                        temp = word.pop(0)
                        word_list.insert(0, temp)
                        o.write(temp)
                        if n != range(len(arr_val))[-1]: o.write(" ")
                    else:
                        o.write(word_list[arr_val[n]])
                        if n != range(len(arr_val))[-1]: o.write(" ")
                        word_list.insert(0, word_list.pop(arr_val[n]))
            o.write('\n')
