package com.sun.xml.internal.ws.api.databinding;

import com.oracle.webservices.internal.api.databinding.JavaCallInfo;
import com.sun.xml.internal.ws.api.message.Packet;
import com.sun.xml.internal.ws.api.model.JavaMethod;

public interface EndpointCallBridge {
  JavaCallInfo deserializeRequest(Packet paramPacket);
  
  Packet serializeResponse(JavaCallInfo paramJavaCallInfo);
  
  JavaMethod getOperationModel();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/databinding/EndpointCallBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */