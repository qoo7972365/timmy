/*    */ package com.sun.xml.internal.ws.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*    */ import com.sun.xml.internal.ws.api.wsdl.parser.XMLEntityResolver;
/*    */ import com.sun.xml.internal.ws.streaming.TidyXMLStreamReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import org.xml.sax.EntityResolver;
/*    */ import org.xml.sax.InputSource;
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
/*    */ final class EntityResolverWrapper
/*    */   implements XMLEntityResolver
/*    */ {
/*    */   private final EntityResolver core;
/*    */   private boolean useStreamFromEntityResolver = false;
/*    */   
/*    */   public EntityResolverWrapper(EntityResolver core) {
/* 49 */     this.core = core;
/*    */   }
/*    */   
/*    */   public EntityResolverWrapper(EntityResolver core, boolean useStreamFromEntityResolver) {
/* 53 */     this.core = core;
/* 54 */     this.useStreamFromEntityResolver = useStreamFromEntityResolver;
/*    */   }
/*    */   public XMLEntityResolver.Parser resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/*    */     InputStream stream;
/* 58 */     InputSource source = this.core.resolveEntity(publicId, systemId);
/* 59 */     if (source == null) {
/* 60 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 65 */     if (source.getSystemId() != null) {
/* 66 */       systemId = source.getSystemId();
/*    */     }
/* 68 */     URL url = new URL(systemId);
/*    */     
/* 70 */     if (this.useStreamFromEntityResolver) {
/* 71 */       stream = source.getByteStream();
/*    */     } else {
/* 73 */       stream = url.openStream();
/*    */     } 
/* 75 */     return new XMLEntityResolver.Parser(url, (XMLStreamReader)new TidyXMLStreamReader(
/* 76 */           XMLStreamReaderFactory.create(url.toExternalForm(), stream, true), stream));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/parser/EntityResolverWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */