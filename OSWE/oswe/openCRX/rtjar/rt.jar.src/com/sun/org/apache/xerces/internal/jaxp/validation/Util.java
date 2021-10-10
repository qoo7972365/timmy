/*    */ package com.sun.org.apache.xerces.internal.jaxp.validation;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*    */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*    */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
/*    */ import javax.xml.transform.stream.StreamSource;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.SAXParseException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Util
/*    */ {
/*    */   public static final XMLInputSource toXMLInputSource(StreamSource in) {
/* 44 */     if (in.getReader() != null)
/* 45 */       return new XMLInputSource(in
/* 46 */           .getPublicId(), in.getSystemId(), in.getSystemId(), in
/* 47 */           .getReader(), null); 
/* 48 */     if (in.getInputStream() != null) {
/* 49 */       return new XMLInputSource(in
/* 50 */           .getPublicId(), in.getSystemId(), in.getSystemId(), in
/* 51 */           .getInputStream(), null);
/*    */     }
/* 53 */     return new XMLInputSource(in
/* 54 */         .getPublicId(), in.getSystemId(), in.getSystemId());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SAXException toSAXException(XNIException e) {
/* 61 */     if (e instanceof XMLParseException)
/* 62 */       return toSAXParseException((XMLParseException)e); 
/* 63 */     if (e.getException() instanceof SAXException)
/* 64 */       return (SAXException)e.getException(); 
/* 65 */     return new SAXException(e.getMessage(), e.getException());
/*    */   }
/*    */   
/*    */   public static SAXParseException toSAXParseException(XMLParseException e) {
/* 69 */     if (e.getException() instanceof SAXParseException)
/* 70 */       return (SAXParseException)e.getException(); 
/* 71 */     return new SAXParseException(e.getMessage(), e
/* 72 */         .getPublicId(), e.getExpandedSystemId(), e
/* 73 */         .getLineNumber(), e.getColumnNumber(), e
/* 74 */         .getException());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */