package org.w3c.dom;

public interface DOMConfiguration {
  void setParameter(String paramString, Object paramObject) throws DOMException;
  
  Object getParameter(String paramString) throws DOMException;
  
  boolean canSetParameter(String paramString, Object paramObject);
  
  DOMStringList getParameterNames();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/DOMConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */