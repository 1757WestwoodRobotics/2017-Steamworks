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
  LED1_STATUS = 1,
  LED2_STATUS = 2,
  LED1_HUE_SET = 3,
  LED1_SAT_SET = 4,
  LED1_VAL_SET = 5, // 225 = full, 0 = off
  LED2_HUE_SET = 6,
  LED2_SAT_SET = 7,
  LED2_VAL_SET = 8,
  ULTRASONIC_CM = 9, 
};

uint8_t sendCommands[2*4];
uint8_t receivedCommands[2*4]; // [0] = address

uint32_t ultrasonicDistance;
boolean commandRequested = false;

uint8_t arr[4]; 

void setup() {
  Wire.begin(SLAVE_ADDRESS);                // join i2c bus with address #8
  Wire.onReceive(receiveEvent); // register event
  Wire.onRequest(requestEvent);
  Serial.begin(9600);           // start serial for output
}

void storeData(byte target[], uint32_t data) {
  Serial.println(151192065);
  Serial.println(data);
  Serial.println((data & 0x000000FF) >> 0);
  Serial.println(data);
  Serial.println((data & 0x0000FF00) >> 8);
  Serial.println(data);
  Serial.println((data & 0x00FF0000) >> 16);
  Serial.println(data);
  Serial.println((data & 0xFF000000) >> 24);
  Serial.println(data);
  target[0] = (data & 0x000000FF) >> 0;
  target[1] = (data & 0x0000FF00) >> 8;
  target[2] = (data & 0x00FF0000) >> 16;
  target[3] = (data & 0xFF000000) >> 24;
}

uint32_t unpackData(uint8_t data[]) {
  uint32_t i = (uint32_t) data[3];
  i << 24;
  i |= (uint32_t) data[2];
  i << 16;
  i |= (uint32_t) data[1];
  i << 8;
  i |= (uint32_t) data[0];
  return i;
}

void loop() {
//   for (int i = 0; i < 4; ++i) {
//    Serial.println(arr[i]);
//   }
  // Serial.println(unpackData(arr));
  commandRequested = false;
}

void requestEvent() {
  
  switch (receivedCommands[0]) {
    case 0:
      // null
      break;
    case 9:
      storeData(arr, 151192065); // 0x01020309
      Wire.write(arr, 4);
      break;
  }
  
  // Wire.write(registerMap, REG_MAP_SIZE); 
}

void receiveEvent(int bytesReceived) {
  
  for (int i=0; i<bytesReceived; i++) {
    receivedCommands[i] = Wire.read();
    //Serial.println(receivedCommands[i]);
  }

//  Serial.println(unpackData(&receivedCommands[0])); // gets address
//  Serial.println(unpackData(&receivedCommands[5]));

  commandRequested = true;
  // ultrasonicDistance = unpackData(receivedCommands); //pack data?
}

/**
 * TODO
 */
uint8_t getUltrasonicDistance() {
  return 0;
}

