/*     */ package sun.security.pkcs12;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import sun.security.pkcs.ParsingException;
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
/*     */ class MacData
/*     */ {
/*     */   private String digestAlgorithmName;
/*     */   private AlgorithmParameters digestAlgorithmParams;
/*     */   private byte[] digest;
/*     */   private byte[] macSalt;
/*     */   private int iterations;
/*  53 */   private byte[] encoded = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MacData(DerInputStream paramDerInputStream) throws IOException, ParsingException {
/*  61 */     DerValue[] arrayOfDerValue1 = paramDerInputStream.getSequence(2);
/*     */ 
/*     */     
/*  64 */     DerInputStream derInputStream = new DerInputStream(arrayOfDerValue1[0].toByteArray());
/*  65 */     DerValue[] arrayOfDerValue2 = derInputStream.getSequence(2);
/*     */ 
/*     */     
/*  68 */     AlgorithmId algorithmId = AlgorithmId.parse(arrayOfDerValue2[0]);
/*  69 */     this.digestAlgorithmName = algorithmId.getName();
/*  70 */     this.digestAlgorithmParams = algorithmId.getParameters();
/*     */     
/*  72 */     this.digest = arrayOfDerValue2[1].getOctetString();
/*     */ 
/*     */     
/*  75 */     this.macSalt = arrayOfDerValue1[1].getOctetString();
/*     */ 
/*     */     
/*  78 */     if (arrayOfDerValue1.length > 2) {
/*  79 */       this.iterations = arrayOfDerValue1[2].getInteger();
/*     */     } else {
/*  81 */       this.iterations = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MacData(String paramString, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws NoSuchAlgorithmException {
/*  88 */     if (paramString == null) {
/*  89 */       throw new NullPointerException("the algName parameter must be non-null");
/*     */     }
/*     */     
/*  92 */     AlgorithmId algorithmId = AlgorithmId.get(paramString);
/*  93 */     this.digestAlgorithmName = algorithmId.getName();
/*  94 */     this.digestAlgorithmParams = algorithmId.getParameters();
/*     */     
/*  96 */     if (paramArrayOfbyte1 == null) {
/*  97 */       throw new NullPointerException("the digest parameter must be non-null");
/*     */     }
/*  99 */     if (paramArrayOfbyte1.length == 0) {
/* 100 */       throw new IllegalArgumentException("the digest parameter must not be empty");
/*     */     }
/*     */     
/* 103 */     this.digest = (byte[])paramArrayOfbyte1.clone();
/*     */ 
/*     */     
/* 106 */     this.macSalt = paramArrayOfbyte2;
/* 107 */     this.iterations = paramInt;
/*     */ 
/*     */ 
/*     */     
/* 111 */     this.encoded = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MacData(AlgorithmParameters paramAlgorithmParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws NoSuchAlgorithmException {
/* 118 */     if (paramAlgorithmParameters == null) {
/* 119 */       throw new NullPointerException("the algParams parameter must be non-null");
/*     */     }
/*     */     
/* 122 */     AlgorithmId algorithmId = AlgorithmId.get(paramAlgorithmParameters);
/* 123 */     this.digestAlgorithmName = algorithmId.getName();
/* 124 */     this.digestAlgorithmParams = algorithmId.getParameters();
/*     */     
/* 126 */     if (paramArrayOfbyte1 == null) {
/* 127 */       throw new NullPointerException("the digest parameter must be non-null");
/*     */     }
/* 129 */     if (paramArrayOfbyte1.length == 0) {
/* 130 */       throw new IllegalArgumentException("the digest parameter must not be empty");
/*     */     }
/*     */     
/* 133 */     this.digest = (byte[])paramArrayOfbyte1.clone();
/*     */ 
/*     */     
/* 136 */     this.macSalt = paramArrayOfbyte2;
/* 137 */     this.iterations = paramInt;
/*     */ 
/*     */ 
/*     */     
/* 141 */     this.encoded = null;
/*     */   }
/*     */ 
/*     */   
/*     */   String getDigestAlgName() {
/* 146 */     return this.digestAlgorithmName;
/*     */   }
/*     */   
/*     */   byte[] getSalt() {
/* 150 */     return this.macSalt;
/*     */   }
/*     */   
/*     */   int getIterations() {
/* 154 */     return this.iterations;
/*     */   }
/*     */   
/*     */   byte[] getDigest() {
/* 158 */     return this.digest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncoded() throws NoSuchAlgorithmException, IOException {
/* 169 */     if (this.encoded != null) {
/* 170 */       return (byte[])this.encoded.clone();
/*     */     }
/* 172 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 173 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 175 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/*     */     
/* 177 */     AlgorithmId algorithmId = AlgorithmId.get(this.digestAlgorithmName);
/* 178 */     algorithmId.encode(derOutputStream3);
/*     */ 
/*     */     
/* 181 */     derOutputStream3.putOctetString(this.digest);
/*     */     
/* 183 */     derOutputStream2.write((byte)48, derOutputStream3);
/*     */ 
/*     */     
/* 186 */     derOutputStream2.putOctetString(this.macSalt);
/*     */ 
/*     */     
/* 189 */     derOutputStream2.putInteger(this.iterations);
/*     */ 
/*     */     
/* 192 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 193 */     this.encoded = derOutputStream1.toByteArray();
/*     */     
/* 195 */     return (byte[])this.encoded.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs12/MacData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */