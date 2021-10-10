/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ public class SerialNumber
/*     */ {
/*     */   private BigInteger serialNum;
/*     */   
/*     */   private void construct(DerValue paramDerValue) throws IOException {
/*  44 */     this.serialNum = paramDerValue.getBigInteger();
/*  45 */     if (paramDerValue.data.available() != 0) {
/*  46 */       throw new IOException("Excess SerialNumber data");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerialNumber(BigInteger paramBigInteger) {
/*  56 */     this.serialNum = paramBigInteger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerialNumber(int paramInt) {
/*  65 */     this.serialNum = BigInteger.valueOf(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerialNumber(DerInputStream paramDerInputStream) throws IOException {
/*  75 */     DerValue derValue = paramDerInputStream.getDerValue();
/*  76 */     construct(derValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerialNumber(DerValue paramDerValue) throws IOException {
/*  86 */     construct(paramDerValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerialNumber(InputStream paramInputStream) throws IOException {
/*  96 */     DerValue derValue = new DerValue(paramInputStream);
/*  97 */     construct(derValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 104 */     return "SerialNumber: [" + Debug.toHexString(this.serialNum) + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 114 */     paramDerOutputStream.putInteger(this.serialNum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getNumber() {
/* 121 */     return this.serialNum;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/SerialNumber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */