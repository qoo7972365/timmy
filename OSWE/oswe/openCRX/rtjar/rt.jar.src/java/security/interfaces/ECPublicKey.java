package java.security.interfaces;

import java.security.PublicKey;
import java.security.spec.ECPoint;

public interface ECPublicKey extends PublicKey, ECKey {
  public static final long serialVersionUID = -3314988629879632826L;
  
  ECPoint getW();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/interfaces/ECPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */