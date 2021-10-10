package com.sun.xml.internal.ws.policy;

interface PolicyMapKeyHandler {
  boolean areEqual(PolicyMapKey paramPolicyMapKey1, PolicyMapKey paramPolicyMapKey2);
  
  int generateHashCode(PolicyMapKey paramPolicyMapKey);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/PolicyMapKeyHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */