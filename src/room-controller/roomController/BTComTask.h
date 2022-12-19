#ifndef __BTCOMTASK__
#define __BTCOMTASK__

#include "Task.h"

class OutputTask: public Task {


public:
  OutputTask(int rxPin, int txPin);  
  void init(int period);  
  void tick();
};

#endif