/*
 * picknum.asm
 *
 *  Created: March 1st
 *   Author: Evan Roubekas (eroub)
 */ 

.equ COUNT = 20

.cseg

.org 0

	rjmp start

	src: .db 33, 60, 24, 55, 20, 44, 38, 90, 40, 0, 22, 80, 57, 43, 31, 100, 33, 27, 24, 18

	ldi ZL, low(src<<1)
	ldi ZH, high(src<<1)
	ldi XL, low(dest)
	ldi XH, high(dest)
	ldi r16, 0

start:

	lpm r17, Z+

	// If we've gone through all data values, jump to done
	inc r16
	cpi r16, COUNT+2
	breq done

	// 20 <= x < 40 check
	cpi r17, 20
	brlo start
	cpi r17, 40
	brsh start

	// If last bit is 1, jump back to start
	SBRC r17, 0
	jmp start

	// Store and jump
	st X+, r17
	jmp start

done:
	rjmp done

.dseg

.org 0x200

	dest: .byte 20