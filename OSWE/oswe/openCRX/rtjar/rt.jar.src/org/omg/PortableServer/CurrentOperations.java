package org.omg.PortableServer;

import org.omg.CORBA.CurrentOperations;
import org.omg.PortableServer.CurrentPackage.NoContext;

public interface CurrentOperations extends CurrentOperations {
  POA get_POA() throws NoContext;
  
  byte[] get_object_id() throws NoContext;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/CurrentOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */