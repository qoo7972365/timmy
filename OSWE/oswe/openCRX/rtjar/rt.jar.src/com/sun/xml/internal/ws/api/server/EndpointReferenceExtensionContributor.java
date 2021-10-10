package com.sun.xml.internal.ws.api.server;

import com.sun.istack.internal.Nullable;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import javax.xml.namespace.QName;

public abstract class EndpointReferenceExtensionContributor {
  public abstract WSEndpointReference.EPRExtension getEPRExtension(WSEndpoint paramWSEndpoint, @Nullable WSEndpointReference.EPRExtension paramEPRExtension);
  
  public abstract QName getQName();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/EndpointReferenceExtensionContributor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */