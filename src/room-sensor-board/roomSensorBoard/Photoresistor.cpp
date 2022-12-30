#include "Photoresistor.h"

Photoresistor::Photoresistor(const int pin) : Sensor(pin) {}

int Photoresistor::read() {
  int value = analogRead(pin);
  return value;
}