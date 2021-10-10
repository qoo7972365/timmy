/*     */ package org.jcp.xml.dsig.internal;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.utils.UnsyncByteArrayOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class DigesterOutputStream
/*     */   extends OutputStream
/*     */ {
/*  50 */   private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal");
/*     */ 
/*     */   
/*     */   private final boolean buffer;
/*     */ 
/*     */   
/*     */   private UnsyncByteArrayOutputStream bos;
/*     */   
/*     */   private final MessageDigest md;
/*     */ 
/*     */   
/*     */   public DigesterOutputStream(MessageDigest paramMessageDigest) {
/*  62 */     this(paramMessageDigest, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigesterOutputStream(MessageDigest paramMessageDigest, boolean paramBoolean) {
/*  72 */     this.md = paramMessageDigest;
/*  73 */     this.buffer = paramBoolean;
/*  74 */     if (paramBoolean) {
/*  75 */       this.bos = new UnsyncByteArrayOutputStream();
/*     */     }
/*     */   }
/*     */   
/*     */   public void write(int paramInt) {
/*  80 */     if (this.buffer) {
/*  81 */       this.bos.write(paramInt);
/*     */     }
/*  83 */     this.md.update((byte)paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  88 */     if (this.buffer) {
/*  89 */       this.bos.write(paramArrayOfbyte, paramInt1, paramInt2);
/*     */     }
/*  91 */     if (log.isLoggable(Level.FINE)) {
/*  92 */       log.log(Level.FINE, "Pre-digested input:");
/*  93 */       StringBuilder stringBuilder = new StringBuilder(paramInt2);
/*  94 */       for (int i = paramInt1; i < paramInt1 + paramInt2; i++) {
/*  95 */         stringBuilder.append((char)paramArrayOfbyte[i]);
/*     */       }
/*  97 */       log.log(Level.FINE, stringBuilder.toString());
/*     */     } 
/*  99 */     this.md.update(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getDigestValue() {
/* 106 */     return this.md.digest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() {
/* 114 */     if (this.buffer) {
/* 115 */       return new ByteArrayInputStream(this.bos.toByteArray());
/*     */     }
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 123 */     if (this.buffer)
/* 124 */       this.bos.close(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/DigesterOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */