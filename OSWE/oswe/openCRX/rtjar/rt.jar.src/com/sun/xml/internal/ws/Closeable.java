package com.sun.xml.internal.ws;

import java.io.Closeable;
import javax.xml.ws.WebServiceException;

public interface Closeable extends Closeable {
  void close() throws WebServiceException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/Closeable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */