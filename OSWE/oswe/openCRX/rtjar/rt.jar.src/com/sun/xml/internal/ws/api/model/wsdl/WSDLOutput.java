package com.sun.xml.internal.ws.api.model.wsdl;

import com.sun.istack.internal.NotNull;
import javax.xml.namespace.QName;

public interface WSDLOutput extends WSDLObject, WSDLExtensible {
  String getName();
  
  WSDLMessage getMessage();
  
  String getAction();
  
  @NotNull
  WSDLOperation getOperation();
  
  @NotNull
  QName getQName();
  
  boolean isDefaultAction();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/model/wsdl/WSDLOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */