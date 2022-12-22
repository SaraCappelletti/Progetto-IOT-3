#include "ServoMotor.h"

ServoMotor::ServoMotor(const int pin) : Component(pin) {
    motor.attach(pin);
}

void ServoMotor::move(int angle) {
  //moving the motor only if a new angle has been requested
  if(this->angle != angle){
    motor.write(MIN_PULSE_WIDTH + angle*coeff);
    this->angle = angle;    
  }
}

int ServoMotor::getAngle() {
  return angle;
}