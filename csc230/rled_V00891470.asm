;
; Assignment2.asm
;
; Created: 3/3/2019 8:28:16 AM
; Author : eroubekas
;

.def ZERO = r20
.def ONE = r21
.equ A = 13
.equ C = 17
.equ SEED = 19

.cseg

.org 0

	ldi ZERO, 0
	ldi ONE, 1

	ldi r16, 0xFF
	sts DDRL, r16
	out DDRB, r16

	ldi r16, SEED

repeat:

	; Random Gen
	ldi r17, A
	mul r16, r17
	mov r16, r0
	ldi r17, C
	add r16, r17
	andi r16, 0b00111111

	; ----- PORTB
	ldi r29, 0b00000000
	
	ldi r28, 8
	sbrc r16, 1
	add r29, r28

	ldi r28, 2
	sbrc r16, 0
	add r29, r28

	; ----- PORTL
	ldi r25, 0b00000000

	ldi r28, 128
	sbrc r16, 5
	add r25, r28

	ldi r28, 32
	sbrc r16, 4
	add r25, r28
	
	ldi r28, 8
	sbrc r16, 3
	add r25, r28

	ldi r28, 2
	sbrc r16, 2
	add r25, r28

	; ----- OUTPUT
	sts PORTL, r25
	out PORTB, r29
	
	; delay 1s
	clr r19
	clr r18
loop1:
	ldi r20, 20
loop2:
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	nop
	dec r20
	cpi r20, 0
	brne loop2
	add r18, ONE
	adc r19, ZERO
	cpi r19, 250
	brne loop1

	;another round
	rjmp repeat
