package org.xml.sax;

public interface AttributeList {
  int getLength();
  
  String getName(int paramInt);
  
  String getType(int paramInt);
  
  String getValue(int paramInt);
  
  String getType(String paramString);
  
  String getValue(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/xml/sax/AttributeList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */