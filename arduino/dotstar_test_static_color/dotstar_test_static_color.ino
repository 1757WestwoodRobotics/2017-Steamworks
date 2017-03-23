#include <Flash.h>

#include <FastLED.h>

#define NEOPIXEL_DATA_PIN    6
#define NEOPIXEL_COLOR_ORDER GRB
#define NEOPIXEL_CHIPSET     WS2812
#define NEOPIXEL_NUM_LEDS    48
#define DOTSTAR_DATA_PIN    11
#define DOTSTAR_CLOCK_PIN   13
#define DOTSTAR_COLOR_ORDER BGR
#define DOTSTAR_CHIPSET     APA102
#define DOTSTAR_NUM_LEDS    144
#define R_ANALOG_PIN 0
#define G_ANALOG_PIN 1
#define B_ANALOG_PIN 2
#define H_ANALOG_PIN 0
#define S_ANALOG_PIN 1
#define V_ANALOG_PIN 2
#define V_DIGITAL_PIN 2
#define BRIGHTNESS  255

CRGBArray<NEOPIXEL_NUM_LEDS> neopixel_leds;
CRGBArray<DOTSTAR_NUM_LEDS>  dotstar_leds;


void setup() {
  Serial.begin(9600);
  FastLED.delay(3000); // sanity delay
  FastLED.addLeds<NEOPIXEL_CHIPSET, 
                  NEOPIXEL_DATA_PIN,
                  NEOPIXEL_COLOR_ORDER>
                 (neopixel_leds, 
                  NEOPIXEL_NUM_LEDS);
  FastLED.addLeds<DOTSTAR_CHIPSET,
                  DOTSTAR_DATA_PIN,
                  DOTSTAR_CLOCK_PIN,
                  DOTSTAR_COLOR_ORDER,
                  DATA_RATE_MHZ(12)>
                 (dotstar_leds, 
                  DOTSTAR_NUM_LEDS);
  FastLED.setBrightness( BRIGHTNESS );
  neopixel_leds.fill_solid(0);
  dotstar_leds.fill_solid(0);
}

uint8_t cycle_hue = 0;

void loop()
{
//  const int red   = analogRead(R_ANALOG_PIN);
//  const int green = analogRead(G_ANALOG_PIN);
//  const int blue  = analogRead(B_ANALOG_PIN);
//  const CRGB color(red   >> 2,
//                   green >> 2,
//                   blue  >> 2);
//  const int hue        = cycle_hue;
  const int hue        = 100; //analogRead(H_ANALOG_PIN);
  const int saturation = 255; //analogRead(S_ANALOG_PIN);
  int value = 0;

  if (digitalRead(V_DIGITAL_PIN) == 0) {
    value = 255;
  } else {
    value = 0;
  }
  
  
  const CHSV color(hue        ,
                   saturation ,
                   value      );
  
//  const CHSV color(hue,
//                   255,
//                   255);
//  ++cycle_hue;

  delay(1);
  neopixel_leds.fill_solid(color);
  dotstar_leds.fill_solid(color);
  FastLED.show(); // display this frame
  Serial << " H: " << hue
         << " S: " << saturation
         << " V: " << value
         << "\n";
  FastLED.delay(100); // sanity delay
}
