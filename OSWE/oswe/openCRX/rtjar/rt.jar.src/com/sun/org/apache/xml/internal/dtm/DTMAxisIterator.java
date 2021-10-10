package com.sun.org.apache.xml.internal.dtm;

public interface DTMAxisIterator extends Cloneable {
  public static final int END = -1;
  
  int next();
  
  DTMAxisIterator reset();
  
  int getLast();
  
  int getPosition();
  
  void setMark();
  
  void gotoMark();
  
  DTMAxisIterator setStartNode(int paramInt);
  
  int getStartNode();
  
  boolean isReverse();
  
  DTMAxisIterator cloneIterator();
  
  void setRestartable(boolean paramBoolean);
  
  int getNodeByPosition(int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/dtm/DTMAxisIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */