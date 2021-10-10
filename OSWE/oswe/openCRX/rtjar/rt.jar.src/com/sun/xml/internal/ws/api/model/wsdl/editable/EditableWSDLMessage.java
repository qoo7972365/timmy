package com.sun.xml.internal.ws.api.model.wsdl.editable;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLMessage;

public interface EditableWSDLMessage extends WSDLMessage {
  Iterable<? extends EditableWSDLPart> parts();
  
  void add(EditableWSDLPart paramEditableWSDLPart);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/model/wsdl/editable/EditableWSDLMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */