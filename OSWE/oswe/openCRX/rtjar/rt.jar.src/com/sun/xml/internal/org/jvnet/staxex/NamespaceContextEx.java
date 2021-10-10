package com.sun.xml.internal.org.jvnet.staxex;

import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

public interface NamespaceContextEx extends NamespaceContext, Iterable<NamespaceContextEx.Binding> {
  Iterator<Binding> iterator();
  
  public static interface Binding {
    String getPrefix();
    
    String getNamespaceURI();
  }
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/staxex/NamespaceContextEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */