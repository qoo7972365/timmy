package java.security.cert;

public interface CRLSelector extends Cloneable {
  boolean match(CRL paramCRL);
  
  Object clone();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/CRLSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */