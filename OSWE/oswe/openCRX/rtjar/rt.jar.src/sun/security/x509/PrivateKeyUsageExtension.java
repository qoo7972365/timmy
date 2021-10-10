/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateExpiredException;
/*     */ import java.security.cert.CertificateNotYetValidException;
/*     */ import java.security.cert.CertificateParsingException;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrivateKeyUsageExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.PrivateKeyUsage";
/*     */   public static final String NAME = "PrivateKeyUsage";
/*     */   public static final String NOT_BEFORE = "not_before";
/*     */   public static final String NOT_AFTER = "not_after";
/*     */   private static final byte TAG_BEFORE = 0;
/*     */   private static final byte TAG_AFTER = 1;
/*  80 */   private Date notBefore = null;
/*  81 */   private Date notAfter = null;
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/*  85 */     if (this.notBefore == null && this.notAfter == null) {
/*  86 */       this.extensionValue = null;
/*     */       return;
/*     */     } 
/*  89 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*     */     
/*  91 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*  92 */     if (this.notBefore != null) {
/*  93 */       DerOutputStream derOutputStream = new DerOutputStream();
/*  94 */       derOutputStream.putGeneralizedTime(this.notBefore);
/*  95 */       derOutputStream2.writeImplicit(DerValue.createTag(-128, false, (byte)0), derOutputStream);
/*     */     } 
/*     */     
/*  98 */     if (this.notAfter != null) {
/*  99 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 100 */       derOutputStream.putGeneralizedTime(this.notAfter);
/* 101 */       derOutputStream2.writeImplicit(DerValue.createTag(-128, false, (byte)1), derOutputStream);
/*     */     } 
/*     */     
/* 104 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 105 */     this.extensionValue = derOutputStream1.toByteArray();
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
/*     */   public PrivateKeyUsageExtension(Date paramDate1, Date paramDate2) throws IOException {
/* 118 */     this.notBefore = paramDate1;
/* 119 */     this.notAfter = paramDate2;
/*     */     
/* 121 */     this.extensionId = PKIXExtensions.PrivateKeyUsage_Id;
/* 122 */     this.critical = false;
/* 123 */     encodeThis();
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
/*     */   
/*     */   public PrivateKeyUsageExtension(Boolean paramBoolean, Object paramObject) throws CertificateException, IOException {
/* 137 */     this.extensionId = PKIXExtensions.PrivateKeyUsage_Id;
/* 138 */     this.critical = paramBoolean.booleanValue();
/*     */     
/* 140 */     this.extensionValue = (byte[])paramObject;
/* 141 */     DerInputStream derInputStream = new DerInputStream(this.extensionValue);
/* 142 */     DerValue[] arrayOfDerValue = derInputStream.getSequence(2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     for (byte b = 0; b < arrayOfDerValue.length; b++) {
/* 148 */       DerValue derValue = arrayOfDerValue[b];
/*     */       
/* 150 */       if (derValue.isContextSpecific((byte)0) && 
/* 151 */         !derValue.isConstructed()) {
/* 152 */         if (this.notBefore != null) {
/* 153 */           throw new CertificateParsingException("Duplicate notBefore in PrivateKeyUsage.");
/*     */         }
/*     */         
/* 156 */         derValue.resetTag((byte)24);
/* 157 */         derInputStream = new DerInputStream(derValue.toByteArray());
/* 158 */         this.notBefore = derInputStream.getGeneralizedTime();
/*     */       }
/* 160 */       else if (derValue.isContextSpecific((byte)1) && 
/* 161 */         !derValue.isConstructed()) {
/* 162 */         if (this.notAfter != null) {
/* 163 */           throw new CertificateParsingException("Duplicate notAfter in PrivateKeyUsage.");
/*     */         }
/*     */         
/* 166 */         derValue.resetTag((byte)24);
/* 167 */         derInputStream = new DerInputStream(derValue.toByteArray());
/* 168 */         this.notAfter = derInputStream.getGeneralizedTime();
/*     */       } else {
/* 170 */         throw new IOException("Invalid encoding of PrivateKeyUsageExtension");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 179 */     return super.toString() + "PrivateKeyUsage: [\n" + ((this.notBefore == null) ? "" : ("From: " + this.notBefore
/*     */       
/* 181 */       .toString() + ", ")) + ((this.notAfter == null) ? "" : ("To: " + this.notAfter
/* 182 */       .toString())) + "]\n";
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
/*     */   public void valid() throws CertificateNotYetValidException, CertificateExpiredException {
/* 195 */     Date date = new Date();
/* 196 */     valid(date);
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
/*     */   
/*     */   public void valid(Date paramDate) throws CertificateNotYetValidException, CertificateExpiredException {
/* 210 */     Objects.requireNonNull(paramDate);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     if (this.notBefore != null && this.notBefore.after(paramDate)) {
/* 217 */       throw new CertificateNotYetValidException("NotBefore: " + this.notBefore
/* 218 */           .toString());
/*     */     }
/* 220 */     if (this.notAfter != null && this.notAfter.before(paramDate)) {
/* 221 */       throw new CertificateExpiredException("NotAfter: " + this.notAfter
/* 222 */           .toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 233 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 234 */     if (this.extensionValue == null) {
/* 235 */       this.extensionId = PKIXExtensions.PrivateKeyUsage_Id;
/* 236 */       this.critical = false;
/* 237 */       encodeThis();
/*     */     } 
/* 239 */     encode(derOutputStream);
/* 240 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws CertificateException, IOException {
/* 249 */     if (!(paramObject instanceof Date)) {
/* 250 */       throw new CertificateException("Attribute must be of type Date.");
/*     */     }
/* 252 */     if (paramString.equalsIgnoreCase("not_before")) {
/* 253 */       this.notBefore = (Date)paramObject;
/* 254 */     } else if (paramString.equalsIgnoreCase("not_after")) {
/* 255 */       this.notAfter = (Date)paramObject;
/*     */     } else {
/* 257 */       throw new CertificateException("Attribute name not recognized by CertAttrSet:PrivateKeyUsage.");
/*     */     } 
/*     */     
/* 260 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date get(String paramString) throws CertificateException {
/* 268 */     if (paramString.equalsIgnoreCase("not_before"))
/* 269 */       return new Date(this.notBefore.getTime()); 
/* 270 */     if (paramString.equalsIgnoreCase("not_after")) {
/* 271 */       return new Date(this.notAfter.getTime());
/*     */     }
/* 273 */     throw new CertificateException("Attribute name not recognized by CertAttrSet:PrivateKeyUsage.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws CertificateException, IOException {
/* 283 */     if (paramString.equalsIgnoreCase("not_before")) {
/* 284 */       this.notBefore = null;
/* 285 */     } else if (paramString.equalsIgnoreCase("not_after")) {
/* 286 */       this.notAfter = null;
/*     */     } else {
/* 288 */       throw new CertificateException("Attribute name not recognized by CertAttrSet:PrivateKeyUsage.");
/*     */     } 
/*     */     
/* 291 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 299 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 300 */     attributeNameEnumeration.addElement("not_before");
/* 301 */     attributeNameEnumeration.addElement("not_after");
/*     */     
/* 303 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 310 */     return "PrivateKeyUsage";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/PrivateKeyUsageExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */