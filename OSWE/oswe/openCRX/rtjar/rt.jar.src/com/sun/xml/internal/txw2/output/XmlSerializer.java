package com.sun.xml.internal.txw2.output;

public interface XmlSerializer {
  void startDocument();
  
  void beginStartTag(String paramString1, String paramString2, String paramString3);
  
  void writeAttribute(String paramString1, String paramString2, String paramString3, StringBuilder paramStringBuilder);
  
  void writeXmlns(String paramString1, String paramString2);
  
  void endStartTag(String paramString1, String paramString2, String paramString3);
  
  void endTag();
  
  void text(StringBuilder paramStringBuilder);
  
  void cdata(StringBuilder paramStringBuilder);
  
  void comment(StringBuilder paramStringBuilder);
  
  void endDocument();
  
  void flush();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/output/XmlSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */