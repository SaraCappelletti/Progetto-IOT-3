#include <WiFi.h>
#include <PubSubClient.h>
#include "Const.h"
#include "Photoresistor.h"
#include "Pir.h"
#include "Led.h"


TaskHandle_t ComunicationTask;
TaskHandle_t LedTask;


WiFiClient espClient;
PubSubClient client(espClient);

char msg[MSG_BUFFER_SIZE];

Led* led = new Led(LED_PIN);
Pir* pir = new Pir(PIR_PIN);
Photoresistor photores = new Photoresistor(PHOTORES_PIN);

void setup_wifi() {

  delay(10);

  Serial.println(String("Connecting to ") + ssid);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.println(String("Message arrived on [") + topic + "] len: " + length );
}

void reconnect() {
  
  // Loop until we're reconnected
  
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    
    // Create a random client ID
    String clientId = String("esiot-2122-client-")+String(random(0xffff), HEX);

    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      // ... and resubscribe
      client.subscribe(topic);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void ComunicationTaskCode( void * parameter ){
  if (!client.connected()) {
    reconnect();
  }
  client.loop();
  //TO DO   inviare lo stato del pir e della fotoresistenza
  unsigned long now = millis();
  if (now - lastMsgTime > 10000) {
    lastMsgTime = now;
    value++;

    /* creating a msg in the buffer */
    snprintf (msg, MSG_BUFFER_SIZE, "hello world #%ld", value);

    Serial.println(String("Publishing message: ") + msg);
    
    /* publishing the msg */
    client.publish(topic, msg);  
  }
  delay(DELAY_PERIOD);
}

void LedTaskCode( void * parameter ){
  if(photores->isDetected()){
    led->turnOn();
  }
  else if(!(photores->isDetected())){
    led->turnOff();
  }
}

void setup() {
  Serial.begin(9600); 
  pinMode(led_1, OUTPUT);
  pinMode(led_2, OUTPUT);
  setup_wifi();
  randomSeed(micros());
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
  xTaskCreatePinnedToCore(ComunicationTaskCode,"ComunicationTask",10000,NULL,1,&ComunicationTask,0);                         
  delay(500);
  xTaskCreatePinnedToCore(LedTaskCode,"LedTask",10000,NULL,1,&LedTask,1);                         
  delay(500);
}



void loop() {

}
