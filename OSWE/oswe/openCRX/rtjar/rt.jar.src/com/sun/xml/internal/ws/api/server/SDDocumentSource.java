/*     */ package com.sun.xml.internal.ws.api.server;
/*     */ 
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.internal.ws.streaming.TidyXMLStreamReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ public abstract class SDDocumentSource
/*     */ {
/*     */   public abstract XMLStreamReader read(XMLInputFactory paramXMLInputFactory) throws IOException, XMLStreamException;
/*     */   
/*     */   public abstract XMLStreamReader read() throws IOException, XMLStreamException;
/*     */   
/*     */   public abstract URL getSystemId();
/*     */   
/*     */   public static SDDocumentSource create(final URL url) {
/*  93 */     return new SDDocumentSource() {
/*  94 */         private final URL systemId = url;
/*     */         
/*     */         public XMLStreamReader read(XMLInputFactory xif) throws IOException, XMLStreamException {
/*  97 */           InputStream is = url.openStream();
/*  98 */           return (XMLStreamReader)new TidyXMLStreamReader(xif
/*  99 */               .createXMLStreamReader(this.systemId.toExternalForm(), is), is);
/*     */         }
/*     */         
/*     */         public XMLStreamReader read() throws IOException, XMLStreamException {
/* 103 */           InputStream is = url.openStream();
/* 104 */           return (XMLStreamReader)new TidyXMLStreamReader(
/* 105 */               XMLStreamReaderFactory.create(this.systemId.toExternalForm(), is, false), is);
/*     */         }
/*     */         
/*     */         public URL getSystemId() {
/* 109 */           return this.systemId;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SDDocumentSource create(final URL systemId, final XMLStreamBuffer xsb) {
/* 118 */     return new SDDocumentSource() {
/*     */         public XMLStreamReader read(XMLInputFactory xif) throws XMLStreamException {
/* 120 */           return (XMLStreamReader)xsb.readAsXMLStreamReader();
/*     */         }
/*     */         
/*     */         public XMLStreamReader read() throws XMLStreamException {
/* 124 */           return (XMLStreamReader)xsb.readAsXMLStreamReader();
/*     */         }
/*     */         
/*     */         public URL getSystemId() {
/* 128 */           return systemId;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/SDDocumentSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */