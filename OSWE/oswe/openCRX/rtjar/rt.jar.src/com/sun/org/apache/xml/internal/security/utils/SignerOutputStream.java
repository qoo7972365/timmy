/*    */ package com.sun.org.apache.xml.internal.security.utils;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
/*    */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
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
/*    */ public class SignerOutputStream
/*    */   extends ByteArrayOutputStream
/*    */ {
/* 36 */   private static Logger log = Logger.getLogger(SignerOutputStream.class.getName());
/*    */ 
/*    */   
/*    */   final SignatureAlgorithm sa;
/*    */ 
/*    */ 
/*    */   
/*    */   public SignerOutputStream(SignatureAlgorithm paramSignatureAlgorithm) {
/* 44 */     this.sa = paramSignatureAlgorithm;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] paramArrayOfbyte) {
/*    */     try {
/* 50 */       this.sa.update(paramArrayOfbyte);
/* 51 */     } catch (XMLSignatureException xMLSignatureException) {
/* 52 */       throw new RuntimeException("" + xMLSignatureException);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int paramInt) {
/*    */     try {
/* 59 */       this.sa.update((byte)paramInt);
/* 60 */     } catch (XMLSignatureException xMLSignatureException) {
/* 61 */       throw new RuntimeException("" + xMLSignatureException);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 67 */     if (log.isLoggable(Level.FINE)) {
/* 68 */       log.log(Level.FINE, "Canonicalized SignedInfo:");
/* 69 */       StringBuilder stringBuilder = new StringBuilder(paramInt2);
/* 70 */       for (int i = paramInt1; i < paramInt1 + paramInt2; i++) {
/* 71 */         stringBuilder.append((char)paramArrayOfbyte[i]);
/*    */       }
/* 73 */       log.log(Level.FINE, stringBuilder.toString());
/*    */     } 
/*    */     try {
/* 76 */       this.sa.update(paramArrayOfbyte, paramInt1, paramInt2);
/* 77 */     } catch (XMLSignatureException xMLSignatureException) {
/* 78 */       throw new RuntimeException("" + xMLSignatureException);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/SignerOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */