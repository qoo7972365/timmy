package com.sun.org.apache.xalan.internal.xsltc;

public interface DOMEnhancedForDTM extends DOM {
  short[] getMapping(String[] paramArrayOfString1, String[] paramArrayOfString2, int[] paramArrayOfint);
  
  int[] getReverseMapping(String[] paramArrayOfString1, String[] paramArrayOfString2, int[] paramArrayOfint);
  
  short[] getNamespaceMapping(String[] paramArrayOfString);
  
  short[] getReverseNamespaceMapping(String[] paramArrayOfString);
  
  String getDocumentURI();
  
  void setDocumentURI(String paramString);
  
  int getExpandedTypeID2(int paramInt);
  
  boolean hasDOMSource();
  
  int getElementById(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/DOMEnhancedForDTM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */