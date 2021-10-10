package com.sun.org.apache.xerces.internal.xs.datatypes;

import java.util.List;

public interface ObjectList extends List {
  int getLength();
  
  boolean contains(Object paramObject);
  
  Object item(int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/datatypes/ObjectList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */