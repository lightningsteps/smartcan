#include <Bridge.h>
#include <HttpClient.h>
#include <NewPing.h>

 #define TRIGGER_PIN  12  // Arduino pin tied to trigger pin on the ultrasonic sensor.
 #define ECHO_PIN     11  // Arduino pin tied to echo pin on the ultrasonic sensor.
 #define MAX_DISTANCE 400 // Maximum distance we want to ping for (in centimeters). Maximum sensor distance is rated at 400-500cm.

 NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);

 int sensorHeight;
 int differenceBetweenSensorAndCan = 17;
 

void setup() {
  // put your setup code here, to run once:
  Bridge.begin();
  Serial.begin(9600);
  while(!Serial);
}

void loop() {

  unsigned int uS = sonar.ping(); // Send ping, get ping time in microseconds (uS).
  sensorHeight = uS / US_ROUNDTRIP_CM;
  
  // put your main code here, to run repeatedly:
  HttpClient client;
  client.get("dubrovniksmartcan-bracketscan.rhcloud.com/updateData/" + String(getResult()));
  
  Serial.println();

 // while (client.available())  {
   // char c = client.read();
    //Serial.print(c);
   // }
  Serial.println();
  Serial.println(String(getResult()));
  Serial.println(sensorHeight);
    Serial.flush();
    delay(300);
}

String getResult ()  {
  float result = 0;
  result = sensorHeight;
  result = result/60;
  result = result - 1;
  result = -result;
  result = result*100;
  if (result<60)  {
    return "15";  
  }else if (result <= 0){
    return "100";
  } else if (result > 100) {
    return "100";
  } else if (result < 10){
      return "<10";
  }else {
    return String(result,0);
    }
  }
