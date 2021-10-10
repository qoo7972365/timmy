/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlDataContentHandler
/*     */   implements DataContentHandler
/*     */ {
/*     */   private final DataFlavor[] flavors;
/*     */   
/*     */   public XmlDataContentHandler() throws ClassNotFoundException {
/*  55 */     this.flavors = new DataFlavor[3];
/*  56 */     this.flavors[0] = (DataFlavor)new ActivationDataFlavor(StreamSource.class, "text/xml", "XML");
/*  57 */     this.flavors[1] = (DataFlavor)new ActivationDataFlavor(StreamSource.class, "application/xml", "XML");
/*  58 */     this.flavors[2] = (DataFlavor)new ActivationDataFlavor(String.class, "text/xml", "XML String");
/*     */   }
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  62 */     return Arrays.<DataFlavor>copyOf(this.flavors, this.flavors.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getTransferData(DataFlavor df, DataSource ds) throws IOException {
/*  68 */     for (DataFlavor aFlavor : this.flavors) {
/*  69 */       if (aFlavor.equals(df)) {
/*  70 */         return getContent(ds);
/*     */       }
/*     */     } 
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getContent(DataSource ds) throws IOException {
/*  80 */     String ctStr = ds.getContentType();
/*  81 */     String charset = null;
/*  82 */     if (ctStr != null) {
/*  83 */       ContentType ct = new ContentType(ctStr);
/*  84 */       if (!isXml(ct)) {
/*  85 */         throw new IOException("Cannot convert DataSource with content type \"" + ctStr + "\" to object in XmlDataContentHandler");
/*     */       }
/*     */ 
/*     */       
/*  89 */       charset = ct.getParameter("charset");
/*     */     } 
/*  91 */     return (charset != null) ? new StreamSource(new InputStreamReader(ds
/*  92 */           .getInputStream()), charset) : new StreamSource(ds
/*  93 */         .getInputStream());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
/* 102 */     if (!(obj instanceof DataSource) && !(obj instanceof Source) && !(obj instanceof String)) {
/* 103 */       throw new IOException("Invalid Object type = " + obj.getClass() + ". XmlDataContentHandler can only convert DataSource|Source|String to XML.");
/*     */     }
/*     */ 
/*     */     
/* 107 */     ContentType ct = new ContentType(mimeType);
/* 108 */     if (!isXml(ct)) {
/* 109 */       throw new IOException("Invalid content type \"" + mimeType + "\" for XmlDataContentHandler");
/*     */     }
/*     */ 
/*     */     
/* 113 */     String charset = ct.getParameter("charset");
/* 114 */     if (obj instanceof String) {
/* 115 */       String s = (String)obj;
/* 116 */       if (charset == null) {
/* 117 */         charset = "utf-8";
/*     */       }
/* 119 */       OutputStreamWriter osw = new OutputStreamWriter(os, charset);
/* 120 */       osw.write(s, 0, s.length());
/* 121 */       osw.flush();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 126 */     Source source = (obj instanceof DataSource) ? (Source)getContent((DataSource)obj) : (Source)obj;
/*     */     try {
/* 128 */       Transformer transformer = XmlUtil.newTransformer();
/* 129 */       if (charset != null) {
/* 130 */         transformer.setOutputProperty("encoding", charset);
/*     */       }
/* 132 */       StreamResult result = new StreamResult(os);
/* 133 */       transformer.transform(source, result);
/* 134 */     } catch (Exception ex) {
/* 135 */       throw new IOException("Unable to run the JAXP transformer in XmlDataContentHandler " + ex
/*     */           
/* 137 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isXml(ContentType ct) {
/* 142 */     return (ct.getSubType().equals("xml") && (ct
/* 143 */       .getPrimaryType().equals("text") || ct.getPrimaryType().equals("application")));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/XmlDataContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */