package com.sun.tracing;

import java.lang.reflect.Method;

public interface Provider {
  Probe getProbe(Method paramMethod);
  
  void dispose();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/tracing/Provider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */