#ifndef __BTCOMTASK__
#define __BTCOMTASK__

#include "Task.h"
#include "SmartRoom.h"
#include "SoftwareSerial.h"
#include <Wire.h>

class BTComTask : public Task {
  int rxPin;
  int txPin;
  SoftwareSerial* channel;
  SmartRoom* smartRoom;

public:
  BTComTask(int rxPin, int txPin, SmartRoom* smartRoom);  
  void init(int period);  
  void tick();
};

#endif