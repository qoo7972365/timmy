package org.xml.sax;

import java.io.IOException;

public interface EntityResolver {
  InputSource resolveEntity(String paramString1, String paramString2) throws SAXException, IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/xml/sax/EntityResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */