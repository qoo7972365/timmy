package com.sun.org.apache.xerces.internal.xs;

import java.util.List;

public interface StringList extends List {
  int getLength();
  
  boolean contains(String paramString);
  
  String item(int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/StringList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */