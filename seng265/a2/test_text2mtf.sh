#!/bin/bash
clear
chmod 777 coding.py
chmod 777 text2mtf.py
echo " -------------- Testing text2mtf encoding functionality ---------------"

echo "Test 0: ... "
./text2mtf.py tests/test00.txt 
returned="$(diff tests/test00.mtf tests_comparable/test00.mtf)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"	
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 1: ... "
./text2mtf.py tests/test01.txt 
returned="$(diff tests/test01.mtf tests_comparable/test01.mtf)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"	
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 2: ... "
./text2mtf.py tests/test02.txt 
returned="$(diff tests/test02.mtf tests_comparable/test02.mtf)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"	
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 3: ... "
./text2mtf.py tests/test03.txt 
returned="$(diff tests/test03.mtf tests_comparable/test03.mtf)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"	
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 4: ... "
./text2mtf.py tests/test04.txt 
returned="$(diff tests/test04.mtf tests_comparable/test04.mtf)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"	
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 5: ... "
./text2mtf.py tests/test05.txt 
returned="$(diff tests/test05.mtf tests_comparable/test05.mtf)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"	
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 6: ... "
./text2mtf.py tests/test06.txt 
returned="$(diff tests/test06.mtf tests_comparable/test06.mtf)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"	
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 7: ... "
./text2mtf.py tests/test07.txt 
returned="$(diff tests/test07.mtf tests_comparable/test07.mtf)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"	
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 8: ... "
./text2mtf.py tests/test08.txt 
returned="$(diff tests/test08.mtf tests_comparable/test08.mtf)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"	
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 9: ... "
./text2mtf.py tests/test09.txt 
returned="$(diff tests/test09.mtf tests_comparable/test09.mtf)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"	
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 10: ... "
./text2mtf.py tests/test10.txt 
returned="$(diff tests/test10.mtf tests_comparable/test10.mtf)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"	
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'
