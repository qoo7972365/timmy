package com.sun.corba.se.spi.activation;

public interface ActivatorOperations {
  void active(int paramInt, Server paramServer) throws ServerNotRegistered;
  
  void registerEndpoints(int paramInt, String paramString, EndPointInfo[] paramArrayOfEndPointInfo) throws ServerNotRegistered, NoSuchEndPoint, ORBAlreadyRegistered;
  
  int[] getActiveServers();
  
  void activate(int paramInt) throws ServerAlreadyActive, ServerNotRegistered, ServerHeldDown;
  
  void shutdown(int paramInt) throws ServerNotActive, ServerNotRegistered;
  
  void install(int paramInt) throws ServerNotRegistered, ServerHeldDown, ServerAlreadyInstalled;
  
  String[] getORBNames(int paramInt) throws ServerNotRegistered;
  
  void uninstall(int paramInt) throws ServerNotRegistered, ServerHeldDown, ServerAlreadyUninstalled;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ActivatorOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */