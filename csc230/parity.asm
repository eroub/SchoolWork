;
; parity.asm
;
; Created: 2019-03-01 2:06:10 PM
; Author : evanr
;


; Replace with your application code
.cseg
.org 0

ldi r16, 0x56
ldi r18, 0xAB
add r16, r18
ldi r17, 0
Loop:
	SBRC r16, 0
	inc r17
	lsr r16
	cpi r16, 0
	brne loop

SBRS r17, 0
inc r0


Done:
	rjmp Done