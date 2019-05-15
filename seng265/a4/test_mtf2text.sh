#!/bin/bash
make
clear
chmod 777 mtf2text2
echo "--------------- Testing mtf2text decoding functionality ---------------"
echo -e '\n'

for file in tests/test*.mtf; do

	./mtf2text2 $file
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
