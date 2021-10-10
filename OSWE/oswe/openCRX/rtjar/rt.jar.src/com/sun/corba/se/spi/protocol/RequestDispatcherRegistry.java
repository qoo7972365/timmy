package com.sun.corba.se.spi.protocol;

import com.sun.corba.se.pept.protocol.ClientRequestDispatcher;
import com.sun.corba.se.spi.oa.ObjectAdapterFactory;
import java.util.Set;

public interface RequestDispatcherRegistry {
  void registerClientRequestDispatcher(ClientRequestDispatcher paramClientRequestDispatcher, int paramInt);
  
  ClientRequestDispatcher getClientRequestDispatcher(int paramInt);
  
  void registerLocalClientRequestDispatcherFactory(LocalClientRequestDispatcherFactory paramLocalClientRequestDispatcherFactory, int paramInt);
  
  LocalClientRequestDispatcherFactory getLocalClientRequestDispatcherFactory(int paramInt);
  
  void registerServerRequestDispatcher(CorbaServerRequestDispatcher paramCorbaServerRequestDispatcher, int paramInt);
  
  CorbaServerRequestDispatcher getServerRequestDispatcher(int paramInt);
  
  void registerServerRequestDispatcher(CorbaServerRequestDispatcher paramCorbaServerRequestDispatcher, String paramString);
  
  CorbaServerRequestDispatcher getServerRequestDispatcher(String paramString);
  
  void registerObjectAdapterFactory(ObjectAdapterFactory paramObjectAdapterFactory, int paramInt);
  
  ObjectAdapterFactory getObjectAdapterFactory(int paramInt);
  
  Set<ObjectAdapterFactory> getObjectAdapterFactories();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/protocol/RequestDispatcherRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */