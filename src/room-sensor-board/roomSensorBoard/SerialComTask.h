#ifndef __SERIALCOMTASK__
#define __SERIALCOMTASK__

#include "Task.h"
#include "ServoMotor.h"
#include "Led.h"

class SerialComTask : public Task {
  ServoMotor* servoMotor;
  Led* led;
  bool msSent;

public:
  SerialComTask(ServoMotor* servoMotor, Led* led);  
  void init(int period);  
  void tick();
};

#endif