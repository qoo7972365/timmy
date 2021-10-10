package com.sun.java.util.jar.pack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

interface CodingMethod {
  void readArrayFrom(InputStream paramInputStream, int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException;
  
  void writeArrayTo(OutputStream paramOutputStream, int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException;
  
  byte[] getMetaCoding(Coding paramCoding);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/CodingMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */