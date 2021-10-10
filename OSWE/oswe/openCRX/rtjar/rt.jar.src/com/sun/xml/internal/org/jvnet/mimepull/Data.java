package com.sun.xml.internal.org.jvnet.mimepull;

import java.nio.ByteBuffer;

interface Data {
  int size();
  
  byte[] read();
  
  long writeTo(DataFile paramDataFile);
  
  Data createNext(DataHead paramDataHead, ByteBuffer paramByteBuffer);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/Data.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */