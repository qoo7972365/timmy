/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
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
/*     */ public class IssuerAlternativeNameExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.IssuerAlternativeName";
/*     */   public static final String NAME = "IssuerAlternativeName";
/*     */   public static final String ISSUER_NAME = "issuer_name";
/*  65 */   GeneralNames names = null;
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/*  69 */     if (this.names == null || this.names.isEmpty()) {
/*  70 */       this.extensionValue = null;
/*     */       return;
/*     */     } 
/*  73 */     DerOutputStream derOutputStream = new DerOutputStream();
/*  74 */     this.names.encode(derOutputStream);
/*  75 */     this.extensionValue = derOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IssuerAlternativeNameExtension(GeneralNames paramGeneralNames) throws IOException {
/*  86 */     this.names = paramGeneralNames;
/*  87 */     this.extensionId = PKIXExtensions.IssuerAlternativeName_Id;
/*  88 */     this.critical = false;
/*  89 */     encodeThis();
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
/*     */   public IssuerAlternativeNameExtension(Boolean paramBoolean, GeneralNames paramGeneralNames) throws IOException {
/* 102 */     this.names = paramGeneralNames;
/* 103 */     this.extensionId = PKIXExtensions.IssuerAlternativeName_Id;
/* 104 */     this.critical = paramBoolean.booleanValue();
/* 105 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IssuerAlternativeNameExtension() {
/* 112 */     this.extensionId = PKIXExtensions.IssuerAlternativeName_Id;
/* 113 */     this.critical = false;
/* 114 */     this.names = new GeneralNames();
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
/*     */   public IssuerAlternativeNameExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 127 */     this.extensionId = PKIXExtensions.IssuerAlternativeName_Id;
/* 128 */     this.critical = paramBoolean.booleanValue();
/* 129 */     this.extensionValue = (byte[])paramObject;
/* 130 */     DerValue derValue = new DerValue(this.extensionValue);
/* 131 */     if (derValue.data == null) {
/* 132 */       this.names = new GeneralNames();
/*     */       
/*     */       return;
/*     */     } 
/* 136 */     this.names = new GeneralNames(derValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 144 */     String str = super.toString() + "IssuerAlternativeName [\n";
/* 145 */     if (this.names == null) {
/* 146 */       str = str + "  null\n";
/*     */     } else {
/* 148 */       for (GeneralName generalName : this.names.names()) {
/* 149 */         str = str + "  " + generalName + "\n";
/*     */       }
/*     */     } 
/* 152 */     str = str + "]\n";
/* 153 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 163 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 164 */     if (this.extensionValue == null) {
/* 165 */       this.extensionId = PKIXExtensions.IssuerAlternativeName_Id;
/* 166 */       this.critical = false;
/* 167 */       encodeThis();
/*     */     } 
/* 169 */     encode(derOutputStream);
/* 170 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 177 */     if (paramString.equalsIgnoreCase("issuer_name")) {
/* 178 */       if (!(paramObject instanceof GeneralNames)) {
/* 179 */         throw new IOException("Attribute value should be of type GeneralNames.");
/*     */       }
/*     */       
/* 182 */       this.names = (GeneralNames)paramObject;
/*     */     } else {
/* 184 */       throw new IOException("Attribute name not recognized by CertAttrSet:IssuerAlternativeName.");
/*     */     } 
/*     */     
/* 187 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralNames get(String paramString) throws IOException {
/* 194 */     if (paramString.equalsIgnoreCase("issuer_name")) {
/* 195 */       return this.names;
/*     */     }
/* 197 */     throw new IOException("Attribute name not recognized by CertAttrSet:IssuerAlternativeName.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 206 */     if (paramString.equalsIgnoreCase("issuer_name")) {
/* 207 */       this.names = null;
/*     */     } else {
/* 209 */       throw new IOException("Attribute name not recognized by CertAttrSet:IssuerAlternativeName.");
/*     */     } 
/*     */     
/* 212 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 220 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 221 */     attributeNameEnumeration.addElement("issuer_name");
/*     */     
/* 223 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 230 */     return "IssuerAlternativeName";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/IssuerAlternativeNameExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */