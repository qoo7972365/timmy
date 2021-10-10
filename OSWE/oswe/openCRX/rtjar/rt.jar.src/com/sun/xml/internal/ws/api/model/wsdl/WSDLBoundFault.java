package com.sun.xml.internal.ws.api.model.wsdl;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import javax.xml.namespace.QName;

public interface WSDLBoundFault extends WSDLObject, WSDLExtensible {
  @NotNull
  String getName();
  
  @Nullable
  QName getQName();
  
  @Nullable
  WSDLFault getFault();
  
  @NotNull
  WSDLBoundOperation getBoundOperation();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/model/wsdl/WSDLBoundFault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */