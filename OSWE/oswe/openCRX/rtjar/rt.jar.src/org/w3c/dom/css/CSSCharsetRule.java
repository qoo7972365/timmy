package org.w3c.dom.css;

import org.w3c.dom.DOMException;

public interface CSSCharsetRule extends CSSRule {
  String getEncoding();
  
  void setEncoding(String paramString) throws DOMException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/css/CSSCharsetRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */