package com.sun.org.apache.xml.internal.security.signature;

import org.w3c.dom.Node;

public interface NodeFilter {
  int isNodeInclude(Node paramNode);
  
  int isNodeIncludeDO(Node paramNode, int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/signature/NodeFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */