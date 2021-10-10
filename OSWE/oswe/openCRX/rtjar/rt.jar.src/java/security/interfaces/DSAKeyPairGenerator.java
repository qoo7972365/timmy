package java.security.interfaces;

import java.security.InvalidParameterException;
import java.security.SecureRandom;

public interface DSAKeyPairGenerator {
  void initialize(DSAParams paramDSAParams, SecureRandom paramSecureRandom) throws InvalidParameterException;
  
  void initialize(int paramInt, boolean paramBoolean, SecureRandom paramSecureRandom) throws InvalidParameterException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/interfaces/DSAKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */