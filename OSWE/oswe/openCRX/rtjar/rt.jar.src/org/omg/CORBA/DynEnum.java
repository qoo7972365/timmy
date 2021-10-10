package org.omg.CORBA;

@Deprecated
public interface DynEnum extends Object, DynAny {
  String value_as_string();
  
  void value_as_string(String paramString);
  
  int value_as_ulong();
  
  void value_as_ulong(int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/DynEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */