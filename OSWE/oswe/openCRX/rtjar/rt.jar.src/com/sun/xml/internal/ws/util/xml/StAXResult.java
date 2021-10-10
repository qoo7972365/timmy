/*    */ package com.sun.xml.internal.ws.util.xml;
/*    */ 
/*    */ import javax.xml.stream.XMLStreamWriter;
/*    */ import javax.xml.transform.sax.SAXResult;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StAXResult
/*    */   extends SAXResult
/*    */ {
/*    */   public StAXResult(XMLStreamWriter writer) {
/* 83 */     if (writer == null) {
/* 84 */       throw new IllegalArgumentException();
/*    */     }
/*    */     
/* 87 */     setHandler(new ContentHandlerToXMLStreamWriter(writer));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/xml/StAXResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */