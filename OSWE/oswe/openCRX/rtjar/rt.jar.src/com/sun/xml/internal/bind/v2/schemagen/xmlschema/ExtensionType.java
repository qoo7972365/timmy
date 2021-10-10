package com.sun.xml.internal.bind.v2.schemagen.xmlschema;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import javax.xml.namespace.QName;

public interface ExtensionType extends Annotated, TypedXmlWriter {
  @XmlAttribute
  ExtensionType base(QName paramQName);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/schemagen/xmlschema/ExtensionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */