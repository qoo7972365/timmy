package org.omg.CORBA;

public abstract class NVList {
  public abstract int count();
  
  public abstract NamedValue add(int paramInt);
  
  public abstract NamedValue add_item(String paramString, int paramInt);
  
  public abstract NamedValue add_value(String paramString, Any paramAny, int paramInt);
  
  public abstract NamedValue item(int paramInt) throws Bounds;
  
  public abstract void remove(int paramInt) throws Bounds;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/NVList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */