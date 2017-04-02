// Wire Slave Receiver
// by Nicholas Zambetti <http://www.zambetti.com>

// Demonstrates use of the Wire library
// Receives data as an I2C/TWI slave device
// Refer to the "Wire Master Writer" example for use with this

// Created 29 March 2006

// This example code is in the public domain.

#include <Wire.h>

#define SLAVE_ADDRESS   8;
#define REG_MAP_SIZE    4;
#define MAX_SENT_BYTES  3;

byte registerMap[REG_MAP_SIZE];
byte registerMapTemp[REG_MAP_SIZE-1];
byte receivedCommands[MAX_SENT_BYTES]; // address, led1, led2

enum RegisterAddresses
{
  LED1Enable,
  LED2Enable,
  UltrasonicDistance,
  NUM_REGISTERS // Must be last
};

uint8_t registers[NUM_REGISTERS];

void setup() {
  Wire.begin(SLAVE_ADDRESS);                // join i2c bus with address #8
  Wire.onReceive(receiveEvent); // register event
  Wire.onRequest(requestEvent);
  Serial.begin(9600);           // start serial for output
}

void loop() {
 
//  delay(100);
//  uint16_t ultasoniceReading = getUltrasonic();
//  registers[UltrasonicHigh] = (ultasoniceReading >> 8);
//  registers[UltrasonicLow] = ultrasonicReading | 0xFF;

  ultrasonicDistance = getUltrasonicDistance();
  storeData();

}

//// function that executes whenever data is received from master
//// this function is registered as an event, see setup()
//void receiveEvent(int howMany) {
//  while (1 < Wire.available()) { // loop through all but the last
//    uint8_t registerAddress = Wire.read(); // receive byte as a character
//    uint8_t registerCount = Wire.read();
//    Serial.print(c);         // print the character
//  }
//  Wire.write(registers + registerAddress, registerCount);
//  int x = Wire.read();    // receive byte as an integer
//  Serial.println(x);         // print the integer
//}

void requestEvent() {
  Wire.send(registerMap, REG_MAP_SIZE);
}

void receiveEvent(int bytesReceived) {
  
  for (int a=0; i<bytesReceived; i++) {
    if (a < MAX_SENT_BYTES) {
      receivedCommands[i] = Wire.receive();
    } else {
      Wire.receive();
    }
  }
  
  if (bytesReceived == 1 && (receivedCommands[0] < REG_MAP_SIZE) {
    return;
  }

  if (bytesReceived == 1 && (receivedCommands[0] >= REG_MAP_SIZE)) {
    receivedCommands[0] = 0x00;
    return;
  }

  switch (receivedCommands[0] {
    case 0x0B :
      zeroBData = receivedCommands[1];
      bytesReceived--;
      if (bytedReceived == 1) {
        return;
      }
      break;
    case 0x0C:
      zeroCData = receivedCommands[1];
      return;
      break;
    default:
      return;
  }
  
}

void storeData() {
  registerMap[0] = ultrasonicDistance;
}

/**
 * TODO
 */
uint8_t getUltrasonicDistance() {
  return 0;
}

