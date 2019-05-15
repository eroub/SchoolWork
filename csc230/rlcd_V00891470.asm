;
; Q5.asm
;
; Created: 2019-03-19 11:24:27 AM
; Author : evanr
;

#define LCD_LIBONLY
.include "lcd.asm"

.def ZERO = r20
.def ONE = r21

.equ A = 13
.equ C = 17
.equ SEED = 19

.cseg

    ldi ZERO, 0
    ldi ONE, 1

    ;initialization (comment this code because it conflicts with the driver of LCD,
	;turning off the background light of LCD)
    ;ldi r16, 0b00001010
    ;out DDRB, r16
	;ldi r16, 0b10101010
    ;sts DDRL, r16h

	call lcd_init

    ldi r16, SEED

repeat:

    rcall rand				;generate random value
	rcall led				;illuminate LEDs
	rcall itoa_binary		;show binary string
	rcall itoa_decimal		;show decimal string
	rcall show_binary_str	;show binary string
	rcall show_decimal_str	;show decimal string
    rcall delay				;delay 1 second
	
    rjmp repeat

;description: ; generate a random number in range 0~63
;input: R16 - seed or previous random number
;output:R16 - new random number 
rand:
    push r17

	ldi r17, A
	mul r16, r17
	mov r16, r0
	ldi r17, C
	add r16, r17
	andi r16, 0b00111111

	pop r17
	ret

;description: ; illuminate LEDs
;input: R16 - controlling value
;output:none
led:
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

	ret


;description: convert a value to binary string
;input: R16 - the value
;output: "bstr" in data memory
itoa_binary:

	ldi XL, low(bstr)
	ldi XH, high(bstr)

	sixth:
		sbrc r16, 6
		jmp sixth_1
		sixth_0:
			ldi r25, '0'
			st X+, r25
			jmp fifth
		sixth_1:
			ldi r25, '1'
			st X+, r25
	fifth:
		sbrc r16, 5
		jmp fifth_1
		fifth_0:
			ldi r25, '0'
			st X+, r25
			jmp fourth
		fifth_1:
			ldi r25, '1'
			st X+, r25
	fourth:
		sbrc r16, 4
		jmp fourth_1
		fourth_0:
			ldi r25, '0'
			st X+, r25
			jmp third
		fourth_1:
			ldi r25, '1'
			st X+, r25
	third:
		sbrc r16, 3
		jmp third_1
		third_0:
			ldi r25, '0'
			st X+, r25
			jmp second
		third_1:
			ldi r25, '1'
			st X+, r25
	second:
		sbrc r16, 5
		jmp second_1
		second_0:
			ldi r25, '0'
			st X+, r25
			jmp first
		second_1:
			ldi r25, '1'
			st X+, r25
	first:
		sbrc r16, 5
		jmp first_1
		first_0:
			ldi r25, '0'
			st X+, r25
			jmp nice_b
		first_1:
			ldi r25, '1'
			st X+, r25

	nice_b:			 
		ret

;description: convert a value to decimal string
;input: R16 - the value
;output: "dstr" in data memory
itoa_decimal:
	
	ldi XL, low(dstr)
	ldi XH, high(dstr)

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

;description: show binary on LCD at the 2st row (right aligned; 6 bits)
;input: string in data memory with label "bstr"
;output: none
show_binary_str:

	push r16

	ldi r16, 0x01
	push r16
	ldi r16, 0x09
	push r16
	call lcd_gotoxy
	pop r16
	pop r16

	ldi r16, high(bstr)
	push r16
	ldi r16, low(bstr)
	push r16
	call lcd_puts
	pop r16
	pop r16

	pop r16

	ret

;description: show decimal on LCD at the 1st row (left aligned; two digits)
;input: string in data memory with label "dstr"
;output: none
show_decimal_str:

	push r16

	ldi r16, 0x00
	push r16
	ldi r16, 0x00
	push r16
	call lcd_gotoxy
	pop r16
	pop r16

	ldi r16, high(dstr)
	push r16
	ldi r16, low(dstr)
	push r16
	call lcd_puts
	pop r16
	pop r16

	pop r16

	ret


;description: delay for some time
;input: none
;output: none
delay:
	push r16
	ldi r16, 0
loop_delay:
	call dly_ms
	inc r16
	cpi r16,250
	brlo loop_delay
	pop r16
	ret



.dseg

	bstr: .byte 100	;for temporarily storing string (for binary display)
	dstr: .byte 100	;for temporarily storing string (for decimal display)
