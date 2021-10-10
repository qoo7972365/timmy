package com.sun.org.apache.xml.internal.security.encryption;

import java.util.Iterator;
import org.w3c.dom.Element;

public interface EncryptionProperty {
  String getTarget();
  
  void setTarget(String paramString);
  
  String getId();
  
  void setId(String paramString);
  
  String getAttribute(String paramString);
  
  void setAttribute(String paramString1, String paramString2);
  
  Iterator<Element> getEncryptionInformation();
  
  void addEncryptionInformation(Element paramElement);
  
  void removeEncryptionInformation(Element paramElement);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/encryption/EncryptionProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */