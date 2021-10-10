package com.sun.xml.internal.ws.api.model.wsdl;

import com.sun.xml.internal.ws.api.model.ParameterBinding;

public interface WSDLPart extends WSDLObject {
  String getName();
  
  ParameterBinding getBinding();
  
  int getIndex();
  
  WSDLPartDescriptor getDescriptor();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/model/wsdl/WSDLPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */