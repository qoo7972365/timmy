package com.sun.xml.internal.bind.v2.schemagen.xmlschema;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import javax.xml.namespace.QName;

@XmlElement("list")
public interface List extends Annotated, SimpleTypeHost, TypedXmlWriter {
  @XmlAttribute
  List itemType(QName paramQName);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/schemagen/xmlschema/List.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */