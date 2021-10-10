package com.sun.xml.internal.bind.v2.schemagen.xmlschema;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import javax.xml.namespace.QName;

public interface AttributeType extends SimpleTypeHost, TypedXmlWriter {
  @XmlAttribute
  AttributeType type(QName paramQName);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/schemagen/xmlschema/AttributeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */