package com.sun.org.apache.xerces.internal.xni;

import java.util.Enumeration;

public interface Augmentations {
  Object putItem(String paramString, Object paramObject);
  
  Object getItem(String paramString);
  
  Object removeItem(String paramString);
  
  Enumeration keys();
  
  void removeAllItems();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xni/Augmentations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */