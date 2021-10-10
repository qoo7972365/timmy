/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthorityInfoAccessExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.AuthorityInfoAccess";
/*     */   public static final String NAME = "AuthorityInfoAccess";
/*     */   public static final String DESCRIPTIONS = "descriptions";
/*     */   private List<AccessDescription> accessDescriptions;
/*     */   
/*     */   public AuthorityInfoAccessExtension(List<AccessDescription> paramList) throws IOException {
/*  96 */     this.extensionId = PKIXExtensions.AuthInfoAccess_Id;
/*  97 */     this.critical = false;
/*  98 */     this.accessDescriptions = paramList;
/*  99 */     encodeThis();
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
/*     */   public AuthorityInfoAccessExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 111 */     this.extensionId = PKIXExtensions.AuthInfoAccess_Id;
/* 112 */     this.critical = paramBoolean.booleanValue();
/*     */     
/* 114 */     if (!(paramObject instanceof byte[])) {
/* 115 */       throw new IOException("Illegal argument type");
/*     */     }
/*     */     
/* 118 */     this.extensionValue = (byte[])paramObject;
/* 119 */     DerValue derValue = new DerValue(this.extensionValue);
/* 120 */     if (derValue.tag != 48) {
/* 121 */       throw new IOException("Invalid encoding for AuthorityInfoAccessExtension.");
/*     */     }
/*     */     
/* 124 */     this.accessDescriptions = new ArrayList<>();
/* 125 */     while (derValue.data.available() != 0) {
/* 126 */       DerValue derValue1 = derValue.data.getDerValue();
/* 127 */       AccessDescription accessDescription = new AccessDescription(derValue1);
/* 128 */       this.accessDescriptions.add(accessDescription);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AccessDescription> getAccessDescriptions() {
/* 136 */     return this.accessDescriptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 143 */     return "AuthorityInfoAccess";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 153 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 154 */     if (this.extensionValue == null) {
/* 155 */       this.extensionId = PKIXExtensions.AuthInfoAccess_Id;
/* 156 */       this.critical = false;
/* 157 */       encodeThis();
/*     */     } 
/* 159 */     encode(derOutputStream);
/* 160 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 168 */     if (paramString.equalsIgnoreCase("descriptions")) {
/* 169 */       if (!(paramObject instanceof List)) {
/* 170 */         throw new IOException("Attribute value should be of type List.");
/*     */       }
/* 172 */       this.accessDescriptions = (List<AccessDescription>)paramObject;
/*     */     } else {
/* 174 */       throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:AuthorityInfoAccessExtension.");
/*     */     } 
/*     */ 
/*     */     
/* 178 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AccessDescription> get(String paramString) throws IOException {
/* 185 */     if (paramString.equalsIgnoreCase("descriptions")) {
/* 186 */       return this.accessDescriptions;
/*     */     }
/* 188 */     throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:AuthorityInfoAccessExtension.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 198 */     if (paramString.equalsIgnoreCase("descriptions")) {
/* 199 */       this.accessDescriptions = new ArrayList<>();
/*     */     } else {
/* 201 */       throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:AuthorityInfoAccessExtension.");
/*     */     } 
/*     */ 
/*     */     
/* 205 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 213 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 214 */     attributeNameEnumeration.addElement("descriptions");
/* 215 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/* 220 */     if (this.accessDescriptions.isEmpty()) {
/* 221 */       this.extensionValue = null;
/*     */     } else {
/* 223 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 224 */       for (AccessDescription accessDescription : this.accessDescriptions) {
/* 225 */         accessDescription.encode(derOutputStream1);
/*     */       }
/* 227 */       DerOutputStream derOutputStream2 = new DerOutputStream();
/* 228 */       derOutputStream2.write((byte)48, derOutputStream1);
/* 229 */       this.extensionValue = derOutputStream2.toByteArray();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 237 */     return super.toString() + "AuthorityInfoAccess [\n  " + this.accessDescriptions + "\n]\n";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/AuthorityInfoAccessExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */