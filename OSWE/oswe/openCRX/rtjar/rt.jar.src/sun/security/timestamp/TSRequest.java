/*     */ package sun.security.timestamp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.X509Extension;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.ObjectIdentifier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TSRequest
/*     */ {
/*  70 */   private int version = 1;
/*     */   
/*  72 */   private AlgorithmId hashAlgorithmId = null;
/*     */   
/*     */   private byte[] hashValue;
/*     */   
/*  76 */   private String policyId = null;
/*     */   
/*  78 */   private BigInteger nonce = null;
/*     */   
/*     */   private boolean returnCertificate = false;
/*     */   
/*  82 */   private X509Extension[] extensions = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TSRequest(String paramString, byte[] paramArrayOfbyte, MessageDigest paramMessageDigest) throws NoSuchAlgorithmException {
/*  94 */     this.policyId = paramString;
/*  95 */     this.hashAlgorithmId = AlgorithmId.get(paramMessageDigest.getAlgorithm());
/*  96 */     this.hashValue = paramMessageDigest.digest(paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   public byte[] getHashedMessage() {
/* 100 */     return (byte[])this.hashValue.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVersion(int paramInt) {
/* 109 */     this.version = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolicyId(String paramString) {
/* 118 */     this.policyId = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNonce(BigInteger paramBigInteger) {
/* 128 */     this.nonce = paramBigInteger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void requestCertificate(boolean paramBoolean) {
/* 138 */     this.returnCertificate = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtensions(X509Extension[] paramArrayOfX509Extension) {
/* 147 */     this.extensions = paramArrayOfX509Extension;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] encode() throws IOException {
/* 152 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*     */ 
/*     */     
/* 155 */     derOutputStream1.putInteger(this.version);
/*     */ 
/*     */     
/* 158 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 159 */     this.hashAlgorithmId.encode(derOutputStream2);
/* 160 */     derOutputStream2.putOctetString(this.hashValue);
/* 161 */     derOutputStream1.write((byte)48, derOutputStream2);
/*     */ 
/*     */ 
/*     */     
/* 165 */     if (this.policyId != null) {
/* 166 */       derOutputStream1.putOID(new ObjectIdentifier(this.policyId));
/*     */     }
/* 168 */     if (this.nonce != null) {
/* 169 */       derOutputStream1.putInteger(this.nonce);
/*     */     }
/* 171 */     if (this.returnCertificate) {
/* 172 */       derOutputStream1.putBoolean(true);
/*     */     }
/*     */     
/* 175 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 176 */     derOutputStream3.write((byte)48, derOutputStream1);
/* 177 */     return derOutputStream3.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/timestamp/TSRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */