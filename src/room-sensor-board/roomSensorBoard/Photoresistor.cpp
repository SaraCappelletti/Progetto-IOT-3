#include "Photoresistor.h"

Photoresistor::Photoresistor(const int pin) : Sensor(pin) {
  pinMode(pin, INPUT);
}

int Photoresistor::read() {
  int value = analogRead(pin);
  return value;
}