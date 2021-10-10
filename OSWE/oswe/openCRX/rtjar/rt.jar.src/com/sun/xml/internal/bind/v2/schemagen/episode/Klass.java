package com.sun.xml.internal.bind.v2.schemagen.episode;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;

public interface Klass extends TypedXmlWriter {
  @XmlAttribute
  void ref(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/schemagen/episode/Klass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */