package com.sun.corba.se.spi.activation;

import com.sun.corba.se.spi.activation.RepositoryPackage.ServerDef;

public interface RepositoryOperations {
  int registerServer(ServerDef paramServerDef) throws ServerAlreadyRegistered, BadServerDefinition;
  
  void unregisterServer(int paramInt) throws ServerNotRegistered;
  
  ServerDef getServer(int paramInt) throws ServerNotRegistered;
  
  boolean isInstalled(int paramInt) throws ServerNotRegistered;
  
  void install(int paramInt) throws ServerNotRegistered, ServerAlreadyInstalled;
  
  void uninstall(int paramInt) throws ServerNotRegistered, ServerAlreadyUninstalled;
  
  int[] listRegisteredServers();
  
  String[] getApplicationNames();
  
  int getServerID(String paramString) throws ServerNotRegistered;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/RepositoryOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */