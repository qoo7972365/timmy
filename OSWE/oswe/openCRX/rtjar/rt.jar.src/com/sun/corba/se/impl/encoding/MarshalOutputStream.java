package com.sun.corba.se.impl.encoding;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.Principal;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;

public interface MarshalOutputStream {
  InputStream create_input_stream();
  
  void write_boolean(boolean paramBoolean);
  
  void write_char(char paramChar);
  
  void write_wchar(char paramChar);
  
  void write_octet(byte paramByte);
  
  void write_short(short paramShort);
  
  void write_ushort(short paramShort);
  
  void write_long(int paramInt);
  
  void write_ulong(int paramInt);
  
  void write_longlong(long paramLong);
  
  void write_ulonglong(long paramLong);
  
  void write_float(float paramFloat);
  
  void write_double(double paramDouble);
  
  void write_string(String paramString);
  
  void write_wstring(String paramString);
  
  void write_boolean_array(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2);
  
  void write_char_array(char[] paramArrayOfchar, int paramInt1, int paramInt2);
  
  void write_wchar_array(char[] paramArrayOfchar, int paramInt1, int paramInt2);
  
  void write_octet_array(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  void write_short_array(short[] paramArrayOfshort, int paramInt1, int paramInt2);
  
  void write_ushort_array(short[] paramArrayOfshort, int paramInt1, int paramInt2);
  
  void write_long_array(int[] paramArrayOfint, int paramInt1, int paramInt2);
  
  void write_ulong_array(int[] paramArrayOfint, int paramInt1, int paramInt2);
  
  void write_longlong_array(long[] paramArrayOflong, int paramInt1, int paramInt2);
  
  void write_ulonglong_array(long[] paramArrayOflong, int paramInt1, int paramInt2);
  
  void write_float_array(float[] paramArrayOffloat, int paramInt1, int paramInt2);
  
  void write_double_array(double[] paramArrayOfdouble, int paramInt1, int paramInt2);
  
  void write_Object(Object paramObject);
  
  void write_TypeCode(TypeCode paramTypeCode);
  
  void write_any(Any paramAny);
  
  void write_Principal(Principal paramPrincipal);
  
  void write_value(Serializable paramSerializable);
  
  void start_block();
  
  void end_block();
  
  void putEndian();
  
  void writeTo(OutputStream paramOutputStream) throws IOException;
  
  byte[] toByteArray();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/MarshalOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */