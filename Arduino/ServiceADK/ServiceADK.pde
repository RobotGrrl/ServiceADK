// ServiceADK.pde
// --------------
// RobotGrrl.com
// November 29, 2011

#include <Max3421e.h>
#include <Usb.h>
#include <AndroidAccessory.h>
#include <Streaming.h>

AndroidAccessory acc("RobotGrrl",
"ServiceADK",
"Arduino ADK",
"1.0",
"http://www.android.com",
"0000000012345678");

int led = 13;

void setup() {

  Serial.begin(115200);
  Serial.print("\r\nStart");

  pinMode(led, OUTPUT);
  digitalWrite(led, LOW);

  acc.powerOn();

}

void loop() {

  byte msg[4];

  if (acc.isConnected()) {

    int len = acc.read(msg, sizeof(msg), 1);
    Serial << "len: " << len << endl;

    if (len > 0) {

      char command = (char)msg[0];

      Serial << command << endl;

      switch(command) {
      case 'B':
        switch((char)msg[1]) {
        case 'B':
          digitalWrite(led, !digitalRead(led));
          break;
        case '0':
          digitalWrite(led, LOW);
          break;
        case '1':
          digitalWrite(led, HIGH);
          break;
        }

        break;
      }

      msg[0] = '-';
      acc.write(msg, 1);

    }
    
  } 
  else {
    // Reset everything
    digitalWrite(led, LOW);
  }

  delay(10);
}


