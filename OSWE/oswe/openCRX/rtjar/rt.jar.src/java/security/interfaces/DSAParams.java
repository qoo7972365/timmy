package java.security.interfaces;

import java.math.BigInteger;

public interface DSAParams {
  BigInteger getP();
  
  BigInteger getQ();
  
  BigInteger getG();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/interfaces/DSAParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */