package org.omg.IOP;

import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.IOP.CodecPackage.FormatMismatch;
import org.omg.IOP.CodecPackage.InvalidTypeForEncoding;
import org.omg.IOP.CodecPackage.TypeMismatch;

public interface CodecOperations {
  byte[] encode(Any paramAny) throws InvalidTypeForEncoding;
  
  Any decode(byte[] paramArrayOfbyte) throws FormatMismatch;
  
  byte[] encode_value(Any paramAny) throws InvalidTypeForEncoding;
  
  Any decode_value(byte[] paramArrayOfbyte, TypeCode paramTypeCode) throws FormatMismatch, TypeMismatch;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/CodecOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */