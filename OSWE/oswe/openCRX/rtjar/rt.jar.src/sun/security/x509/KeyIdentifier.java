/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PublicKey;
/*     */ import java.util.Arrays;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyIdentifier
/*     */ {
/*     */   private byte[] octetString;
/*     */   
/*     */   public KeyIdentifier(byte[] paramArrayOfbyte) {
/*  51 */     this.octetString = (byte[])paramArrayOfbyte.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyIdentifier(DerValue paramDerValue) throws IOException {
/*  60 */     this.octetString = paramDerValue.getOctetString();
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
/*     */   public KeyIdentifier(PublicKey paramPublicKey) throws IOException {
/*  85 */     DerValue derValue = new DerValue(paramPublicKey.getEncoded());
/*  86 */     if (derValue.tag != 48) {
/*  87 */       throw new IOException("PublicKey value is not a valid X.509 public key");
/*     */     }
/*     */     
/*  90 */     AlgorithmId algorithmId = AlgorithmId.parse(derValue.data.getDerValue());
/*  91 */     byte[] arrayOfByte = derValue.data.getUnalignedBitString().toByteArray();
/*     */     
/*  93 */     MessageDigest messageDigest = null;
/*     */     try {
/*  95 */       messageDigest = MessageDigest.getInstance("SHA1");
/*  96 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  97 */       throw new IOException("SHA1 not supported");
/*     */     } 
/*  99 */     messageDigest.update(arrayOfByte);
/* 100 */     this.octetString = messageDigest.digest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getIdentifier() {
/* 107 */     return (byte[])this.octetString.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 114 */     String str = "KeyIdentifier [\n";
/*     */     
/* 116 */     HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 117 */     str = str + hexDumpEncoder.encodeBuffer(this.octetString);
/* 118 */     str = str + "]\n";
/* 119 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 129 */     paramDerOutputStream.putOctetString(this.octetString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 137 */     int i = 0;
/* 138 */     for (byte b = 0; b < this.octetString.length; b++)
/* 139 */       i += this.octetString[b] * b; 
/* 140 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 147 */     if (this == paramObject)
/* 148 */       return true; 
/* 149 */     if (!(paramObject instanceof KeyIdentifier))
/* 150 */       return false; 
/* 151 */     byte[] arrayOfByte = ((KeyIdentifier)paramObject).octetString;
/* 152 */     return Arrays.equals(this.octetString, arrayOfByte);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/KeyIdentifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */