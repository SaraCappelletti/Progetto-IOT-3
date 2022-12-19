#include "BTComTask.h"
#include "SoftwareSerial.h"
#include <Arduino.h>

BTComTask::BTComTask(int rxPin, int txPin){
  SoftwareSerial channel(rxPin, txPin);
}
  
void BTComTask::init(int period){
  Task::init(period);
  channel.begin(9600);
}
  
void BTComTask::tick(){
  char msg = (char) channel.read();
  //TODO controllare il messaggio ricevuto dal BT e agire di conseguenza
}