package java.security.cert;

public interface CertSelector extends Cloneable {
  boolean match(Certificate paramCertificate);
  
  Object clone();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/CertSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */