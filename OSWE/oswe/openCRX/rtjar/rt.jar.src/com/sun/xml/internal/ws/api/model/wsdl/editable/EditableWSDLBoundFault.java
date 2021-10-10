package com.sun.xml.internal.ws.api.model.wsdl.editable;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundFault;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLFault;

public interface EditableWSDLBoundFault extends WSDLBoundFault {
  @Nullable
  EditableWSDLFault getFault();
  
  @NotNull
  EditableWSDLBoundOperation getBoundOperation();
  
  void freeze(EditableWSDLBoundOperation paramEditableWSDLBoundOperation);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/model/wsdl/editable/EditableWSDLBoundFault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */