/*     */ package java.security.cert;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.misc.HexDumpEncoder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolicyQualifierInfo
/*     */ {
/*     */   private byte[] mEncoded;
/*     */   private String mId;
/*     */   private byte[] mData;
/*     */   private String pqiString;
/*     */   
/*     */   public PolicyQualifierInfo(byte[] paramArrayOfbyte) throws IOException {
/* 101 */     this.mEncoded = (byte[])paramArrayOfbyte.clone();
/*     */     
/* 103 */     DerValue derValue = new DerValue(this.mEncoded);
/* 104 */     if (derValue.tag != 48) {
/* 105 */       throw new IOException("Invalid encoding for PolicyQualifierInfo");
/*     */     }
/* 107 */     this.mId = derValue.data.getDerValue().getOID().toString();
/* 108 */     byte[] arrayOfByte = derValue.data.toByteArray();
/* 109 */     if (arrayOfByte == null) {
/* 110 */       this.mData = null;
/*     */     } else {
/* 112 */       this.mData = new byte[arrayOfByte.length];
/* 113 */       System.arraycopy(arrayOfByte, 0, this.mData, 0, arrayOfByte.length);
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
/*     */   public final String getPolicyQualifierId() {
/* 126 */     return this.mId;
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
/*     */   public final byte[] getEncoded() {
/* 138 */     return (byte[])this.mEncoded.clone();
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
/*     */   public final byte[] getPolicyQualifier() {
/* 150 */     return (this.mData == null) ? null : (byte[])this.mData.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 161 */     if (this.pqiString != null)
/* 162 */       return this.pqiString; 
/* 163 */     HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 164 */     StringBuffer stringBuffer = new StringBuffer();
/* 165 */     stringBuffer.append("PolicyQualifierInfo: [\n");
/* 166 */     stringBuffer.append("  qualifierID: " + this.mId + "\n");
/* 167 */     stringBuffer.append("  qualifier: " + ((this.mData == null) ? "null" : hexDumpEncoder
/* 168 */         .encodeBuffer(this.mData)) + "\n");
/* 169 */     stringBuffer.append("]");
/* 170 */     this.pqiString = stringBuffer.toString();
/* 171 */     return this.pqiString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/PolicyQualifierInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */