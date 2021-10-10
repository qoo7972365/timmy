/*     */ package sun.security.pkcs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.x509.GeneralNames;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ESSCertId
/*     */ {
/*     */   private static volatile HexDumpEncoder hexDumper;
/*     */   private byte[] certHash;
/*     */   private GeneralNames issuer;
/*     */   private SerialNumber serialNumber;
/*     */   
/*     */   ESSCertId(DerValue paramDerValue) throws IOException {
/* 141 */     this.certHash = paramDerValue.data.getDerValue().toByteArray();
/*     */ 
/*     */     
/* 144 */     if (paramDerValue.data.available() > 0) {
/* 145 */       DerValue derValue = paramDerValue.data.getDerValue();
/*     */       
/* 147 */       this.issuer = new GeneralNames(derValue.data.getDerValue());
/*     */       
/* 149 */       this.serialNumber = new SerialNumber(derValue.data.getDerValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 154 */     StringBuffer stringBuffer = new StringBuffer();
/* 155 */     stringBuffer.append("[\n\tCertificate hash (SHA-1):\n");
/* 156 */     if (hexDumper == null) {
/* 157 */       hexDumper = new HexDumpEncoder();
/*     */     }
/* 159 */     stringBuffer.append(hexDumper.encode(this.certHash));
/* 160 */     if (this.issuer != null && this.serialNumber != null) {
/* 161 */       stringBuffer.append("\n\tIssuer: " + this.issuer + "\n");
/* 162 */       stringBuffer.append("\t" + this.serialNumber);
/*     */     } 
/* 164 */     stringBuffer.append("\n]");
/* 165 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs/ESSCertId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */