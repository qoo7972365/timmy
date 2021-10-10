package java.security.cert;

import java.util.Set;

public interface X509Extension {
  boolean hasUnsupportedCriticalExtension();
  
  Set<String> getCriticalExtensionOIDs();
  
  Set<String> getNonCriticalExtensionOIDs();
  
  byte[] getExtensionValue(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/X509Extension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */