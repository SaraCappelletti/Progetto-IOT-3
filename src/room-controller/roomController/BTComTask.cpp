#include "BTComTask.h"
#include <Arduino.h>
#include "Scheduler.h"
#include "Const.h"


BTComTask::BTComTask(int rxPin, int txPin, ServoMotor* servoMotor, Led* led) : 
  rxPin(rxPin), txPin(txPin), servoMotor(servoMotor), led(led) {}
  
void BTComTask::init(int period){
  Task::init(period);
  service = new msgServiceBT(rxPin, txPin);
  service->init();
  
}
  
void BTComTask::tick(){
  Msg* msg = new Msg("");
  if(service->isMessageAvailable()){
    msg = service->recieveMsg();    
  }
  if(msg->getContent() != String("")){
    Scheduler::setBTReceiving(true);
    String str = msg->getContent();
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
  else{
    Scheduler::setBTReceiving(false);
  }
}