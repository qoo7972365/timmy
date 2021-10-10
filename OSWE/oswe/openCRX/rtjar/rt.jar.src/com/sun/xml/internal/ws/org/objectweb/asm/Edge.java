package com.sun.xml.internal.ws.org.objectweb.asm;

class Edge {
  static final int NORMAL = 0;
  
  static final int EXCEPTION = 2147483647;
  
  int info;
  
  Label successor;
  
  Edge next;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/org/objectweb/asm/Edge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */