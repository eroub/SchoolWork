#!/bin/bash
clear
chmod 777 coding.py
chmod 777 mtf2text.py
echo "--------------- Testing mtf2text decoding functionality ---------------"
echo -e '\n'

for file in tests/test1*.mtf; do

	./mtf2text.py $file
	echo "Testing ${file:6:6}"
	returned="$(diff ${file::12}.txt tests_comparable/${file:6:6}.txt)"

	if [[ $returned -eq 0 ]]; then
		echo "PASSED"
	else
		echo "FAILED:"
		echo "${returned}"
	fi
	echo -e '\n'
done
