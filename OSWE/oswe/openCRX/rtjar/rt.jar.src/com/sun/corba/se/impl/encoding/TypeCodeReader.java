package com.sun.corba.se.impl.encoding;

import com.sun.corba.se.impl.corba.TypeCodeImpl;
import org.omg.CORBA_2_3.portable.InputStream;

public interface TypeCodeReader extends MarshalInputStream {
  void addTypeCodeAtPosition(TypeCodeImpl paramTypeCodeImpl, int paramInt);
  
  TypeCodeImpl getTypeCodeAtPosition(int paramInt);
  
  void setEnclosingInputStream(InputStream paramInputStream);
  
  TypeCodeReader getTopLevelStream();
  
  int getTopLevelPosition();
  
  int getPosition();
  
  void printTypeMap();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/TypeCodeReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */