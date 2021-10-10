package com.sun.xml.internal.org.jvnet.fastinfoset.sax;

import org.xml.sax.Attributes;

public interface EncodingAlgorithmAttributes extends Attributes {
  String getAlgorithmURI(int paramInt);
  
  int getAlgorithmIndex(int paramInt);
  
  Object getAlgorithmData(int paramInt);
  
  String getAlpababet(int paramInt);
  
  boolean getToIndex(int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/fastinfoset/sax/EncodingAlgorithmAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */