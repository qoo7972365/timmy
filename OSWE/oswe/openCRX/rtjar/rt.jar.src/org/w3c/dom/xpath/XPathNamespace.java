package org.w3c.dom.xpath;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface XPathNamespace extends Node {
  public static final short XPATH_NAMESPACE_NODE = 13;
  
  Element getOwnerElement();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/xpath/XPathNamespace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */