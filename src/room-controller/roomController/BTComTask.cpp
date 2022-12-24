#include "BTComTask.h"
#include "SoftwareSerial.h"
#include <Arduino.h>
#include "Scheduler.h"

BTComTask::BTComTask(int rxPin, int txPin, ServoMotor* servoMotor, Led* led) : 
  rxPin(rxPin), txPin(txPin), servoMotor(servoMotor), led(led) {}
  
void BTComTask::init(int period){
  Task::init(period);
  SoftwareSerial channel(rxPin, txPin);
  channel.begin(9600);
  
}
  
void BTComTask::tick(){
  char msg = (char) channel.read();
  if(msg != -1){
    Scheduler::setBTReceiving(true);
    //TODO PARSING DEL MESSAGGIO E MOVIMENTO CONSEGUENTE
  }
  else{
    Scheduler::setBTReceiving(false);
  }
}