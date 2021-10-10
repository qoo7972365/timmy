package javax.xml.crypto.dsig;

import java.util.List;
import javax.xml.crypto.XMLStructure;

public interface SignatureProperty extends XMLStructure {
  String getTarget();
  
  String getId();
  
  List getContent();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dsig/SignatureProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */