package com.sun.jmx.remote.internal;

import java.io.IOException;
import java.rmi.MarshalledObject;

public interface Unmarshal {
  Object get(MarshalledObject<?> paramMarshalledObject) throws IOException, ClassNotFoundException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/internal/Unmarshal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */