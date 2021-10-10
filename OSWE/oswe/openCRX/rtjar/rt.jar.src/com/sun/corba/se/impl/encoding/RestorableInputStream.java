package com.sun.corba.se.impl.encoding;

interface RestorableInputStream {
  Object createStreamMemento();
  
  void restoreInternalState(Object paramObject);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/RestorableInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */