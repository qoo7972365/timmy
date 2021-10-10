package com.sun.org.apache.xalan.internal.extensions;

import com.sun.org.apache.xml.internal.utils.QName;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.objects.XObject;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public interface ExpressionContext {
  Node getContextNode();
  
  NodeIterator getContextNodes();
  
  ErrorListener getErrorListener();
  
  double toNumber(Node paramNode);
  
  String toString(Node paramNode);
  
  XObject getVariableOrParam(QName paramQName) throws TransformerException;
  
  XPathContext getXPathContext() throws TransformerException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/extensions/ExpressionContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */