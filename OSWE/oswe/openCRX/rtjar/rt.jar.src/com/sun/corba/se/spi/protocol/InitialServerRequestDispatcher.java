package com.sun.corba.se.spi.protocol;

import com.sun.corba.se.spi.resolver.Resolver;

public interface InitialServerRequestDispatcher extends CorbaServerRequestDispatcher {
  void init(Resolver paramResolver);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/protocol/InitialServerRequestDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */