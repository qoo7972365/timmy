package com.sun.org.apache.xml.internal.security.encryption;

import com.sun.org.apache.xml.internal.security.keys.KeyInfo;

public interface EncryptedType {
  String getId();
  
  void setId(String paramString);
  
  String getType();
  
  void setType(String paramString);
  
  String getMimeType();
  
  void setMimeType(String paramString);
  
  String getEncoding();
  
  void setEncoding(String paramString);
  
  EncryptionMethod getEncryptionMethod();
  
  void setEncryptionMethod(EncryptionMethod paramEncryptionMethod);
  
  KeyInfo getKeyInfo();
  
  void setKeyInfo(KeyInfo paramKeyInfo);
  
  CipherData getCipherData();
  
  EncryptionProperties getEncryptionProperties();
  
  void setEncryptionProperties(EncryptionProperties paramEncryptionProperties);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/encryption/EncryptedType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */