#include "Scheduler.h"
#include "ServoMotor.h"
#include "Led.h"



Scheduler sched(SCHEDULER_PERIOD);


void setup(){
  Serial.begin(9600);
  ServoMotor servoMotor(SERVOMOTOR_PIN);
  Led led(LED_PIN);
  //initializing scheduler
  sched.init(SCHEDULER_PERIOD);
  
  //initializing all tasks and adding them to the scheduler
  /*
  monitoringTask->init(MS_NORMAL_PERIOD, data);
  sched.addTask(monitoringTask);
  */

}

void loop(){
  sched.schedule();
}