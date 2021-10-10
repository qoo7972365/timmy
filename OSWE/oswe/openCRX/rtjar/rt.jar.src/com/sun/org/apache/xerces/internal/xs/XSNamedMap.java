package com.sun.org.apache.xerces.internal.xs;

import java.util.Map;

public interface XSNamedMap extends Map {
  int getLength();
  
  XSObject item(int paramInt);
  
  XSObject itemByName(String paramString1, String paramString2);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/XSNamedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */