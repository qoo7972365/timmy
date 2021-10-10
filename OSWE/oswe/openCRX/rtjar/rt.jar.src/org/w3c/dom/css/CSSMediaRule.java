package org.w3c.dom.css;

import org.w3c.dom.DOMException;
import org.w3c.dom.stylesheets.MediaList;

public interface CSSMediaRule extends CSSRule {
  MediaList getMedia();
  
  CSSRuleList getCssRules();
  
  int insertRule(String paramString, int paramInt) throws DOMException;
  
  void deleteRule(int paramInt) throws DOMException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/css/CSSMediaRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */