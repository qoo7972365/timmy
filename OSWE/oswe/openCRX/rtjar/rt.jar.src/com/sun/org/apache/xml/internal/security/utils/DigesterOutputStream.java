/*    */ package com.sun.org.apache.xml.internal.security.utils;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
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
/*    */ public class DigesterOutputStream
/*    */   extends ByteArrayOutputStream
/*    */ {
/* 35 */   private static final Logger log = Logger.getLogger(DigesterOutputStream.class.getName());
/*    */ 
/*    */   
/*    */   final MessageDigestAlgorithm mda;
/*    */ 
/*    */ 
/*    */   
/*    */   public DigesterOutputStream(MessageDigestAlgorithm paramMessageDigestAlgorithm) {
/* 43 */     this.mda = paramMessageDigestAlgorithm;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] paramArrayOfbyte) {
/* 48 */     write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int paramInt) {
/* 53 */     this.mda.update((byte)paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 58 */     if (log.isLoggable(Level.FINE)) {
/* 59 */       log.log(Level.FINE, "Pre-digested input:");
/* 60 */       StringBuilder stringBuilder = new StringBuilder(paramInt2);
/* 61 */       for (int i = paramInt1; i < paramInt1 + paramInt2; i++) {
/* 62 */         stringBuilder.append((char)paramArrayOfbyte[i]);
/*    */       }
/* 64 */       log.log(Level.FINE, stringBuilder.toString());
/*    */     } 
/* 66 */     this.mda.update(paramArrayOfbyte, paramInt1, paramInt2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getDigestValue() {
/* 73 */     return this.mda.digest();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/DigesterOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */