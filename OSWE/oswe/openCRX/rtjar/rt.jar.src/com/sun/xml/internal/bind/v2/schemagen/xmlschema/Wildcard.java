package com.sun.xml.internal.bind.v2.schemagen.xmlschema;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;

public interface Wildcard extends Annotated, TypedXmlWriter {
  @XmlAttribute
  Wildcard processContents(String paramString);
  
  @XmlAttribute
  Wildcard namespace(String[] paramArrayOfString);
  
  @XmlAttribute
  Wildcard namespace(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/schemagen/xmlschema/Wildcard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */