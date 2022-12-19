#include "BTComTask.h"
#include "SoftwareSerial.h"
#include <Arduino.h>

BTComTask::BTComTask(int rxPin, int txPin, ServoMotor* servoMotor, Led* led) : 
  rxPin(rxPin), txPin(txPin), servoMotor(servoMotor), led(led) {}
  
void BTComTask::init(int period){
  Task::init(period);
  channel.begin(9600);
  SoftwareSerial channel(rxPin, txPin);
}
  
void BTComTask::tick(){
  char msg = (char) channel.read();
  //TODO controllare il messaggio ricevuto dal BT e muovere il servo/accendere la luce
}