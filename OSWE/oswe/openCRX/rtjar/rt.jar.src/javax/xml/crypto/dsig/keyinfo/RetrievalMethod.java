package javax.xml.crypto.dsig.keyinfo;

import java.util.List;
import javax.xml.crypto.Data;
import javax.xml.crypto.URIReference;
import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;

public interface RetrievalMethod extends URIReference, XMLStructure {
  List getTransforms();
  
  String getURI();
  
  Data dereference(XMLCryptoContext paramXMLCryptoContext) throws URIReferenceException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dsig/keyinfo/RetrievalMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */