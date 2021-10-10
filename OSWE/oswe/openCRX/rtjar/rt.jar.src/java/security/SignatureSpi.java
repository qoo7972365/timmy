/*     */ package java.security;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import sun.security.jca.JCAUtil;
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
/*     */ public abstract class SignatureSpi
/*     */ {
/*  57 */   protected SecureRandom appRandom = null;
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
/*     */   protected abstract void engineInitVerify(PublicKey paramPublicKey) throws InvalidKeyException;
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
/*     */   protected abstract void engineInitSign(PrivateKey paramPrivateKey) throws InvalidKeyException;
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
/*     */   protected void engineInitSign(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom) throws InvalidKeyException {
/* 102 */     this.appRandom = paramSecureRandom;
/* 103 */     engineInitSign(paramPrivateKey);
/*     */   }
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
/*     */   protected abstract void engineUpdate(byte paramByte) throws SignatureException;
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
/*     */   protected abstract void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException;
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
/*     */   protected void engineUpdate(ByteBuffer paramByteBuffer) {
/* 142 */     if (!paramByteBuffer.hasRemaining()) {
/*     */       return;
/*     */     }
/*     */     try {
/* 146 */       if (paramByteBuffer.hasArray()) {
/* 147 */         byte[] arrayOfByte = paramByteBuffer.array();
/* 148 */         int i = paramByteBuffer.arrayOffset();
/* 149 */         int j = paramByteBuffer.position();
/* 150 */         int k = paramByteBuffer.limit();
/* 151 */         engineUpdate(arrayOfByte, i + j, k - j);
/* 152 */         paramByteBuffer.position(k);
/*     */       } else {
/* 154 */         int i = paramByteBuffer.remaining();
/* 155 */         byte[] arrayOfByte = new byte[JCAUtil.getTempArraySize(i)];
/* 156 */         while (i > 0) {
/* 157 */           int j = Math.min(i, arrayOfByte.length);
/* 158 */           paramByteBuffer.get(arrayOfByte, 0, j);
/* 159 */           engineUpdate(arrayOfByte, 0, j);
/* 160 */           i -= j;
/*     */         } 
/*     */       } 
/* 163 */     } catch (SignatureException signatureException) {
/*     */ 
/*     */       
/* 166 */       throw new ProviderException("update() failed", signatureException);
/*     */     } 
/*     */   }
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
/*     */   protected abstract byte[] engineSign() throws SignatureException;
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
/*     */   protected int engineSign(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
/* 226 */     byte[] arrayOfByte = engineSign();
/* 227 */     if (paramInt2 < arrayOfByte.length) {
/* 228 */       throw new SignatureException("partial signatures not returned");
/*     */     }
/*     */     
/* 231 */     if (paramArrayOfbyte.length - paramInt1 < arrayOfByte.length) {
/* 232 */       throw new SignatureException("insufficient space in the output buffer to store the signature");
/*     */     }
/*     */ 
/*     */     
/* 236 */     System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
/* 237 */     return arrayOfByte.length;
/*     */   }
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
/*     */   protected abstract boolean engineVerify(byte[] paramArrayOfbyte) throws SignatureException;
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
/*     */   protected boolean engineVerify(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
/* 276 */     byte[] arrayOfByte = new byte[paramInt2];
/* 277 */     System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, paramInt2);
/* 278 */     return engineVerify(arrayOfByte);
/*     */   }
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
/*     */   @Deprecated
/*     */   protected abstract void engineSetParameter(String paramString, Object paramObject) throws InvalidParameterException;
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
/*     */   protected void engineSetParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 324 */     throw new UnsupportedOperationException();
/*     */   }
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
/*     */   protected AlgorithmParameters engineGetParameters() {
/* 346 */     throw new UnsupportedOperationException();
/*     */   }
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
/*     */   @Deprecated
/*     */   protected abstract Object engineGetParameter(String paramString) throws InvalidParameterException;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 384 */     if (this instanceof Cloneable) {
/* 385 */       return super.clone();
/*     */     }
/* 387 */     throw new CloneNotSupportedException();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/SignatureSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */