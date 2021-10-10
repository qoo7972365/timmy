/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import com.sun.xml.internal.org.jvnet.mimepull.MIMEPart;
/*     */ import com.sun.xml.internal.ws.developer.StreamingDataHandler;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataSource;
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
/*     */ public class MIMEPartStreamingDataHandler
/*     */   extends StreamingDataHandler
/*     */ {
/*     */   private final StreamingDataSource ds;
/*     */   
/*     */   public MIMEPartStreamingDataHandler(MIMEPart part) {
/*  61 */     super(new StreamingDataSource(part));
/*  62 */     this.ds = (StreamingDataSource)getDataSource();
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream readOnce() throws IOException {
/*  67 */     return this.ds.readOnce();
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveTo(File file) throws IOException {
/*  72 */     this.ds.moveTo(file);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  77 */     this.ds.close();
/*     */   }
/*     */   
/*     */   private static final class StreamingDataSource implements DataSource {
/*     */     private final MIMEPart part;
/*     */     
/*     */     StreamingDataSource(MIMEPart part) {
/*  84 */       this.part = part;
/*     */     }
/*     */ 
/*     */     
/*     */     public InputStream getInputStream() throws IOException {
/*  89 */       return this.part.read();
/*     */     }
/*     */     
/*     */     InputStream readOnce() throws IOException {
/*     */       try {
/*  94 */         return this.part.readOnce();
/*  95 */       } catch (Exception e) {
/*  96 */         throw new MIMEPartStreamingDataHandler.MyIOException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     void moveTo(File file) throws IOException {
/* 101 */       this.part.moveTo(file);
/*     */     }
/*     */ 
/*     */     
/*     */     public OutputStream getOutputStream() throws IOException {
/* 106 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getContentType() {
/* 111 */       return this.part.getContentType();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 116 */       return "";
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 120 */       this.part.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class MyIOException extends IOException {
/*     */     private final Exception linkedException;
/*     */     
/*     */     MyIOException(Exception linkedException) {
/* 128 */       this.linkedException = linkedException;
/*     */     }
/*     */ 
/*     */     
/*     */     public Throwable getCause() {
/* 133 */       return this.linkedException;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/MIMEPartStreamingDataHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */