package org.omg.CORBA;

import org.omg.CORBA.DynAnyPackage.InvalidSeq;

@Deprecated
public interface DynSequence extends Object, DynAny {
  int length();
  
  void length(int paramInt);
  
  Any[] get_elements();
  
  void set_elements(Any[] paramArrayOfAny) throws InvalidSeq;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/DynSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */