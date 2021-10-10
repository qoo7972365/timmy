package javax.net.ssl;

import java.util.Enumeration;

public interface SSLSessionContext {
  SSLSession getSession(byte[] paramArrayOfbyte);
  
  Enumeration<byte[]> getIds();
  
  void setSessionTimeout(int paramInt) throws IllegalArgumentException;
  
  int getSessionTimeout();
  
  void setSessionCacheSize(int paramInt) throws IllegalArgumentException;
  
  int getSessionCacheSize();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/SSLSessionContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */