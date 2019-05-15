#!/bin/bash
clear
chmod 777 coding.py
chmod 777 text2mtf.py
echo "--------------- Testing text2mtf encoding functionality ---------------"
echo -e '\n'

for file in tests/test1*.txt; do

	./text2mtf.py $file
	echo "Testing ${file:6:6}"
	returned="$(cmp ${file::12}.mtf tests_comparable/${file:6:6}.mtf)"

	if [[ $returned -eq 0 ]]; then
		echo "PASSED"
	else
		echo "FAILED:"
		echo "${returned}"
	fi
	echo -e '\n'
done

