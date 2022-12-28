#include "SerialComTask.h"
#include <Arduino.h>
#include "Scheduler.h"
#include "Const.h"

SerialComTask::SerialComTask(ServoMotor* servoMotor, Led* led) : 
  servoMotor(servoMotor), led(led) {
    msSent = false;
  }
  
void SerialComTask::init(int period){
  Task::init(period);
  Serial.begin(9600);
}
  
void SerialComTask::tick(){
  //if a message has not yet been sent to answer the server, then a message on the current status of roomController is sent
  if(!msSent){
    String msg;
    if(led->isOn()){
      msg = "ON" + String(DELIMITER);
    }
    else{
      msg = "OFF" + String(DELIMITER);
    }
    msg = msg + String(servoMotor->getAngle());
    Serial.println(msg);
    msSent = true;
  }
  if(!(Scheduler::isBTReceiving)){
    int msg = Serial.read();
    if(msg != -1){
      msSent = false;
      String str = String((char) msg);
      String ledStateCommand = str.substring(0, str.indexOf(DELIMITER));
      String servoStateCommand = str.substring(str.indexOf(DELIMITER), str.length());
      if(ledStateCommand == "ON"){
        led->turnOn();
      }
      else if(ledStateCommand == "OFF"){
        led->turnOff();
      }
      servoMotor->move(servoStateCommand.toInt());
    }
  }
}