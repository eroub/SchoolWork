;
; Question4.asm
;
; Created: 2019-03-18 11:37:03 AM
; Author : evanr
;


.equ COUNT = 4

.cseg
.org 0

	rjmp start

	nums: .db 0x12, 0x45, 0x89, 0xCD

start:

	ldi ZL, low(nums<<1)
	ldi ZH, high(nums<<1)

	ldi r17, 0
loop:
	lpm r16, Z+
	ldi XL, low(str)
	ldi XH, high(str)
	rcall itoa

	inc r17
	cpi r17, COUNT
	brlo loop

done:
	rjmp done


;description: convert a signed magnitude value into 0-endign string
;input: R16 - the value
;       X   - starting address of the string
;output:none   
itoa:
	
	sbrc r16, 7
	jmp negative
	sbrs r16, 7
	jmp positive

	negative:
		ldi r25, '-'
		st X+, r25
		andi r16, 0b01111111
		jmp Hundreds
	positive:
		ldi r25, '+'
		st X+, r25

	Hundreds:
		ldi r20, 0
		sbrc r16, 6
		inc r20
		sbrc r16, 5
		inc r20

		ldi r21, 0

		sbrc r16, 4
		inc r21
		sbrc r16, 3
		inc r21
		sbrc r16, 2
		inc r21

		cpi r20, 2
		breq it_is
		it_isnt:
			ldi r25, '0'
			st X+, r25
			jmp tens
		it_is:
			cpi r21, 1
			brlo it_isnt
			ldi r25, '1'
			st X+, r25

	tens:
		ldi r20, 0
	tens_loop:
		cpi r16, 10
		brlo get_out
		subi r16, 10
		inc r20
		jmp tens_loop

	get_out:
		cpi r20, 10
		brlo ten_it_is
	ten_it_isnt:
		subi r20, 10
	ten_it_is:
		
		cpi r20, 9
		breq t_nine
		cpi r20, 8
		breq t_eight
		cpi r20, 7
		breq t_seven
		cpi r20, 6
		breq t_six
		cpi r20, 5
		breq t_five
		cpi r20, 4
		breq t_four
		cpi r20, 3
		breq t_three
		cpi r20, 2
		breq t_two
		cpi r20, 1
		breq t_one

		ldi r25, '0'
		st X+, r25
		jmp ones

		t_nine:
			ldi r25, '9'
			st X+, r25
			jmp ones
		t_eight:
			ldi r25, '8'
			st X+, r25
			jmp ones
		t_seven:
			ldi r25, '7'
			st X+, r25
			jmp ones
		t_six:
			ldi r25, '6'
			st X+, r25
			jmp ones
		t_five:
			ldi r25, '5'
			st X+, r25
			jmp ones
		t_four:
			ldi r25, '4'
			st X+, r25
			jmp ones
		t_three:
			ldi r25, '3'
			st X+, r25
			jmp ones
		t_two:
			ldi r25, '2'
			st X+, r25
			jmp ones
		t_one:
			ldi r25, '1'
			st X+, r25
			jmp ones

	ones:
		cpi r16, 9
		breq o_nine
		cpi r16, 8
		breq o_eight
		cpi r16, 7
		breq o_seven
		cpi r16, 6
		breq o_six
		cpi r16, 5
		breq o_five
		cpi r16, 4
		breq o_four
		cpi r16, 3
		breq o_three
		cpi r16, 2
		breq o_two
		cpi r16, 1
		breq o_one

		ldi r25, '0'
		st X+, r25
		jmp nice

		o_nine:
			ldi r25, '9'
			st X+, r25
			jmp nice
		o_eight:
			ldi r25, '8'
			st X+, r25
			jmp nice
		o_seven:
			ldi r25, '7'
			st X+, r25
			jmp nice
		o_six:
			ldi r25, '6'
			st X+, r25
			jmp nice
		o_five:
			ldi r25, '5'
			st X+, r25
			jmp nice
		o_four:
			ldi r25, '4'
			st X+, r25
			jmp nice
		o_three:
			ldi r25, '3'
			st X+, r25
			jmp nice
		o_two:
			ldi r25, '2'
			st X+, r25
			jmp nice
		o_one:
			ldi r25, '1'
			st X+, r25
			jmp nice

	nice:
		ldi r25, '\0'
		st X+, r25
		ret



.dseg

.org 0x200

	str: .byte 100
