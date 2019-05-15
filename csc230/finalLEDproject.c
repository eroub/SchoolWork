//#define TOP 100000   //choose a appropriate value by test on board
#define TOP 6249
//#define F_CPU 1600000UL

#include "main.h"
#include <avr/io.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <util/delay.h>
#include <avr/interrupt.h>
#include "lcd_drv.h"
#include "mydefs.h"

//global variables
#define DMODE _IRAM_BYTE(0x250)	//display mode
#define RNDNUM _IRAM_BYTE(0x251)	//random number
#define RESET_FLAG _IRAM_BYTE(0x252)	//control bit, make sure initialization only run once
#define ADC_BTN_SELECT 0x316

void led(int num)
{
	uint8_t temp = 0x00;
	if(num & 0x01)
	temp |= 0x02;
	if(num & 0x02)
	temp |= 0x08;
	PORTB = temp;
	
	temp = 0x00;
	if(num & 0x04)
	temp |= 0x02;
	if(num & 0x08)
	temp |= 0x08;
	if(num & 0x10)
	temp |= 0x20;
	if(num & 0x20)
	temp |= 0x80;
	PORTL = temp;
}

ISR(ADC_vect) {
	char str[2];
	uint16_t val = ADC;   //combination of ADCH and ADCL
	
	RNDNUM = rand()%64;
	led(RNDNUM);
	itoa(RNDNUM, str, 10);
	
	lcd_xy(3,0);
	lcd_puts(str);
	
	if(val<ADC_BTN_SELECT) {
		DMODE++;
		DMODE %= 2;
		if(DMODE == 1){
			lcd_xy(0,0);
			lcd_puts("T:-");
			lcd_xy(5,0);
			lcd_puts("C");
		} else {
			lcd_xy(0,0);
			lcd_puts("H: ");
			lcd_xy(5,0);
			lcd_puts("%");	
		}
	}
	reti();
}

ISR(TIMER1_COMPB_vect) {
	reti();
}


int main(void)
{
	
	if(RESET_FLAG) {
		lcd_init();
		lcd_xy(10,1);
		lcd_puts("V-1470");
		
		TCCR1A = 0b00100000;
		TCCR1B = 0x05;
		TCCR1C = 0x00;
		TIMSK1 = 0x04;
		OCR1B = TOP;
		
		ADMUX = 0x40;		//out AVcc
		ADCSRB = 0x05;		//use Timer 1 Compare Match B to trigger ADC conversion
		ADCSRA = 0xAF;
		DIDR0 = 0xFE;
		DIDR2 = 0xFE;		// enable ADC, auto-trigger and ADC complete interrupt. with 128 prescaler
		
		RESET_FLAG = 0;
	}
	
	sei();
	
	while (1){}
}