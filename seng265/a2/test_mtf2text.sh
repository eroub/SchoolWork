#!/bin/bash
clear
chmod 777 coding.py
chmod 777 mtf2text.py
echo "-------------- Testing mtf2text decoding functionality --------------"

echo "Test 0: ..."
./mtf2text.py tests/test00.mtf
returned="$(diff tests/test00.txt tests_comparable/test00.txt)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 1: ..."
./mtf2text.py tests/test01.mtf
returned="$(diff tests/test01.txt tests_comparable/test01.txt)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 2: ..."
./mtf2text.py tests/test02.mtf
returned="$(diff tests/test02.txt tests_comparable/test02.txt)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 3: ..."
./mtf2text.py tests/test03.mtf
returned="$(diff tests/test03.txt tests_comparable/test03.txt)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 4: ..."
./mtf2text.py tests/test04.mtf
returned="$(diff tests/test04.txt tests_comparable/test04.txt)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 5: ..."
./mtf2text.py tests/test05.mtf
returned="$(diff tests/test05.txt tests_comparable/test05.txt)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 6: ..."
./mtf2text.py tests/test06.mtf
returned="$(diff tests/test06.txt tests_comparable/test06.txt)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 7: ..."
./mtf2text.py tests/test07.mtf
returned="$(diff tests/test07.txt tests_comparable/test07.txt)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 8: ..."
./mtf2text.py tests/test08.mtf
returned="$(diff tests/test08.txt tests_comparable/test08.txt)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 9: ..."
./mtf2text.py tests/test09.mtf
returned="$(diff tests/test09.txt tests_comparable/test09.txt)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'

echo "Test 10: ..."
./mtf2text.py tests/test10.mtf
returned="$(diff tests/test10.txt tests_comparable/test10.txt)"
if [[ $returned -eq 0 ]]; then
	echo "PASSED"
else
	echo "FAILED:"
	echo "${returned}"
fi
echo -e '\n'
