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
/*     */ 
/*     */ public class ETypeInfo2
/*     */ {
/*     */   private int etype;
/*  48 */   private String saltStr = null;
/*  49 */   private byte[] s2kparams = null;
/*     */   
/*     */   private static final byte TAG_TYPE = 0;
/*     */   
/*     */   private static final byte TAG_VALUE1 = 1;
/*     */   private static final byte TAG_VALUE2 = 2;
/*     */   
/*     */   private ETypeInfo2() {}
/*     */   
/*     */   public ETypeInfo2(int paramInt, String paramString, byte[] paramArrayOfbyte) {
/*  59 */     this.etype = paramInt;
/*  60 */     this.saltStr = paramString;
/*  61 */     if (paramArrayOfbyte != null) {
/*  62 */       this.s2kparams = (byte[])paramArrayOfbyte.clone();
/*     */     }
/*     */   }
/*     */   
/*     */   public Object clone() {
/*  67 */     ETypeInfo2 eTypeInfo2 = new ETypeInfo2();
/*  68 */     eTypeInfo2.etype = this.etype;
/*  69 */     eTypeInfo2.saltStr = this.saltStr;
/*  70 */     if (this.s2kparams != null) {
/*  71 */       eTypeInfo2.s2kparams = new byte[this.s2kparams.length];
/*  72 */       System.arraycopy(this.s2kparams, 0, eTypeInfo2.s2kparams, 0, this.s2kparams.length);
/*     */     } 
/*     */     
/*  75 */     return eTypeInfo2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ETypeInfo2(DerValue paramDerValue) throws Asn1Exception, IOException {
/*  86 */     DerValue derValue = null;
/*     */     
/*  88 */     if (paramDerValue.getTag() != 48) {
/*  89 */       throw new Asn1Exception(906);
/*     */     }
/*     */ 
/*     */     
/*  93 */     derValue = paramDerValue.getData().getDerValue();
/*  94 */     if ((derValue.getTag() & 0x1F) == 0) {
/*  95 */       this.etype = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/*     */       
/*  98 */       throw new Asn1Exception(906);
/*     */     } 
/*     */     
/* 101 */     if (paramDerValue.getData().available() > 0 && (
/* 102 */       paramDerValue.getData().peekByte() & 0x1F) == 1) {
/* 103 */       derValue = paramDerValue.getData().getDerValue();
/* 104 */       this
/* 105 */         .saltStr = (new KerberosString(derValue.getData().getDerValue())).toString();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 110 */     if (paramDerValue.getData().available() > 0 && (
/* 111 */       paramDerValue.getData().peekByte() & 0x1F) == 2) {
/* 112 */       derValue = paramDerValue.getData().getDerValue();
/* 113 */       this.s2kparams = derValue.getData().getOctetString();
/*     */     } 
/*     */ 
/*     */     
/* 117 */     if (paramDerValue.getData().available() > 0) {
/* 118 */       throw new Asn1Exception(906);
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
/* 130 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 131 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 133 */     derOutputStream2.putInteger(this.etype);
/* 134 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/*     */ 
/*     */     
/* 137 */     if (this.saltStr != null) {
/* 138 */       derOutputStream2 = new DerOutputStream();
/* 139 */       derOutputStream2.putDerValue((new KerberosString(this.saltStr)).toDerValue());
/* 140 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/*     */     } 
/*     */     
/* 143 */     if (this.s2kparams != null) {
/* 144 */       derOutputStream2 = new DerOutputStream();
/* 145 */       derOutputStream2.putOctetString(this.s2kparams);
/* 146 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), derOutputStream2);
/*     */     } 
/*     */ 
/*     */     
/* 150 */     derOutputStream2 = new DerOutputStream();
/* 151 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 152 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEType() {
/* 157 */     return this.etype;
/*     */   }
/*     */   
/*     */   public String getSalt() {
/* 161 */     return this.saltStr;
/*     */   }
/*     */   
/*     */   public byte[] getParams() {
/* 165 */     return (this.s2kparams == null) ? null : (byte[])this.s2kparams.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/ETypeInfo2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */