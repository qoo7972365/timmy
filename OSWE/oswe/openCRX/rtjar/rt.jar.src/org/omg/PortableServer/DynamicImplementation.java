package org.omg.PortableServer;

import org.omg.CORBA.ServerRequest;

public abstract class DynamicImplementation extends Servant {
  public abstract void invoke(ServerRequest paramServerRequest);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/DynamicImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */