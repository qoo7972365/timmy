package org.omg.CORBA;

public abstract class Environment {
  public abstract Exception exception();
  
  public abstract void exception(Exception paramException);
  
  public abstract void clear();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/Environment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */