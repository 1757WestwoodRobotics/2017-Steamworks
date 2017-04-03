// http://dsscircuits.com/articles/arduino-i2c-slave-guide

// -------------------------
// | Register Map          |
// | Address   Descriptor  |
// |-----------------------|
// | 0    | Light1 Set     |
// | 1    | Light2 Set     |
// | 2    | Ultrasonic     |
// | 3    | ---            |
// | 4    | ---            |
// -------------------------

#include <Wire.h>

#define SLAVE_ADDRESS   8
#define REG_MAP_SIZE    4
#define MAX_SENT_BYTES  3

byte registerMap[REG_MAP_SIZE];

int ultrasonicDistance;

void setup() {
  Wire.begin(SLAVE_ADDRESS);                // join i2c bus with address #8
  Wire.onReceive(receiveEvent); // register event
  Wire.onRequest(requestEvent);
  Serial.begin(9600);           // start serial for output
}

void loop() {
  ultrasonicDistance = getUltrasonicDistance();
}

void requestEvent() {
  Wire.write(registerMap, REG_MAP_SIZE);
}

void receiveEvent(int bytesReceived) {
  
  for (int i=0; i<bytesReceived; i++) {
    Serial.println(Wire.read());
    if (i < MAX_SENT_BYTES) {
      receivedCommands[i] = Wire.read();
    } else {
      Wire.read();
    }
  }

  switch (receivedCommands[0]) {
    case 0:
      zeroZeroData = receivedCommands[1]; // set Address
      return;
      break;
    case 1:
      zeroOneData = receivedCommands[1];
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

