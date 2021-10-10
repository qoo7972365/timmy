/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.Extension;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
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
/*     */ public class InvalidityDateExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String NAME = "InvalidityDate";
/*     */   public static final String DATE = "date";
/*     */   private Date date;
/*     */   
/*     */   private void encodeThis() throws IOException {
/*  72 */     if (this.date == null) {
/*  73 */       this.extensionValue = null;
/*     */       return;
/*     */     } 
/*  76 */     DerOutputStream derOutputStream = new DerOutputStream();
/*  77 */     derOutputStream.putGeneralizedTime(this.date);
/*  78 */     this.extensionValue = derOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InvalidityDateExtension(Date paramDate) throws IOException {
/*  88 */     this(false, paramDate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InvalidityDateExtension(boolean paramBoolean, Date paramDate) throws IOException {
/*  99 */     this.extensionId = PKIXExtensions.InvalidityDate_Id;
/* 100 */     this.critical = paramBoolean;
/* 101 */     this.date = paramDate;
/* 102 */     encodeThis();
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
/*     */   public InvalidityDateExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 115 */     this.extensionId = PKIXExtensions.InvalidityDate_Id;
/* 116 */     this.critical = paramBoolean.booleanValue();
/* 117 */     this.extensionValue = (byte[])paramObject;
/* 118 */     DerValue derValue = new DerValue(this.extensionValue);
/* 119 */     this.date = derValue.getGeneralizedTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 126 */     if (!(paramObject instanceof Date)) {
/* 127 */       throw new IOException("Attribute must be of type Date.");
/*     */     }
/* 129 */     if (paramString.equalsIgnoreCase("date")) {
/* 130 */       this.date = (Date)paramObject;
/*     */     } else {
/* 132 */       throw new IOException("Name not supported by InvalidityDateExtension");
/*     */     } 
/*     */     
/* 135 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date get(String paramString) throws IOException {
/* 142 */     if (paramString.equalsIgnoreCase("date")) {
/* 143 */       if (this.date == null) {
/* 144 */         return null;
/*     */       }
/* 146 */       return new Date(this.date.getTime());
/*     */     } 
/*     */     
/* 149 */     throw new IOException("Name not supported by InvalidityDateExtension");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 158 */     if (paramString.equalsIgnoreCase("date")) {
/* 159 */       this.date = null;
/*     */     } else {
/* 161 */       throw new IOException("Name not supported by InvalidityDateExtension");
/*     */     } 
/*     */     
/* 164 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 171 */     return super.toString() + "    Invalidity Date: " + String.valueOf(this.date);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 181 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */     
/* 183 */     if (this.extensionValue == null) {
/* 184 */       this.extensionId = PKIXExtensions.InvalidityDate_Id;
/* 185 */       this.critical = false;
/* 186 */       encodeThis();
/*     */     } 
/* 188 */     encode(derOutputStream);
/* 189 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 197 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 198 */     attributeNameEnumeration.addElement("date");
/*     */     
/* 200 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 207 */     return "InvalidityDate";
/*     */   }
/*     */ 
/*     */   
/*     */   public static InvalidityDateExtension toImpl(Extension paramExtension) throws IOException {
/* 212 */     if (paramExtension instanceof InvalidityDateExtension) {
/* 213 */       return (InvalidityDateExtension)paramExtension;
/*     */     }
/* 215 */     return new InvalidityDateExtension(
/* 216 */         Boolean.valueOf(paramExtension.isCritical()), paramExtension.getValue());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/InvalidityDateExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */