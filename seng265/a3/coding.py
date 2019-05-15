#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os, sys, re
from sys import argv

def encoding(token, word_list, o):
    """Encoding function"""
    for n in token:
        # If word not in word_list
        if n not in word_list:
            code_len = len(word_list)+1
            if(code_len <= 120):
                o.write(chr(128+code_len))
                o.write(n)
                word_list.insert(0, n)
            elif(code_len > 120 and code_len <= 375):
                o.write(chr(249))
                o.write(chr(code_len-121))
                o.write(n)
                word_list.insert(0, n)
            elif(code_len > 375 and code_len <= 65912):
                o.write(chr(250))
                o.write(chr((code_len-376)//256))
                o.write(chr((code_len-376)%256))
                o.write(n)
                word_list.insert(0, n)
        # If word in word_list
        else:
            index_pos = word_list.index(n)+1
            if(index_pos <= 120):
                o.write(chr(128+index_pos))
                word_list.insert(0, word_list.pop(index_pos-1))
            elif(index_pos > 120 and index_pos <= 375):
                o.write(chr(249))
                o.write(chr(index_pos-121))
                word_list.insert(0, word_list.pop(index_pos-1))
            elif(index_pos > 375 and index_pos <= 65912):
                o.write(chr(250))
                o.write(chr((index_pos-376)//256))
                o.write(chr((index_pos-376)%256))
                word_list.insert(0, word_list.pop(index_pos-1))

def decoding(arr_val, word, word_list, o):
    """Decoding function"""
    for n in range(len(arr_val)):
        if word and arr_val[n] >= len(word_list):
            temp = word.pop(0)
            word_list.insert(0, temp)
            o.write(temp)
            if n != range(len(arr_val))[-1]: o.write(" ")
        else:
            o.write(word_list[arr_val[n]])
            if n != range(len(arr_val))[-1]: o.write(" ")
            word_list.insert(0, word_list.pop(arr_val[n]))

# ----------------------------------- ENCODING -----------------------------------
command = os.path.basename(__file__)
if __name__ == "__main__" and command == "text2mtf.py":
    script, infile = argv
    outfile = list(infile)
    outfile[-3] = "m"; outfile[-2] = "t"; outfile[-1] = "f"
    outfile = ''.join(outfile)
    MAGIC_NUMBER = chr(0xBA)+chr(0x5E)+chr(0xBA)+chr(0x12)

    with open(outfile, encoding="latin-1", mode='w') as o, open(infile) as f:
        o.write(MAGIC_NUMBER)
        word_list = []

        # Encoding algorithm
        for line in f:
            token = line.split(" ")
            token[-1] = token[-1][:-1]
            if token[0] == '': token.pop(0)
            encoding(token, word_list, o)
            o.write('\n')
# ----------------------------------- DECODING -----------------------------------
elif __name__ == "__main__" and command == "mtf2text.py":
    script, infile = argv
    outfile = list(infile)
    outfile[-3] = "t"; outfile[-2] = "x"; outfile[-1] = "t"
    outfile = ''.join(outfile)
    remember_me = False
    forget_me_not = []
    fa_block = False
    magic_check = False

    with open(outfile, 'w') as o, open(infile, encoding = 'latin-1', mode = 'r', newline="") as f:
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
                elif(hex_list[0] == 186 and hex_list[1] == 94 and hex_list[2] == 186 and hex_list[3] == 18):
                    magic_check = True
                    del hex_list[:4]
                else:
                    print("mtf file does not have correct magic numbers. Try again.")
                    exit(1)

            # If hex_list is empty write a line, else split hex_list into arr_values and words
            if hex_list:
                arr_val = []; word = []

                # ---------- Remember me? ----------
                if remember_me:
                    forget_me_not.extend(hex_list)
                    hex_list = forget_me_not.copy()
                    remember_me = False
                    fa_block = False
                # Spaghetti Code to check if line end is valid
                if len(hex_list) > 4:
                    if hex_list[-4] == 250 or hex_list[-3] == 249: fa_block = True
                if len(hex_list) >= 6:
                    if hex_list[-2] == 250 and hex_list[-4] == 250 and hex_list[-6] == 250: fa_block = False
                # New line / Return Carriage Check
                if hex_list[-1] == 13:
                    remember_me = True
                    forget_me_not = hex_list
                    continue
                if 249 in hex_list and not fa_block:
                    if hex_list[-2] == 249:
                        remember_me = True
                        forget_me_not = hex_list
                        continue
                if 250 in hex_list and not fa_block:
                    if hex_list[-2] == 250 or hex_list[-3] == 250:
                        remember_me = True
                        forget_me_not = hex_list
                        continue
                fa_block = False
                # ---------------------------------

                # Pop hex_list until empty | If < 128, turn into word
                    # else put value into an array list representing dictionary indexes
                while hex_list:
                    if hex_list[0] >= 128:
                        if hex_list[0] == 249:
                            hex_list.pop(0)
                            arr_val.append(hex_list.pop(0)+120)
                        elif hex_list[0] == 250:
                            hex_list.pop(0)
                            temp = (hex_list.pop(0)*256) + 375 + hex_list.pop(0)
                            arr_val.append(temp)
                        else:
                            arr_val.append(hex_list.pop(0)-129)
                    else:
                        char_list = []
                        while hex_list and hex_list[0] < 128:
                            if hex_list[0] != 10: char_list.append(chr(hex_list[0]))
                            hex_list.pop(0)
                        word.append(''.join(char_list))
                # DECODE
                decoding(arr_val, word, word_list, o)
            o.write('\n')
