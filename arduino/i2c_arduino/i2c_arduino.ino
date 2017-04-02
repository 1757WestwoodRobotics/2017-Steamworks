// http://dsscircuits.com/articles/arduino-i2c-slave-guide

#include <Wire.h>

#define SLAVE_ADDRESS   8
#define REG_MAP_SIZE    4
#define MAX_SENT_BYTES  3

byte registerMap[REG_MAP_SIZE];
byte registerMapTemp[REG_MAP_SIZE-1];
byte receivedCommands[MAX_SENT_BYTES]; // address, led1, led2

int ultrasonicDistance;

void setup() {
  Wire.begin(SLAVE_ADDRESS);                // join i2c bus with address #8
  Wire.onReceive(receiveEvent); // register event
  Wire.onRequest(requestEvent);
  Serial.begin(9600);           // start serial for output
}

void loop() {
  ultrasonicDistance = getUltrasonicDistance();
  storeData();

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
  
  if (bytesReceived == 1 && (receivedCommands[0] < REG_MAP_SIZE)) {
    return;
  }

  if (bytesReceived == 1 && (receivedCommands[0] >= REG_MAP_SIZE)) {
    receivedCommands[0] = 0x00;
    return;
  }

  byte zeroZeroData;
  byte zeroOneData;

  switch (receivedCommands[0]) {
    case 0x00:
      zeroZeroData = receivedCommands[1];
      bytesReceived--;
      if (bytesReceived == 1) {
        return;
      }
      break;
    case 0x01:
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

