/*    */ package com.sun.xml.internal.ws.streaming;
/*    */ 
/*    */ import com.sun.istack.internal.localization.Localizable;
/*    */ import com.sun.xml.internal.ws.util.exception.JAXWSExceptionBase;
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
/*    */ 
/*    */ public class XMLStreamReaderException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public XMLStreamReaderException(String key, Object... args) {
/* 42 */     super(key, args);
/*    */   }
/*    */   
/*    */   public XMLStreamReaderException(Throwable throwable) {
/* 46 */     super(throwable);
/*    */   }
/*    */   
/*    */   public XMLStreamReaderException(Localizable arg) {
/* 50 */     super("xmlreader.nestedError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 54 */     return "com.sun.xml.internal.ws.resources.streaming";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/streaming/XMLStreamReaderException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */