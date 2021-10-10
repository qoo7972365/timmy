package org.w3c.dom.ls;

import org.w3c.dom.events.Event;

public interface LSProgressEvent extends Event {
  LSInput getInput();
  
  int getPosition();
  
  int getTotalSize();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/ls/LSProgressEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */