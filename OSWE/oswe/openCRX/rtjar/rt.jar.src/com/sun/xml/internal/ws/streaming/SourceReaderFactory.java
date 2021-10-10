/*     */ package com.sun.xml.internal.ws.streaming;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.internal.ws.util.FastInfosetUtil;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamSource;
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
/*     */ public class SourceReaderFactory
/*     */ {
/*     */   static Class fastInfosetSourceClass;
/*     */   static Method fastInfosetSource_getInputStream;
/*     */   
/*     */   static {
/*     */     try {
/*  64 */       fastInfosetSourceClass = Class.forName("com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetSource");
/*     */       
/*  66 */       fastInfosetSource_getInputStream = fastInfosetSourceClass.getMethod("getInputStream", new Class[0]);
/*     */     }
/*  68 */     catch (Exception e) {
/*  69 */       fastInfosetSourceClass = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static XMLStreamReader createSourceReader(Source source, boolean rejectDTDs) {
/*  74 */     return createSourceReader(source, rejectDTDs, null);
/*     */   }
/*     */   
/*     */   public static XMLStreamReader createSourceReader(Source source, boolean rejectDTDs, String charsetName) {
/*     */     try {
/*  79 */       if (source instanceof StreamSource) {
/*  80 */         StreamSource streamSource = (StreamSource)source;
/*  81 */         InputStream is = streamSource.getInputStream();
/*     */         
/*  83 */         if (is != null) {
/*     */           
/*  85 */           if (charsetName != null) {
/*  86 */             return XMLStreamReaderFactory.create(source
/*  87 */                 .getSystemId(), new InputStreamReader(is, charsetName), rejectDTDs);
/*     */           }
/*     */           
/*  90 */           return XMLStreamReaderFactory.create(source
/*  91 */               .getSystemId(), is, rejectDTDs);
/*     */         } 
/*     */ 
/*     */         
/*  95 */         Reader reader = streamSource.getReader();
/*  96 */         if (reader != null) {
/*  97 */           return XMLStreamReaderFactory.create(source
/*  98 */               .getSystemId(), reader, rejectDTDs);
/*     */         }
/*     */         
/* 101 */         return XMLStreamReaderFactory.create(source
/* 102 */             .getSystemId(), (new URL(source.getSystemId())).openStream(), rejectDTDs);
/*     */       } 
/*     */ 
/*     */       
/* 106 */       if (source.getClass() == fastInfosetSourceClass) {
/* 107 */         return FastInfosetUtil.createFIStreamReader((InputStream)fastInfosetSource_getInputStream
/* 108 */             .invoke(source, new Object[0]));
/*     */       }
/* 110 */       if (source instanceof DOMSource) {
/* 111 */         DOMStreamReader dsr = new DOMStreamReader();
/* 112 */         dsr.setCurrentNode(((DOMSource)source).getNode());
/* 113 */         return dsr;
/*     */       } 
/* 115 */       if (source instanceof javax.xml.transform.sax.SAXSource) {
/*     */         
/* 117 */         Transformer tx = XmlUtil.newTransformer();
/* 118 */         DOMResult domResult = new DOMResult();
/* 119 */         tx.transform(source, domResult);
/* 120 */         return createSourceReader(new DOMSource(domResult
/* 121 */               .getNode()), rejectDTDs);
/*     */       } 
/*     */ 
/*     */       
/* 125 */       throw new XMLReaderException("sourceReader.invalidSource", new Object[] { source
/* 126 */             .getClass().getName() });
/*     */     
/*     */     }
/* 129 */     catch (Exception e) {
/* 130 */       throw new XMLReaderException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/streaming/SourceReaderFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */