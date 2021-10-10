/*    */ package org.jcp.xml.dsig.internal;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.security.Signature;
/*    */ import java.security.SignatureException;
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
/*    */   private final Signature sig;
/*    */   
/*    */   public SignerOutputStream(Signature paramSignature) {
/* 47 */     this.sig = paramSignature;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int paramInt) {
/* 52 */     super.write(paramInt);
/*    */     try {
/* 54 */       this.sig.update((byte)paramInt);
/* 55 */     } catch (SignatureException signatureException) {
/* 56 */       throw new RuntimeException(signatureException);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 62 */     super.write(paramArrayOfbyte, paramInt1, paramInt2);
/*    */     try {
/* 64 */       this.sig.update(paramArrayOfbyte, paramInt1, paramInt2);
/* 65 */     } catch (SignatureException signatureException) {
/* 66 */       throw new RuntimeException(signatureException);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/SignerOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */