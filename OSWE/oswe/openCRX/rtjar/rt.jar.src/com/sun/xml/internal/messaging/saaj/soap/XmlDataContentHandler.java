/*     */ package com.sun.xml.internal.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.internal.messaging.saaj.util.transform.EfficientStreamingTransformer;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import javax.activation.ActivationDataFlavor;
/*     */ import javax.activation.DataContentHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.stream.StreamResult;
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
/*     */ public class XmlDataContentHandler
/*     */   implements DataContentHandler
/*     */ {
/*     */   public static final String STR_SRC = "javax.xml.transform.stream.StreamSource";
/*  47 */   private static Class streamSourceClass = null;
/*     */   
/*     */   public XmlDataContentHandler() throws ClassNotFoundException {
/*  50 */     if (streamSourceClass == null) {
/*  51 */       streamSourceClass = Class.forName("javax.xml.transform.stream.StreamSource");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  60 */     DataFlavor[] flavors = new DataFlavor[2];
/*     */     
/*  62 */     flavors[0] = (DataFlavor)new ActivationDataFlavor(streamSourceClass, "text/xml", "XML");
/*     */     
/*  64 */     flavors[1] = (DataFlavor)new ActivationDataFlavor(streamSourceClass, "application/xml", "XML");
/*     */ 
/*     */     
/*  67 */     return flavors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getTransferData(DataFlavor flavor, DataSource dataSource) throws IOException {
/*  78 */     if ((flavor.getMimeType().startsWith("text/xml") || flavor
/*  79 */       .getMimeType().startsWith("application/xml")) && 
/*  80 */       flavor.getRepresentationClass().getName().equals("javax.xml.transform.stream.StreamSource")) {
/*  81 */       return new StreamSource(dataSource.getInputStream());
/*     */     }
/*     */     
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getContent(DataSource dataSource) throws IOException {
/*  91 */     return new StreamSource(dataSource.getInputStream());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
/* 101 */     if (!mimeType.startsWith("text/xml") && !mimeType.startsWith("application/xml")) {
/* 102 */       throw new IOException("Invalid content type \"" + mimeType + "\" for XmlDCH");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 107 */       Transformer transformer = EfficientStreamingTransformer.newTransformer();
/* 108 */       StreamResult result = new StreamResult(os);
/* 109 */       if (obj instanceof DataSource) {
/*     */         
/* 111 */         transformer.transform((Source)getContent((DataSource)obj), result);
/*     */       } else {
/* 113 */         Source src = null;
/* 114 */         if (obj instanceof String) {
/* 115 */           src = new StreamSource(new StringReader((String)obj));
/*     */         } else {
/* 117 */           src = (Source)obj;
/*     */         } 
/* 119 */         transformer.transform(src, result);
/*     */       } 
/* 121 */     } catch (Exception ex) {
/* 122 */       throw new IOException("Unable to run the JAXP transformer on a stream " + ex
/*     */           
/* 124 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/XmlDataContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */