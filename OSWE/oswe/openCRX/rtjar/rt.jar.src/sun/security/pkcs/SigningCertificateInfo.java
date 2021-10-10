/*     */ package sun.security.pkcs;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ public class SigningCertificateInfo
/*     */ {
/*  86 */   private byte[] ber = null;
/*     */   
/*  88 */   private ESSCertId[] certId = null;
/*     */   
/*     */   public SigningCertificateInfo(byte[] paramArrayOfbyte) throws IOException {
/*  91 */     parse(paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  95 */     StringBuffer stringBuffer = new StringBuffer();
/*  96 */     stringBuffer.append("[\n");
/*  97 */     for (byte b = 0; b < this.certId.length; b++) {
/*  98 */       stringBuffer.append(this.certId[b].toString());
/*     */     }
/*     */     
/* 101 */     stringBuffer.append("\n]");
/*     */     
/* 103 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(byte[] paramArrayOfbyte) throws IOException {
/* 109 */     DerValue derValue = new DerValue(paramArrayOfbyte);
/* 110 */     if (derValue.tag != 48) {
/* 111 */       throw new IOException("Bad encoding for signingCertificate");
/*     */     }
/*     */ 
/*     */     
/* 115 */     DerValue[] arrayOfDerValue = derValue.data.getSequence(1);
/* 116 */     this.certId = new ESSCertId[arrayOfDerValue.length];
/* 117 */     for (byte b = 0; b < arrayOfDerValue.length; b++) {
/* 118 */       this.certId[b] = new ESSCertId(arrayOfDerValue[b]);
/*     */     }
/*     */ 
/*     */     
/* 122 */     if (derValue.data.available() > 0) {
/* 123 */       DerValue[] arrayOfDerValue1 = derValue.data.getSequence(1);
/* 124 */       for (byte b1 = 0; b1 < arrayOfDerValue1.length; b1++);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs/SigningCertificateInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */