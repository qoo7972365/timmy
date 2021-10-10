package com.sun.xml.internal.ws.wsdl.writer.document;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlElement("service")
public interface Service extends TypedXmlWriter, Documented {
  @XmlAttribute
  Service name(String paramString);
  
  @XmlElement
  Port port();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/writer/document/Service.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */