/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.internal.ccache.CCacheOutputStream;
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
/*     */ public class AuthorizationDataEntry
/*     */   implements Cloneable
/*     */ {
/*     */   public int adType;
/*     */   public byte[] adData;
/*     */   
/*     */   private AuthorizationDataEntry() {}
/*     */   
/*     */   public AuthorizationDataEntry(int paramInt, byte[] paramArrayOfbyte) {
/*  48 */     this.adType = paramInt;
/*  49 */     this.adData = paramArrayOfbyte;
/*     */   }
/*     */   
/*     */   public Object clone() {
/*  53 */     AuthorizationDataEntry authorizationDataEntry = new AuthorizationDataEntry();
/*     */     
/*  55 */     authorizationDataEntry.adType = this.adType;
/*  56 */     if (this.adData != null) {
/*  57 */       authorizationDataEntry.adData = new byte[this.adData.length];
/*  58 */       System.arraycopy(this.adData, 0, authorizationDataEntry.adData, 0, this.adData.length);
/*     */     } 
/*     */     
/*  61 */     return authorizationDataEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthorizationDataEntry(DerValue paramDerValue) throws Asn1Exception, IOException {
/*  70 */     if (paramDerValue.getTag() != 48) {
/*  71 */       throw new Asn1Exception(906);
/*     */     }
/*  73 */     DerValue derValue = paramDerValue.getData().getDerValue();
/*  74 */     if ((derValue.getTag() & 0x1F) == 0) {
/*  75 */       this.adType = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/*  77 */       throw new Asn1Exception(906);
/*     */     } 
/*  79 */     derValue = paramDerValue.getData().getDerValue();
/*  80 */     if ((derValue.getTag() & 0x1F) == 1) {
/*  81 */       this.adData = derValue.getData().getOctetString();
/*     */     } else {
/*  83 */       throw new Asn1Exception(906);
/*     */     } 
/*  85 */     if (paramDerValue.getData().available() > 0) {
/*  86 */       throw new Asn1Exception(906);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/*  97 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*  98 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*  99 */     derOutputStream2.putInteger(this.adType);
/* 100 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/* 101 */     derOutputStream2 = new DerOutputStream();
/* 102 */     derOutputStream2.putOctetString(this.adData);
/* 103 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/* 104 */     derOutputStream2 = new DerOutputStream();
/* 105 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 106 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntry(CCacheOutputStream paramCCacheOutputStream) throws IOException {
/* 116 */     paramCCacheOutputStream.write16(this.adType);
/* 117 */     paramCCacheOutputStream.write32(this.adData.length);
/* 118 */     paramCCacheOutputStream.write(this.adData, 0, this.adData.length);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 122 */     return "adType=" + this.adType + " adData.length=" + this.adData.length;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/AuthorizationDataEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */