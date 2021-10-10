/*    */ package sun.security.krb5.internal;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import sun.security.krb5.Asn1Exception;
/*    */ import sun.security.util.DerOutputStream;
/*    */ import sun.security.util.DerValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LastReqEntry
/*    */ {
/*    */   private int lrType;
/*    */   private KerberosTime lrValue;
/*    */   
/*    */   private LastReqEntry() {}
/*    */   
/*    */   public LastReqEntry(int paramInt, KerberosTime paramKerberosTime) {
/* 45 */     this.lrType = paramInt;
/* 46 */     this.lrValue = paramKerberosTime;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LastReqEntry(DerValue paramDerValue) throws Asn1Exception, IOException {
/* 57 */     if (paramDerValue.getTag() != 48) {
/* 58 */       throw new Asn1Exception(906);
/*    */     }
/*    */     
/* 61 */     DerValue derValue = paramDerValue.getData().getDerValue();
/* 62 */     if ((derValue.getTag() & 0x1F) == 0) {
/* 63 */       this.lrType = derValue.getData().getBigInteger().intValue();
/*    */     } else {
/*    */       
/* 66 */       throw new Asn1Exception(906);
/*    */     } 
/* 68 */     this.lrValue = KerberosTime.parse(paramDerValue.getData(), (byte)1, false);
/* 69 */     if (paramDerValue.getData().available() > 0) {
/* 70 */       throw new Asn1Exception(906);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 80 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 81 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 82 */     derOutputStream2.putInteger(this.lrType);
/* 83 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/* 84 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), this.lrValue.asn1Encode());
/* 85 */     derOutputStream2 = new DerOutputStream();
/* 86 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 87 */     return derOutputStream2.toByteArray();
/*    */   }
/*    */   
/*    */   public Object clone() {
/* 91 */     LastReqEntry lastReqEntry = new LastReqEntry();
/* 92 */     lastReqEntry.lrType = this.lrType;
/* 93 */     lastReqEntry.lrValue = this.lrValue;
/* 94 */     return lastReqEntry;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/LastReqEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */