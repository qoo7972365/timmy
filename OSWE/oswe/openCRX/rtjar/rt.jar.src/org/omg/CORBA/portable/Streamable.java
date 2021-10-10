package org.omg.CORBA.portable;

import org.omg.CORBA.TypeCode;

public interface Streamable {
  void _read(InputStream paramInputStream);
  
  void _write(OutputStream paramOutputStream);
  
  TypeCode _type();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/portable/Streamable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */