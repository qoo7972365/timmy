/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.interfaces.DSAParams;
/*     */ import java.security.interfaces.DSAPrivateKey;
/*     */ import java.security.spec.DSAParameterSpec;
/*     */ import java.security.spec.InvalidParameterSpecException;
/*     */ import sun.security.pkcs.PKCS8Key;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.x509.AlgIdDSA;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DSAPrivateKey
/*     */   extends PKCS8Key
/*     */   implements DSAPrivateKey, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3244453684193605938L;
/*     */   private BigInteger x;
/*     */   
/*     */   public DSAPrivateKey() {}
/*     */   
/*     */   public DSAPrivateKey(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4) throws InvalidKeyException {
/*  77 */     this.x = paramBigInteger1;
/*  78 */     this.algid = new AlgIdDSA(paramBigInteger2, paramBigInteger3, paramBigInteger4);
/*     */     
/*     */     try {
/*  81 */       this
/*  82 */         .key = (new DerValue((byte)2, paramBigInteger1.toByteArray())).toByteArray();
/*  83 */       encode();
/*  84 */     } catch (IOException iOException) {
/*     */       
/*  86 */       InvalidKeyException invalidKeyException = new InvalidKeyException("could not DER encode x: " + iOException.getMessage());
/*  87 */       invalidKeyException.initCause(iOException);
/*  88 */       throw invalidKeyException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DSAPrivateKey(byte[] paramArrayOfbyte) throws InvalidKeyException {
/*  96 */     clearOldKey();
/*  97 */     decode(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DSAParams getParams() {
/*     */     try {
/* 106 */       if (this.algid instanceof DSAParams) {
/* 107 */         return (DSAParams)this.algid;
/*     */       }
/*     */       
/* 110 */       AlgorithmParameters algorithmParameters = this.algid.getParameters();
/* 111 */       if (algorithmParameters == null) {
/* 112 */         return null;
/*     */       }
/* 114 */       return algorithmParameters.<DSAParameterSpec>getParameterSpec(DSAParameterSpec.class);
/*     */     
/*     */     }
/* 117 */     catch (InvalidParameterSpecException invalidParameterSpecException) {
/* 118 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getX() {
/* 128 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearOldKey() {
/* 133 */     if (this.encodedKey != null) {
/* 134 */       for (byte b = 0; b < this.encodedKey.length; b++) {
/* 135 */         this.encodedKey[b] = 0;
/*     */       }
/*     */     }
/* 138 */     if (this.key != null) {
/* 139 */       for (byte b = 0; b < this.key.length; b++) {
/* 140 */         this.key[b] = 0;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void parseKeyBits() throws InvalidKeyException {
/*     */     try {
/* 147 */       DerInputStream derInputStream = new DerInputStream(this.key);
/* 148 */       this.x = derInputStream.getBigInteger();
/* 149 */     } catch (IOException iOException) {
/* 150 */       InvalidKeyException invalidKeyException = new InvalidKeyException(iOException.getMessage());
/* 151 */       invalidKeyException.initCause(iOException);
/* 152 */       throw invalidKeyException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/DSAPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */