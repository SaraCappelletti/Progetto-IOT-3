#ifndef __BTCOMTASK__
#define __BTCOMTASK__

#include "Task.h"
#include "ServoMotor.h"
#include "Led.h"

class BTComTask : public Task {
  int rxPin;
  int txPin;
  ServoMotor* servoMotor;
  Led* led;

public:
  BTComTask(int rxPin, int txPin, ServoMotor* servoMotor, Led* led);  
  void init(int period);  
  void tick();
};

#endif