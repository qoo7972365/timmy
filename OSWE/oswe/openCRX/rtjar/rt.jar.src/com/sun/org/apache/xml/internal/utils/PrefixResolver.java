package com.sun.org.apache.xml.internal.utils;

import org.w3c.dom.Node;

public interface PrefixResolver {
  String getNamespaceForPrefix(String paramString);
  
  String getNamespaceForPrefix(String paramString, Node paramNode);
  
  String getBaseIdentifier();
  
  boolean handlesNullPrefixes();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/utils/PrefixResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */