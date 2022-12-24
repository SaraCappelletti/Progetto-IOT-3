#include "SerialComTask.h"
#include <Arduino.h>
#include "Scheduler.h"

SerialComTask::SerialComTask(ServoMotor* servoMotor, Led* led) : 
  servoMotor(servoMotor), led(led) {}
  
void SerialComTask::init(int period){
  Task::init(period);
  Serial.begin(9600);
}
  
void SerialComTask::tick(){
  if(!(Scheduler::isBTReceiving)){
    char msg = (char) Serial.read();
    if(msg != -1){
      //TODO PARSING DEL MESSAGGIO E MOVIMENTO CONSEGUENTE      
    }
  }
}