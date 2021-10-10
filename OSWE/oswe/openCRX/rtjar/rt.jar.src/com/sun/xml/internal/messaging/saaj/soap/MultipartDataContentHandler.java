/*    */ package com.sun.xml.internal.messaging.saaj.soap;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ContentType;
/*    */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeMultipart;
/*    */ import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
/*    */ import java.awt.datatransfer.DataFlavor;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import javax.activation.ActivationDataFlavor;
/*    */ import javax.activation.DataContentHandler;
/*    */ import javax.activation.DataSource;
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
/*    */ public class MultipartDataContentHandler
/*    */   implements DataContentHandler
/*    */ {
/* 36 */   private ActivationDataFlavor myDF = new ActivationDataFlavor(MimeMultipart.class, "multipart/mixed", "Multipart");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DataFlavor[] getTransferDataFlavors() {
/* 47 */     return new DataFlavor[] { (DataFlavor)this.myDF };
/*    */   }
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
/*    */   public Object getTransferData(DataFlavor df, DataSource ds) {
/* 60 */     if (this.myDF.equals(df)) {
/* 61 */       return getContent(ds);
/*    */     }
/* 63 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getContent(DataSource ds) {
/*    */     try {
/* 71 */       return new MimeMultipart(ds, new ContentType(ds
/* 72 */             .getContentType()));
/* 73 */     } catch (Exception e) {
/* 74 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
/* 83 */     if (obj instanceof MimeMultipart)
/*    */       
/*    */       try {
/*    */         
/* 87 */         ByteOutputStream baos = null;
/* 88 */         if (os instanceof ByteOutputStream) {
/* 89 */           baos = (ByteOutputStream)os;
/*    */         } else {
/* 91 */           throw new IOException("Input Stream expected to be a com.sun.xml.internal.messaging.saaj.util.ByteOutputStream, but found " + os
/* 92 */               .getClass().getName());
/*    */         } 
/* 94 */         ((MimeMultipart)obj).writeTo((OutputStream)baos);
/* 95 */       } catch (Exception e) {
/* 96 */         throw new IOException(e.toString());
/*    */       }  
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/MultipartDataContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */