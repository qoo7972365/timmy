package java.security.cert;

import java.util.Iterator;
import java.util.Set;

public interface PolicyNode {
  PolicyNode getParent();
  
  Iterator<? extends PolicyNode> getChildren();
  
  int getDepth();
  
  String getValidPolicy();
  
  Set<? extends PolicyQualifierInfo> getPolicyQualifiers();
  
  Set<String> getExpectedPolicies();
  
  boolean isCritical();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/PolicyNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */