package javax.security.sasl;

public interface SaslClient {
  String getMechanismName();
  
  boolean hasInitialResponse();
  
  byte[] evaluateChallenge(byte[] paramArrayOfbyte) throws SaslException;
  
  boolean isComplete();
  
  byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException;
  
  byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException;
  
  Object getNegotiatedProperty(String paramString);
  
  void dispose() throws SaslException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/sasl/SaslClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */