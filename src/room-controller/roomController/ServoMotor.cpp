#include "ServoMotor.h"

ServoMotor::ServoMotor(const int pin, const int tolerance) : Component(pin), tolerance(tolerance) {
    motor.attach(pin);
}

void ServoMotor::move(int angle) {
  //moving the motor only if a new angle has been requested
  if((angle > this->angle + tolerance) || (angle < this->angle - tolerance)){
    motor.write(MIN_PULSE_WIDTH + angle*coeff);
    this->angle = angle;    
  }
}

int ServoMotor::getAngle() {
  return angle;
}