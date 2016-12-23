





#include <WiFiUdp.h>
#include <WiFiServer.h>
#include <WiFiClientSecure.h>
#include <WiFiClient.h>
#include <ESP8266WiFiMulti.h>
#include <ESP8266WiFi.h>
int i;

int sign;

long value;

float result;

int clockpin = 5;

int datapin = 4;

unsigned long tempmicros;
WiFiServer server(3378);
const char* ssid = "DjPhone";
const char* password = "thehalo333";



void setup() {
	
  Serial.begin(9600);
 
  WiFi.begin(ssid);
  pinMode(clockpin, INPUT);
  pinMode(datapin, INPUT);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  server.begin();

}
  void loop () {
	


    while (digitalRead(clockpin) == HIGH) {
      delay(0);
		 //if clock is LOW wait until it turns to HIGH
    }

    tempmicros = micros();

    while (digitalRead(clockpin) == LOW) {delay(0);} //wait for the end of the HIGH pulse

    if ((micros() - tempmicros) > 600) { //if the HIGH pulse was longer than 500 micros we are at the start of a new bit sequence
		
      decode(); //decode the bit sequence
	 
	  

	
	  
	}

  }



  void decode() {

    sign = 1;

    value = 0;

    for (i = 0; i < 23; i++) {

      while (digitalRead(clockpin) == HIGH) { } //wait until clock returns to HIGH- the first bit is not needed

      while (digitalRead(clockpin) == LOW) {} //wait until clock returns to LOW

      if (digitalRead(datapin) == LOW) {

        if (i < 20) {

          value |= 1 << i;

        }

        if (i == 20) {

          sign = -1;

        }

      }

    }

    result = (value * sign) / 2000.0;
	WiFiClient client = server.available();
	delay(50);

	if(!client) {
		Serial.println(".");
		return;
		
  delay(0);
  }
    
	
	 client.print(result,3);
	 client.print("end");
	 client.flush();
	Serial.println(result, 4); //print result with 2 decimals

    delay(5);

  }


 



