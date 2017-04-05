// http://dsscircuits.com/articles/arduino-i2c-slave-guide

/**
 * - array of commands 
 * - commands[0] - arduino address
 * - commands[1] - component address
 * - commands[2] - data
 */
 
#include <Wire.h>

#define SLAVE_ADDRESS   8
#define REG_SIZE        10

// enum of addresses and data

enum addressRegister {
  LED1_STATUS = 0x01,
  LED2_STATUS = 0x02,
  LED1_HUE_SET = 0x03,
  LED1_SAT_SET = 0x04,
  LED1_VAL_SET = 0x05, // 225 = full, 0 = off
  LED2_HUE_SET = 0x06,
  LED2_SAT_SET = 0x07,
  LED2_VAL_SET = 0x08,
  ULTRASONIC_CM = 0x09, 
};

byte sendCommands[3*8];
byte receivedCommands[3*8]; // [0] = address [1-24] = data

int ultrasonicDistance;
boolean commandRequested = false;

void setup() {
  Wire.begin(SLAVE_ADDRESS);                // join i2c bus with address #8
  Wire.onReceive(receiveEvent); // register event
  Wire.onRequest(requestEvent);
  Serial.begin(9600);           // start serial for output
}

void loop() {
  // ultrasonicDistance = getUltrasonicDistance();
//  for (int a=0; a<3; a++) {
//    Serial.print(a);
//    Serial.print(": ");
//    Serial.println(receivedCommands[a]);
//  }
//  Serial.println(ultrasonicDistance);
  commandRequested = false;
}

void requestEvent() {
  
  switch (receivedCommands[0]) {
    case 0x00:
      // null
      break;
    case 1:
      break;
  }
  
  // Wire.write(registerMap, REG_MAP_SIZE); 
}

void receiveEvent(int bytesReceived) {
  
  for (int i=0; i<bytesReceived; i++) {
    receivedCommands[i] = Wire.read();
    //Serial.println(receivedCommands[i]);
  }

  commandRequested = true;
  // ultrasonicDistance = unpackData(receivedCommands); //mpack data?
}

void storeData(byte target[], int data) {
  target[0] = (data & 0x000000FF) >> 0;
  target[1] = (data & 0x0000FF00) >> 8;
  target[2] = (data & 0x00FF0000) >> 16;
  target[3] = (data & 0xFF000000) >> 24;
}

int unpackData(byte data[]) {
  return (data[0] | (data[1] << 8) | (data[2] << 16) | (data[3] << 24));
}

/**
 * TODO
 */
uint8_t getUltrasonicDistance() {
  return 0;
}

