package javax.xml.crypto.dsig.keyinfo;

import java.util.List;
import javax.xml.crypto.XMLStructure;

public interface PGPData extends XMLStructure {
  public static final String TYPE = "http://www.w3.org/2000/09/xmldsig#PGPData";
  
  byte[] getKeyId();
  
  byte[] getKeyPacket();
  
  List getExternalElements();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dsig/keyinfo/PGPData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */