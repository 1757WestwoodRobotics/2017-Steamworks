#include <Wire.h>
#include <FastLED.h>

#define DATA_PIN    6
#define COLOR_ORDER GRB
#define CHIPSET     WS2812 
#define NUM_LEDS    48

#define H_ANALOG_PIN 0
#define S_ANALOG_PIN 1
#define V_ANALOG_PIN 2

CRGB leds[NUM_LEDS];

const CHSV GREEN(100, 255, 255);
const CHSV RED(0, 255, 255);
const CHSV BLUE(160, 255, 255);
const CHSV BLANK(0, 0, 0);

void setup() {
  Wire.begin(9600);
  delay(100);
  FastLED.delay(3000); // sanity delay
  FastLED.addLeds<CHIPSET, DATA_PIN, COLOR_ORDER>(leds, NUM_LEDS);
  for (int k = 0; k < 48; k++) {
    leds[k] = BLANK;
  }
}

void loop() {
  // put your main code here, to run repeatedly:
//  for (int i = 0; i < 24; i++) {
//    leds[i] = GREEN;
//  }
  for (int i = 24; i < 48; i++) {
    leds[i] = GREEN;
  }
  FastLED.show();
  delay(100);
}



