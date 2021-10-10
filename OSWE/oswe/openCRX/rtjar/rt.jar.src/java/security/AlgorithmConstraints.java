package java.security;

import java.util.Set;

public interface AlgorithmConstraints {
  boolean permits(Set<CryptoPrimitive> paramSet, String paramString, AlgorithmParameters paramAlgorithmParameters);
  
  boolean permits(Set<CryptoPrimitive> paramSet, Key paramKey);
  
  boolean permits(Set<CryptoPrimitive> paramSet, String paramString, Key paramKey, AlgorithmParameters paramAlgorithmParameters);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/AlgorithmConstraints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */