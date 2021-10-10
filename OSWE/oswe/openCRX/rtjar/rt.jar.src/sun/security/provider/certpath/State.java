package sun.security.provider.certpath;

import java.io.IOException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

interface State extends Cloneable {
  void updateState(X509Certificate paramX509Certificate) throws CertificateException, IOException, CertPathValidatorException;
  
  Object clone();
  
  boolean isInitial();
  
  boolean keyParamsNeeded();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/State.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */