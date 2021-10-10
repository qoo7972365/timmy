package javax.xml.crypto;

public interface XMLCryptoContext {
  String getBaseURI();
  
  void setBaseURI(String paramString);
  
  KeySelector getKeySelector();
  
  void setKeySelector(KeySelector paramKeySelector);
  
  URIDereferencer getURIDereferencer();
  
  void setURIDereferencer(URIDereferencer paramURIDereferencer);
  
  String getNamespacePrefix(String paramString1, String paramString2);
  
  String putNamespacePrefix(String paramString1, String paramString2);
  
  String getDefaultNamespacePrefix();
  
  void setDefaultNamespacePrefix(String paramString);
  
  Object setProperty(String paramString, Object paramObject);
  
  Object getProperty(String paramString);
  
  Object get(Object paramObject);
  
  Object put(Object paramObject1, Object paramObject2);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/XMLCryptoContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */