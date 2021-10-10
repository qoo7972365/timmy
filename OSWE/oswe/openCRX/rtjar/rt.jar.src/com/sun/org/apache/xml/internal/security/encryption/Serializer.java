package com.sun.org.apache.xml.internal.security.encryption;

import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public interface Serializer {
  void setCanonicalizer(Canonicalizer paramCanonicalizer);
  
  byte[] serializeToByteArray(Element paramElement) throws Exception;
  
  byte[] serializeToByteArray(NodeList paramNodeList) throws Exception;
  
  byte[] canonSerializeToByteArray(Node paramNode) throws Exception;
  
  Node deserialize(byte[] paramArrayOfbyte, Node paramNode) throws XMLEncryptionException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/encryption/Serializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */