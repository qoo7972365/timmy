/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.internal.util.KerberosString;
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
/*     */ 
/*     */ public class ETypeInfo
/*     */ {
/*     */   private int etype;
/*  47 */   private String salt = null;
/*     */   
/*     */   private static final byte TAG_TYPE = 0;
/*     */   
/*     */   private static final byte TAG_VALUE = 1;
/*     */   
/*     */   private ETypeInfo() {}
/*     */   
/*     */   public ETypeInfo(int paramInt, String paramString) {
/*  56 */     this.etype = paramInt;
/*  57 */     this.salt = paramString;
/*     */   }
/*     */   
/*     */   public Object clone() {
/*  61 */     return new ETypeInfo(this.etype, this.salt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ETypeInfo(DerValue paramDerValue) throws Asn1Exception, IOException {
/*  72 */     DerValue derValue = null;
/*     */     
/*  74 */     if (paramDerValue.getTag() != 48) {
/*  75 */       throw new Asn1Exception(906);
/*     */     }
/*     */ 
/*     */     
/*  79 */     derValue = paramDerValue.getData().getDerValue();
/*  80 */     if ((derValue.getTag() & 0x1F) == 0) {
/*  81 */       this.etype = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/*     */       
/*  84 */       throw new Asn1Exception(906);
/*     */     } 
/*     */     
/*  87 */     if (paramDerValue.getData().available() > 0) {
/*  88 */       derValue = paramDerValue.getData().getDerValue();
/*  89 */       if ((derValue.getTag() & 0x1F) == 1) {
/*  90 */         byte[] arrayOfByte = derValue.getData().getOctetString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 101 */         if (KerberosString.MSNAME) {
/* 102 */           this.salt = new String(arrayOfByte, "UTF8");
/*     */         } else {
/* 104 */           this.salt = new String(arrayOfByte);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     if (paramDerValue.getData().available() > 0) {
/* 110 */       throw new Asn1Exception(906);
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 122 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 123 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 125 */     derOutputStream2.putInteger(this.etype);
/* 126 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/*     */ 
/*     */     
/* 129 */     if (this.salt != null) {
/* 130 */       derOutputStream2 = new DerOutputStream();
/* 131 */       if (KerberosString.MSNAME) {
/* 132 */         derOutputStream2.putOctetString(this.salt.getBytes("UTF8"));
/*     */       } else {
/* 134 */         derOutputStream2.putOctetString(this.salt.getBytes());
/*     */       } 
/* 136 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/*     */     } 
/*     */ 
/*     */     
/* 140 */     derOutputStream2 = new DerOutputStream();
/* 141 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 142 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEType() {
/* 147 */     return this.etype;
/*     */   }
/*     */   
/*     */   public String getSalt() {
/* 151 */     return this.salt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/ETypeInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */