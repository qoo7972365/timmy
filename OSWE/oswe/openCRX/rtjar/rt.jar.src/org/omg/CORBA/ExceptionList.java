package org.omg.CORBA;

public abstract class ExceptionList {
  public abstract int count();
  
  public abstract void add(TypeCode paramTypeCode);
  
  public abstract TypeCode item(int paramInt) throws Bounds;
  
  public abstract void remove(int paramInt) throws Bounds;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/ExceptionList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */