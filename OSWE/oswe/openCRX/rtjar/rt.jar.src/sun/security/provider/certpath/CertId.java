/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.x509.AlgorithmId;
/*     */ import sun.security.x509.SerialNumber;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertId
/*     */ {
/*     */   private static final boolean debug = false;
/*  61 */   private static final AlgorithmId SHA1_ALGID = new AlgorithmId(AlgorithmId.SHA_oid);
/*     */   
/*     */   private final AlgorithmId hashAlgId;
/*     */   private final byte[] issuerNameHash;
/*     */   private final byte[] issuerKeyHash;
/*     */   private final SerialNumber certSerialNumber;
/*  67 */   private int myhash = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertId(X509Certificate paramX509Certificate, SerialNumber paramSerialNumber) throws IOException {
/*  75 */     this(paramX509Certificate.getSubjectX500Principal(), paramX509Certificate
/*  76 */         .getPublicKey(), paramSerialNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertId(X500Principal paramX500Principal, PublicKey paramPublicKey, SerialNumber paramSerialNumber) throws IOException {
/*  83 */     MessageDigest messageDigest = null;
/*     */     try {
/*  85 */       messageDigest = MessageDigest.getInstance("SHA1");
/*  86 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  87 */       throw new IOException("Unable to create CertId", noSuchAlgorithmException);
/*     */     } 
/*  89 */     this.hashAlgId = SHA1_ALGID;
/*  90 */     messageDigest.update(paramX500Principal.getEncoded());
/*  91 */     this.issuerNameHash = messageDigest.digest();
/*     */ 
/*     */     
/*  94 */     byte[] arrayOfByte1 = paramPublicKey.getEncoded();
/*  95 */     DerValue derValue = new DerValue(arrayOfByte1);
/*  96 */     DerValue[] arrayOfDerValue = new DerValue[2];
/*  97 */     arrayOfDerValue[0] = derValue.data.getDerValue();
/*  98 */     arrayOfDerValue[1] = derValue.data.getDerValue();
/*  99 */     byte[] arrayOfByte2 = arrayOfDerValue[1].getBitString();
/* 100 */     messageDigest.update(arrayOfByte2);
/* 101 */     this.issuerKeyHash = messageDigest.digest();
/* 102 */     this.certSerialNumber = paramSerialNumber;
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
/*     */   public CertId(DerInputStream paramDerInputStream) throws IOException {
/* 119 */     this.hashAlgId = AlgorithmId.parse(paramDerInputStream.getDerValue());
/* 120 */     this.issuerNameHash = paramDerInputStream.getOctetString();
/* 121 */     this.issuerKeyHash = paramDerInputStream.getOctetString();
/* 122 */     this.certSerialNumber = new SerialNumber(paramDerInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmId getHashAlgorithm() {
/* 129 */     return this.hashAlgId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getIssuerNameHash() {
/* 136 */     return this.issuerNameHash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getIssuerKeyHash() {
/* 143 */     return this.issuerKeyHash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getSerialNumber() {
/* 150 */     return this.certSerialNumber.getNumber();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 159 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 160 */     this.hashAlgId.encode(derOutputStream);
/* 161 */     derOutputStream.putOctetString(this.issuerNameHash);
/* 162 */     derOutputStream.putOctetString(this.issuerKeyHash);
/* 163 */     this.certSerialNumber.encode(derOutputStream);
/* 164 */     paramDerOutputStream.write((byte)48, derOutputStream);
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
/*     */   public int hashCode() {
/* 179 */     if (this.myhash == -1) {
/* 180 */       this.myhash = this.hashAlgId.hashCode(); byte b;
/* 181 */       for (b = 0; b < this.issuerNameHash.length; b++) {
/* 182 */         this.myhash += this.issuerNameHash[b] * b;
/*     */       }
/* 184 */       for (b = 0; b < this.issuerKeyHash.length; b++) {
/* 185 */         this.myhash += this.issuerKeyHash[b] * b;
/*     */       }
/* 187 */       this.myhash += this.certSerialNumber.getNumber().hashCode();
/*     */     } 
/* 189 */     return this.myhash;
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
/*     */   public boolean equals(Object paramObject) {
/* 202 */     if (this == paramObject) {
/* 203 */       return true;
/*     */     }
/* 205 */     if (paramObject == null || !(paramObject instanceof CertId)) {
/* 206 */       return false;
/*     */     }
/*     */     
/* 209 */     CertId certId = (CertId)paramObject;
/* 210 */     if (this.hashAlgId.equals(certId.getHashAlgorithm()) && 
/* 211 */       Arrays.equals(this.issuerNameHash, certId.getIssuerNameHash()) && 
/* 212 */       Arrays.equals(this.issuerKeyHash, certId.getIssuerKeyHash()) && this.certSerialNumber
/* 213 */       .getNumber().equals(certId.getSerialNumber())) {
/* 214 */       return true;
/*     */     }
/* 216 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 224 */     StringBuilder stringBuilder = new StringBuilder();
/* 225 */     stringBuilder.append("CertId \n");
/* 226 */     stringBuilder.append("Algorithm: " + this.hashAlgId.toString() + "\n");
/* 227 */     stringBuilder.append("issuerNameHash \n");
/* 228 */     HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 229 */     stringBuilder.append(hexDumpEncoder.encode(this.issuerNameHash));
/* 230 */     stringBuilder.append("\nissuerKeyHash: \n");
/* 231 */     stringBuilder.append(hexDumpEncoder.encode(this.issuerKeyHash));
/* 232 */     stringBuilder.append("\n" + this.certSerialNumber.toString());
/* 233 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/CertId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */