/*     */ package com.sun.xml.internal.ws.streaming;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
/*     */ import com.sun.xml.internal.ws.encoding.HasEncoding;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamWriterUtil
/*     */ {
/*     */   @Nullable
/*     */   public static OutputStream getOutputStream(XMLStreamWriter writer) throws XMLStreamException {
/*  59 */     Object obj = null;
/*     */ 
/*     */ 
/*     */     
/*  63 */     XMLStreamWriter xmlStreamWriter = (writer instanceof XMLStreamWriterFactory.HasEncodingWriter) ? ((XMLStreamWriterFactory.HasEncodingWriter)writer).getWriter() : writer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     if (xmlStreamWriter instanceof Map) {
/*  69 */       obj = ((Map)xmlStreamWriter).get("sjsxp-outputstream");
/*     */     }
/*     */ 
/*     */     
/*  73 */     if (obj == null) {
/*     */       try {
/*  75 */         obj = writer.getProperty("com.ctc.wstx.outputUnderlyingStream");
/*  76 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     if (obj == null) {
/*     */       try {
/*  85 */         obj = writer.getProperty("http://java.sun.com/xml/stream/properties/outputstream");
/*  86 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     if (obj != null) {
/*  94 */       writer.writeCharacters("");
/*  95 */       writer.flush();
/*  96 */       return (OutputStream)obj;
/*     */     } 
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String getEncoding(XMLStreamWriter writer) {
/* 114 */     return (writer instanceof HasEncoding) ? ((HasEncoding)writer)
/* 115 */       .getEncoding() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encodeQName(XMLStreamWriter writer, QName qname, PrefixFactory prefixFactory) {
/*     */     try {
/* 127 */       String namespaceURI = qname.getNamespaceURI();
/* 128 */       String localPart = qname.getLocalPart();
/*     */       
/* 130 */       if (namespaceURI == null || namespaceURI.equals("")) {
/* 131 */         return localPart;
/*     */       }
/*     */       
/* 134 */       String prefix = writer.getPrefix(namespaceURI);
/* 135 */       if (prefix == null) {
/* 136 */         prefix = prefixFactory.getPrefix(namespaceURI);
/* 137 */         writer.writeNamespace(prefix, namespaceURI);
/*     */       } 
/* 139 */       return prefix + ":" + localPart;
/*     */     
/*     */     }
/* 142 */     catch (XMLStreamException e) {
/* 143 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/streaming/XMLStreamWriterUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */