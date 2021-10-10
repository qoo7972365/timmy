package org.w3c.dom;

public interface ProcessingInstruction extends Node {
  String getTarget();
  
  String getData();
  
  void setData(String paramString) throws DOMException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/ProcessingInstruction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */