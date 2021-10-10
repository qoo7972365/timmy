/*    */ package com.sun.xml.internal.ws.api.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.server.SDDocumentSource;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import org.xml.sax.SAXException;
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
/*    */ public interface XMLEntityResolver
/*    */ {
/*    */   Parser resolveEntity(String paramString1, String paramString2) throws SAXException, IOException, XMLStreamException;
/*    */   
/*    */   public static final class Parser
/*    */   {
/*    */     public final URL systemId;
/*    */     public final XMLStreamReader parser;
/*    */     
/*    */     public Parser(URL systemId, XMLStreamReader parser) {
/* 63 */       assert parser != null;
/* 64 */       this.systemId = systemId;
/* 65 */       this.parser = parser;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Parser(SDDocumentSource doc) throws IOException, XMLStreamException {
/* 72 */       this.systemId = doc.getSystemId();
/* 73 */       this.parser = doc.read();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/wsdl/parser/XMLEntityResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */