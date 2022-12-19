#include "Const.h"
#include "Scheduler.h"
#include "ServoMotor.h"
#include "Led.h"
#include "BTComTask.h"


ServoMotor* servoMotor = new ServoMotor(SERVOMOTOR_PIN);
Led* led = new Led(LED_PIN);
Scheduler sched(SCHEDULER_PERIOD);
BTComTask* btComTask = new BTComTask(RX_BT_PIN, TX_BT_PIN);

void setup(){
  Serial.begin(9600);
  
  //initializing scheduler
  sched.init();
  
  //initializing all tasks and adding them to the scheduler
  btComTask->init(BT_COM_PERIOD);
  sched.addTask(btComTask);
}

void loop(){
  sched.schedule();
}