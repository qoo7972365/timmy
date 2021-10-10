package com.sun.org.apache.xml.internal.dtm;

public interface DTMWSFilter {
  public static final short NOTSTRIP = 1;
  
  public static final short STRIP = 2;
  
  public static final short INHERIT = 3;
  
  short getShouldStripSpace(int paramInt, DTM paramDTM);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/dtm/DTMWSFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */