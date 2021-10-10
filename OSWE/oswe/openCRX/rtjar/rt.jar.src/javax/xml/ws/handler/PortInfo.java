package javax.xml.ws.handler;

import javax.xml.namespace.QName;

public interface PortInfo {
  QName getServiceName();
  
  QName getPortName();
  
  String getBindingID();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/ws/handler/PortInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */