package javax.security.sasl;

public interface SaslServer {
  String getMechanismName();
  
  byte[] evaluateResponse(byte[] paramArrayOfbyte) throws SaslException;
  
  boolean isComplete();
  
  String getAuthorizationID();
  
  byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException;
  
  byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException;
  
  Object getNegotiatedProperty(String paramString);
  
  void dispose() throws SaslException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/sasl/SaslServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */