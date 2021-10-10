/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.interfaces.DSAParams;
/*     */ import java.security.interfaces.DSAPublicKey;
/*     */ import java.security.spec.DSAParameterSpec;
/*     */ import java.security.spec.InvalidParameterSpecException;
/*     */ import sun.security.util.BitArray;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.x509.AlgIdDSA;
/*     */ import sun.security.x509.X509Key;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DSAPublicKey
/*     */   extends X509Key
/*     */   implements DSAPublicKey, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2994193307391104133L;
/*     */   private BigInteger y;
/*     */   
/*     */   public DSAPublicKey() {}
/*     */   
/*     */   public DSAPublicKey(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4) throws InvalidKeyException {
/*  88 */     this.y = paramBigInteger1;
/*  89 */     this.algid = new AlgIdDSA(paramBigInteger2, paramBigInteger3, paramBigInteger4);
/*     */ 
/*     */     
/*     */     try {
/*  93 */       byte[] arrayOfByte = (new DerValue((byte)2, paramBigInteger1.toByteArray())).toByteArray();
/*  94 */       setKey(new BitArray(arrayOfByte.length * 8, arrayOfByte));
/*  95 */       encode();
/*  96 */     } catch (IOException iOException) {
/*  97 */       throw new InvalidKeyException("could not DER encode y: " + iOException
/*  98 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DSAPublicKey(byte[] paramArrayOfbyte) throws InvalidKeyException {
/* 106 */     decode(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DSAParams getParams() {
/*     */     try {
/* 115 */       if (this.algid instanceof DSAParams) {
/* 116 */         return (DSAParams)this.algid;
/*     */       }
/*     */       
/* 119 */       AlgorithmParameters algorithmParameters = this.algid.getParameters();
/* 120 */       if (algorithmParameters == null) {
/* 121 */         return null;
/*     */       }
/* 123 */       return algorithmParameters.<DSAParameterSpec>getParameterSpec(DSAParameterSpec.class);
/*     */     
/*     */     }
/* 126 */     catch (InvalidParameterSpecException invalidParameterSpecException) {
/* 127 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getY() {
/* 137 */     return this.y;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 141 */     return "Sun DSA Public Key\n    Parameters:" + this.algid + "\n  y:\n" + 
/* 142 */       Debug.toHexString(this.y) + "\n";
/*     */   }
/*     */   
/*     */   protected void parseKeyBits() throws InvalidKeyException {
/*     */     try {
/* 147 */       DerInputStream derInputStream = new DerInputStream(getKey().toByteArray());
/* 148 */       this.y = derInputStream.getBigInteger();
/* 149 */     } catch (IOException iOException) {
/* 150 */       throw new InvalidKeyException("Invalid key: y value\n" + iOException
/* 151 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/DSAPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */