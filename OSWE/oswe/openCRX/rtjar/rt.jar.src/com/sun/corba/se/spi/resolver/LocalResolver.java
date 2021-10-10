package com.sun.corba.se.spi.resolver;

import com.sun.corba.se.spi.orbutil.closure.Closure;

public interface LocalResolver extends Resolver {
  void register(String paramString, Closure paramClosure);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/resolver/LocalResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */