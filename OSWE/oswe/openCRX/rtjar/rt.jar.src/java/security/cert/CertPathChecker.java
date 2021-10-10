package java.security.cert;

public interface CertPathChecker {
  void init(boolean paramBoolean) throws CertPathValidatorException;
  
  boolean isForwardCheckingSupported();
  
  void check(Certificate paramCertificate) throws CertPathValidatorException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/CertPathChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */