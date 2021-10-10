package com.sun.jmx.remote.internal;

import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Properties;

public interface IIOPProxy {
  boolean isStub(Object paramObject);
  
  Object getDelegate(Object paramObject);
  
  void setDelegate(Object paramObject1, Object paramObject2);
  
  Object getOrb(Object paramObject);
  
  void connect(Object paramObject1, Object paramObject2) throws RemoteException;
  
  boolean isOrb(Object paramObject);
  
  Object createOrb(String[] paramArrayOfString, Properties paramProperties);
  
  Object stringToObject(Object paramObject, String paramString);
  
  String objectToString(Object paramObject1, Object paramObject2);
  
  <T> T narrow(Object paramObject, Class<T> paramClass);
  
  void exportObject(Remote paramRemote) throws RemoteException;
  
  void unexportObject(Remote paramRemote) throws NoSuchObjectException;
  
  Remote toStub(Remote paramRemote) throws NoSuchObjectException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/internal/IIOPProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */