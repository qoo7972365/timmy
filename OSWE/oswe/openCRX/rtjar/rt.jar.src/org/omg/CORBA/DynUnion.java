package org.omg.CORBA;

@Deprecated
public interface DynUnion extends Object, DynAny {
  boolean set_as_default();
  
  void set_as_default(boolean paramBoolean);
  
  DynAny discriminator();
  
  TCKind discriminator_kind();
  
  DynAny member();
  
  String member_name();
  
  void member_name(String paramString);
  
  TCKind member_kind();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/DynUnion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */