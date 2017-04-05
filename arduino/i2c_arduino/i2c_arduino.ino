// http://dsscircuits.com/articles/arduino-i2c-slave-guide

// -------------------------
// | Map Table             |
// | Address   Descriptor  |
// |-----------------------|
// | 0    | Light1 Set     |
// | 1    | Light2 Set     |
// | 2    | Ultrasonic     |
// | 3    | ---            |
// | 4    | ---            |
// -------------------------

/**
 * - array of commands 
 * - commands[0] - arduino address
 * - commands[1] - component address
 * - commands[2] - data
 */
 
#include <Wire.h>

#define SLAVE_ADDRESS   8
#define REG_SIZE        

byte sendCommands[3*8];
byte receivedCommands[3*8];
int ultrasonicDistance;

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
  Serial.println(ultrasonicDistance);
}

void requestEvent() {
  // Wire.write(registerMap, REG_MAP_SIZE);
}

void receiveEvent(int bytesReceived) {
  
  for (int i=0; i<bytesReceived; i++) {
    receivedCommands[i] = Wire.read();
    //Serial.println(receivedCommands[i]);
  }

  ultrasonicDistance = unpack(recievedCommands);

  switch (receivedCommands[0]) {
    case 0:
      Serial.println("case 0");
      break;
    case 1:
      Serial.println("case 1");
      break;
    case 2:
      Serial.println("case 2");
      break;
    default:
      break;
  }
  
}

void storeData(byte[] target, int data) {
  target[0] = (data & 0x000000FF) >> 0;
  target[1] = (data & 0x0000FF00) >> 8;
  target[2] = (data & 0x00FF0000) >> 16;
  target[3] = (data & 0xFF000000) >> 24;
}

int unpackData(byte[] data) {
  return (data[0] | (data[1] << 8) | (data[2] << 16) | (data[3] << 24));
}

/**
 * TODO
 */
uint8_t getUltrasonicDistance() {
  return 0;
}

