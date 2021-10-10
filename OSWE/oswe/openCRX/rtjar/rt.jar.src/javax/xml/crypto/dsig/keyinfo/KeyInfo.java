package javax.xml.crypto.dsig.keyinfo;

import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;

public interface KeyInfo extends XMLStructure {
  List getContent();
  
  String getId();
  
  void marshal(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws MarshalException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dsig/keyinfo/KeyInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */