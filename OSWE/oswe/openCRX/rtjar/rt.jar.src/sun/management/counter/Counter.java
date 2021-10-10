package sun.management.counter;

import java.io.Serializable;

public interface Counter extends Serializable {
  String getName();
  
  Units getUnits();
  
  Variability getVariability();
  
  boolean isVector();
  
  int getVectorLength();
  
  Object getValue();
  
  boolean isInternal();
  
  int getFlags();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/counter/Counter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */