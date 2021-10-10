package com.sun.org.apache.xml.internal.security.encryption;

public interface CipherData {
  public static final int VALUE_TYPE = 1;
  
  public static final int REFERENCE_TYPE = 2;
  
  int getDataType();
  
  CipherValue getCipherValue();
  
  void setCipherValue(CipherValue paramCipherValue) throws XMLEncryptionException;
  
  CipherReference getCipherReference();
  
  void setCipherReference(CipherReference paramCipherReference) throws XMLEncryptionException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/encryption/CipherData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */