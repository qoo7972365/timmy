package org.omg.PortableServer;

import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;

public interface ServantLocatorOperations extends ServantManagerOperations {
  Servant preinvoke(byte[] paramArrayOfbyte, POA paramPOA, String paramString, CookieHolder paramCookieHolder) throws ForwardRequest;
  
  void postinvoke(byte[] paramArrayOfbyte, POA paramPOA, String paramString, Object paramObject, Servant paramServant);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/ServantLocatorOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */