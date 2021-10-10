/*     */ package sun.security.timestamp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Date;
/*     */ import sun.security.util.DerValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimestampToken
/*     */ {
/*     */   private int version;
/*     */   private ObjectIdentifier policy;
/*     */   private BigInteger serialNumber;
/*     */   private AlgorithmId hashAlgorithm;
/*     */   private byte[] hashedMessage;
/*     */   private Date genTime;
/*     */   private BigInteger nonce;
/*     */   
/*     */   public TimestampToken(byte[] paramArrayOfbyte) throws IOException {
/*  90 */     if (paramArrayOfbyte == null) {
/*  91 */       throw new IOException("No timestamp token info");
/*     */     }
/*  93 */     parse(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getDate() {
/* 102 */     return this.genTime;
/*     */   }
/*     */   
/*     */   public AlgorithmId getHashAlgorithm() {
/* 106 */     return this.hashAlgorithm;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getHashedMessage() {
/* 111 */     return this.hashedMessage;
/*     */   }
/*     */   
/*     */   public BigInteger getNonce() {
/* 115 */     return this.nonce;
/*     */   }
/*     */   
/*     */   public String getPolicyID() {
/* 119 */     return this.policy.toString();
/*     */   }
/*     */   
/*     */   public BigInteger getSerialNumber() {
/* 123 */     return this.serialNumber;
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
/*     */   private void parse(byte[] paramArrayOfbyte) throws IOException {
/* 136 */     DerValue derValue1 = new DerValue(paramArrayOfbyte);
/* 137 */     if (derValue1.tag != 48) {
/* 138 */       throw new IOException("Bad encoding for timestamp token info");
/*     */     }
/*     */     
/* 141 */     this.version = derValue1.data.getInteger();
/*     */ 
/*     */     
/* 144 */     this.policy = derValue1.data.getOID();
/*     */ 
/*     */     
/* 147 */     DerValue derValue2 = derValue1.data.getDerValue();
/* 148 */     this.hashAlgorithm = AlgorithmId.parse(derValue2.data.getDerValue());
/* 149 */     this.hashedMessage = derValue2.data.getOctetString();
/*     */ 
/*     */     
/* 152 */     this.serialNumber = derValue1.data.getBigInteger();
/*     */ 
/*     */     
/* 155 */     this.genTime = derValue1.data.getGeneralizedTime();
/*     */ 
/*     */     
/* 158 */     while (derValue1.data.available() > 0) {
/* 159 */       DerValue derValue = derValue1.data.getDerValue();
/* 160 */       if (derValue.tag == 2) {
/* 161 */         this.nonce = derValue.getBigInteger();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/timestamp/TimestampToken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */