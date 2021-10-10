package sun.corba;

import com.sun.corba.se.impl.io.ValueHandlerImpl;

public interface JavaCorbaAccess {
  ValueHandlerImpl newValueHandlerImpl();
  
  Class<?> loadClass(String paramString) throws ClassNotFoundException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/corba/JavaCorbaAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */