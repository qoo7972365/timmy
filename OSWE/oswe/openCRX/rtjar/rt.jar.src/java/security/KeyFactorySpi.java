package java.security;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public abstract class KeyFactorySpi {
  protected abstract PublicKey engineGeneratePublic(KeySpec paramKeySpec) throws InvalidKeySpecException;
  
  protected abstract PrivateKey engineGeneratePrivate(KeySpec paramKeySpec) throws InvalidKeySpecException;
  
  protected abstract <T extends KeySpec> T engineGetKeySpec(Key paramKey, Class<T> paramClass) throws InvalidKeySpecException;
  
  protected abstract Key engineTranslateKey(Key paramKey) throws InvalidKeyException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/KeyFactorySpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */