/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SubjectInfoAccessExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.SubjectInfoAccess";
/*     */   public static final String NAME = "SubjectInfoAccess";
/*     */   public static final String DESCRIPTIONS = "descriptions";
/*     */   private List<AccessDescription> accessDescriptions;
/*     */   
/*     */   public SubjectInfoAccessExtension(List<AccessDescription> paramList) throws IOException {
/* 101 */     this.extensionId = PKIXExtensions.SubjectInfoAccess_Id;
/* 102 */     this.critical = false;
/* 103 */     this.accessDescriptions = paramList;
/* 104 */     encodeThis();
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
/*     */   public SubjectInfoAccessExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 116 */     this.extensionId = PKIXExtensions.SubjectInfoAccess_Id;
/* 117 */     this.critical = paramBoolean.booleanValue();
/*     */     
/* 119 */     if (!(paramObject instanceof byte[])) {
/* 120 */       throw new IOException("Illegal argument type");
/*     */     }
/*     */     
/* 123 */     this.extensionValue = (byte[])paramObject;
/* 124 */     DerValue derValue = new DerValue(this.extensionValue);
/* 125 */     if (derValue.tag != 48) {
/* 126 */       throw new IOException("Invalid encoding for SubjectInfoAccessExtension.");
/*     */     }
/*     */     
/* 129 */     this.accessDescriptions = new ArrayList<>();
/* 130 */     while (derValue.data.available() != 0) {
/* 131 */       DerValue derValue1 = derValue.data.getDerValue();
/* 132 */       AccessDescription accessDescription = new AccessDescription(derValue1);
/* 133 */       this.accessDescriptions.add(accessDescription);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AccessDescription> getAccessDescriptions() {
/* 141 */     return this.accessDescriptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 148 */     return "SubjectInfoAccess";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 158 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 159 */     if (this.extensionValue == null) {
/* 160 */       this.extensionId = PKIXExtensions.SubjectInfoAccess_Id;
/* 161 */       this.critical = false;
/* 162 */       encodeThis();
/*     */     } 
/* 164 */     encode(derOutputStream);
/* 165 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 173 */     if (paramString.equalsIgnoreCase("descriptions")) {
/* 174 */       if (!(paramObject instanceof List)) {
/* 175 */         throw new IOException("Attribute value should be of type List.");
/*     */       }
/* 177 */       this.accessDescriptions = (List<AccessDescription>)paramObject;
/*     */     } else {
/* 179 */       throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:SubjectInfoAccessExtension.");
/*     */     } 
/*     */ 
/*     */     
/* 183 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AccessDescription> get(String paramString) throws IOException {
/* 190 */     if (paramString.equalsIgnoreCase("descriptions")) {
/* 191 */       return this.accessDescriptions;
/*     */     }
/* 193 */     throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:SubjectInfoAccessExtension.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 203 */     if (paramString.equalsIgnoreCase("descriptions")) {
/* 204 */       this
/* 205 */         .accessDescriptions = Collections.emptyList();
/*     */     } else {
/* 207 */       throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:SubjectInfoAccessExtension.");
/*     */     } 
/*     */ 
/*     */     
/* 211 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 219 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 220 */     attributeNameEnumeration.addElement("descriptions");
/* 221 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/* 226 */     if (this.accessDescriptions.isEmpty()) {
/* 227 */       this.extensionValue = null;
/*     */     } else {
/* 229 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 230 */       for (AccessDescription accessDescription : this.accessDescriptions) {
/* 231 */         accessDescription.encode(derOutputStream1);
/*     */       }
/* 233 */       DerOutputStream derOutputStream2 = new DerOutputStream();
/* 234 */       derOutputStream2.write((byte)48, derOutputStream1);
/* 235 */       this.extensionValue = derOutputStream2.toByteArray();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 243 */     return super.toString() + "SubjectInfoAccess [\n  " + this.accessDescriptions + "\n]\n";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/SubjectInfoAccessExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */