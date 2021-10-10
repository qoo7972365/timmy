package javax.xml.xpath;

import java.util.List;

public interface XPathFunction {
  Object evaluate(List paramList) throws XPathFunctionException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/xpath/XPathFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */