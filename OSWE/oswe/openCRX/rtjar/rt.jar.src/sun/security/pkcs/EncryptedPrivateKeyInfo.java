/*     */ package sun.security.pkcs;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedPrivateKeyInfo
/*     */ {
/*     */   private AlgorithmId algid;
/*     */   private byte[] encryptedData;
/*     */   private byte[] encoded;
/*     */   
/*     */   public EncryptedPrivateKeyInfo(byte[] paramArrayOfbyte) throws IOException {
/*  65 */     if (paramArrayOfbyte == null) {
/*  66 */       throw new IllegalArgumentException("encoding must not be null");
/*     */     }
/*     */     
/*  69 */     DerValue derValue = new DerValue(paramArrayOfbyte);
/*     */     
/*  71 */     DerValue[] arrayOfDerValue = new DerValue[2];
/*     */     
/*  73 */     arrayOfDerValue[0] = derValue.data.getDerValue();
/*  74 */     arrayOfDerValue[1] = derValue.data.getDerValue();
/*     */     
/*  76 */     if (derValue.data.available() != 0) {
/*  77 */       throw new IOException("overrun, bytes = " + derValue.data.available());
/*     */     }
/*     */     
/*  80 */     this.algid = AlgorithmId.parse(arrayOfDerValue[0]);
/*  81 */     if ((arrayOfDerValue[0]).data.available() != 0) {
/*  82 */       throw new IOException("encryptionAlgorithm field overrun");
/*     */     }
/*     */     
/*  85 */     this.encryptedData = arrayOfDerValue[1].getOctetString();
/*  86 */     if ((arrayOfDerValue[1]).data.available() != 0) {
/*  87 */       throw new IOException("encryptedData field overrun");
/*     */     }
/*  89 */     this.encoded = (byte[])paramArrayOfbyte.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedPrivateKeyInfo(AlgorithmId paramAlgorithmId, byte[] paramArrayOfbyte) {
/*  97 */     this.algid = paramAlgorithmId;
/*  98 */     this.encryptedData = (byte[])paramArrayOfbyte.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmId getAlgorithm() {
/* 105 */     return this.algid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncryptedData() {
/* 112 */     return (byte[])this.encryptedData.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncoded() throws IOException {
/* 121 */     if (this.encoded != null) return (byte[])this.encoded.clone();
/*     */     
/* 123 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 124 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */ 
/*     */     
/* 127 */     this.algid.encode(derOutputStream2);
/*     */ 
/*     */     
/* 130 */     derOutputStream2.putOctetString(this.encryptedData);
/*     */ 
/*     */     
/* 133 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 134 */     this.encoded = derOutputStream1.toByteArray();
/*     */     
/* 136 */     return (byte[])this.encoded.clone();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 140 */     if (this == paramObject)
/* 141 */       return true; 
/* 142 */     if (!(paramObject instanceof EncryptedPrivateKeyInfo))
/* 143 */       return false; 
/*     */     try {
/* 145 */       byte[] arrayOfByte1 = getEncoded();
/*     */       
/* 147 */       byte[] arrayOfByte2 = ((EncryptedPrivateKeyInfo)paramObject).getEncoded();
/*     */       
/* 149 */       if (arrayOfByte1.length != arrayOfByte2.length)
/* 150 */         return false; 
/* 151 */       for (byte b = 0; b < arrayOfByte1.length; b++) {
/* 152 */         if (arrayOfByte1[b] != arrayOfByte2[b])
/* 153 */           return false; 
/* 154 */       }  return true;
/* 155 */     } catch (IOException iOException) {
/* 156 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 166 */     int i = 0;
/*     */     
/* 168 */     for (byte b = 0; b < this.encryptedData.length; b++)
/* 169 */       i += this.encryptedData[b] * b; 
/* 170 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs/EncryptedPrivateKeyInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */