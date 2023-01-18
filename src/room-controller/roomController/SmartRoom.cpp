#include "SmartRoom.h"

SmartRoom::SmartRoom(ServoMotor* servoMotor, Led* led) : 
  servoMotor(servoMotor), led(led){}

void SmartRoom::setLedState(bool state){
  if(state){
    led->turnOn();
  }
  else{
    led->turnOff();
  }
}

bool SmartRoom::getLedState(){
  return led->isOn();
}

void SmartRoom::setServoMotorState(int angle){
  servoMotor->move(angle);
}

int SmartRoom::getServoMotorState(){
  return servoMotor->getAngle();
}