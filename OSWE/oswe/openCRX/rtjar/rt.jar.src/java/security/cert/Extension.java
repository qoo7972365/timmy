package java.security.cert;

import java.io.IOException;
import java.io.OutputStream;

public interface Extension {
  String getId();
  
  boolean isCritical();
  
  byte[] getValue();
  
  void encode(OutputStream paramOutputStream) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/Extension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */