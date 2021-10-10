package org.omg.PortableInterceptor;

import org.omg.CORBA.Any;
import org.omg.CORBA.CurrentOperations;

public interface CurrentOperations extends CurrentOperations {
  Any get_slot(int paramInt) throws InvalidSlot;
  
  void set_slot(int paramInt, Any paramAny) throws InvalidSlot;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/CurrentOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */