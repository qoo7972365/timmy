package javax.xml.xpath;

import javax.xml.namespace.QName;

public interface XPathFunctionResolver {
  XPathFunction resolveFunction(QName paramQName, int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/xpath/XPathFunctionResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */