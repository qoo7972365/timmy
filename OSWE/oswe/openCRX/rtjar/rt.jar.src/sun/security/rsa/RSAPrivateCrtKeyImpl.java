/*     */ package sun.security.rsa;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.interfaces.RSAPrivateCrtKey;
/*     */ import java.security.interfaces.RSAPrivateKey;
/*     */ import sun.security.pkcs.PKCS8Key;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.x509.AlgorithmId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RSAPrivateCrtKeyImpl
/*     */   extends PKCS8Key
/*     */   implements RSAPrivateCrtKey
/*     */ {
/*     */   private static final long serialVersionUID = -1326088454257084918L;
/*     */   private BigInteger n;
/*     */   private BigInteger e;
/*     */   private BigInteger d;
/*     */   private BigInteger p;
/*     */   private BigInteger q;
/*     */   private BigInteger pe;
/*     */   private BigInteger qe;
/*     */   private BigInteger coeff;
/*  66 */   static final AlgorithmId rsaId = new AlgorithmId(AlgorithmId.RSAEncryption_oid);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RSAPrivateKey newKey(byte[] paramArrayOfbyte) throws InvalidKeyException {
/*  75 */     RSAPrivateCrtKeyImpl rSAPrivateCrtKeyImpl = new RSAPrivateCrtKeyImpl(paramArrayOfbyte);
/*  76 */     if (rSAPrivateCrtKeyImpl.getPublicExponent().signum() == 0)
/*     */     {
/*  78 */       return new RSAPrivateKeyImpl(rSAPrivateCrtKeyImpl
/*  79 */           .getModulus(), rSAPrivateCrtKeyImpl
/*  80 */           .getPrivateExponent());
/*     */     }
/*     */     
/*  83 */     return rSAPrivateCrtKeyImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RSAPrivateCrtKeyImpl(byte[] paramArrayOfbyte) throws InvalidKeyException {
/*  91 */     decode(paramArrayOfbyte);
/*  92 */     RSAKeyFactory.checkRSAProviderKeyLengths(this.n.bitLength(), this.e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RSAPrivateCrtKeyImpl(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4, BigInteger paramBigInteger5, BigInteger paramBigInteger6, BigInteger paramBigInteger7, BigInteger paramBigInteger8) throws InvalidKeyException {
/* 102 */     this.n = paramBigInteger1;
/* 103 */     this.e = paramBigInteger2;
/* 104 */     this.d = paramBigInteger3;
/* 105 */     this.p = paramBigInteger4;
/* 106 */     this.q = paramBigInteger5;
/* 107 */     this.pe = paramBigInteger6;
/* 108 */     this.qe = paramBigInteger7;
/* 109 */     this.coeff = paramBigInteger8;
/* 110 */     RSAKeyFactory.checkRSAProviderKeyLengths(paramBigInteger1.bitLength(), paramBigInteger2);
/*     */ 
/*     */     
/* 113 */     this.algid = rsaId;
/*     */     try {
/* 115 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 116 */       derOutputStream.putInteger(0);
/* 117 */       derOutputStream.putInteger(paramBigInteger1);
/* 118 */       derOutputStream.putInteger(paramBigInteger2);
/* 119 */       derOutputStream.putInteger(paramBigInteger3);
/* 120 */       derOutputStream.putInteger(paramBigInteger4);
/* 121 */       derOutputStream.putInteger(paramBigInteger5);
/* 122 */       derOutputStream.putInteger(paramBigInteger6);
/* 123 */       derOutputStream.putInteger(paramBigInteger7);
/* 124 */       derOutputStream.putInteger(paramBigInteger8);
/*     */       
/* 126 */       DerValue derValue = new DerValue((byte)48, derOutputStream.toByteArray());
/* 127 */       this.key = derValue.toByteArray();
/* 128 */     } catch (IOException iOException) {
/*     */       
/* 130 */       throw new InvalidKeyException(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAlgorithm() {
/* 136 */     return "RSA";
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getModulus() {
/* 141 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getPublicExponent() {
/* 146 */     return this.e;
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getPrivateExponent() {
/* 151 */     return this.d;
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getPrimeP() {
/* 156 */     return this.p;
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getPrimeQ() {
/* 161 */     return this.q;
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getPrimeExponentP() {
/* 166 */     return this.pe;
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getPrimeExponentQ() {
/* 171 */     return this.qe;
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getCrtCoefficient() {
/* 176 */     return this.coeff;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void parseKeyBits() throws InvalidKeyException {
/*     */     try {
/* 184 */       DerInputStream derInputStream1 = new DerInputStream(this.key);
/* 185 */       DerValue derValue = derInputStream1.getDerValue();
/* 186 */       if (derValue.tag != 48) {
/* 187 */         throw new IOException("Not a SEQUENCE");
/*     */       }
/* 189 */       DerInputStream derInputStream2 = derValue.data;
/* 190 */       int i = derInputStream2.getInteger();
/* 191 */       if (i != 0) {
/* 192 */         throw new IOException("Version must be 0");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       this.n = derInputStream2.getPositiveBigInteger();
/* 203 */       this.e = derInputStream2.getPositiveBigInteger();
/* 204 */       this.d = derInputStream2.getPositiveBigInteger();
/* 205 */       this.p = derInputStream2.getPositiveBigInteger();
/* 206 */       this.q = derInputStream2.getPositiveBigInteger();
/* 207 */       this.pe = derInputStream2.getPositiveBigInteger();
/* 208 */       this.qe = derInputStream2.getPositiveBigInteger();
/* 209 */       this.coeff = derInputStream2.getPositiveBigInteger();
/* 210 */       if (derValue.data.available() != 0) {
/* 211 */         throw new IOException("Extra data available");
/*     */       }
/* 213 */     } catch (IOException iOException) {
/* 214 */       throw new InvalidKeyException("Invalid RSA private key", iOException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/rsa/RSAPrivateCrtKeyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */