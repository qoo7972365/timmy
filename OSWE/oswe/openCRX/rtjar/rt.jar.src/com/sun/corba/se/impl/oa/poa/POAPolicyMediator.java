package com.sun.corba.se.impl.oa.poa;

import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POAPackage.NoServant;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantManager;

public interface POAPolicyMediator {
  Policies getPolicies();
  
  int getScid();
  
  int getServerId();
  
  Object getInvocationServant(byte[] paramArrayOfbyte, String paramString) throws ForwardRequest;
  
  void returnServant();
  
  void etherealizeAll();
  
  void clearAOM();
  
  ServantManager getServantManager() throws WrongPolicy;
  
  void setServantManager(ServantManager paramServantManager) throws WrongPolicy;
  
  Servant getDefaultServant() throws NoServant, WrongPolicy;
  
  void setDefaultServant(Servant paramServant) throws WrongPolicy;
  
  void activateObject(byte[] paramArrayOfbyte, Servant paramServant) throws ObjectAlreadyActive, ServantAlreadyActive, WrongPolicy;
  
  Servant deactivateObject(byte[] paramArrayOfbyte) throws ObjectNotActive, WrongPolicy;
  
  byte[] newSystemId() throws WrongPolicy;
  
  byte[] servantToId(Servant paramServant) throws ServantNotActive, WrongPolicy;
  
  Servant idToServant(byte[] paramArrayOfbyte) throws ObjectNotActive, WrongPolicy;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/POAPolicyMediator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */