package com.sun.xml.internal.ws.wsdl.writer.document.http;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlElement("binding")
public interface Binding extends TypedXmlWriter {
  @XmlAttribute
  Binding verb(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/writer/document/http/Binding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */