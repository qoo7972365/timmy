package com.sun.corba.se.spi.presentation.rmi;

import java.rmi.RemoteException;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.OutputStream;

public interface DynamicStub extends Object {
  void setDelegate(Delegate paramDelegate);
  
  Delegate getDelegate();
  
  ORB getORB();
  
  String[] getTypeIds();
  
  void connect(ORB paramORB) throws RemoteException;
  
  boolean isLocal();
  
  OutputStream request(String paramString, boolean paramBoolean);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/presentation/rmi/DynamicStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */