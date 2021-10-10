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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SubjectKeyIdentifierExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.SubjectKeyIdentifier";
/*     */   public static final String NAME = "SubjectKeyIdentifier";
/*     */   public static final String KEY_ID = "key_id";
/*  70 */   private KeyIdentifier id = null;
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/*  74 */     if (this.id == null) {
/*  75 */       this.extensionValue = null;
/*     */       return;
/*     */     } 
/*  78 */     DerOutputStream derOutputStream = new DerOutputStream();
/*  79 */     this.id.encode(derOutputStream);
/*  80 */     this.extensionValue = derOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectKeyIdentifierExtension(byte[] paramArrayOfbyte) throws IOException {
/*  90 */     this.id = new KeyIdentifier(paramArrayOfbyte);
/*     */     
/*  92 */     this.extensionId = PKIXExtensions.SubjectKey_Id;
/*  93 */     this.critical = false;
/*  94 */     encodeThis();
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
/*     */   public SubjectKeyIdentifierExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 107 */     this.extensionId = PKIXExtensions.SubjectKey_Id;
/* 108 */     this.critical = paramBoolean.booleanValue();
/* 109 */     this.extensionValue = (byte[])paramObject;
/* 110 */     DerValue derValue = new DerValue(this.extensionValue);
/* 111 */     this.id = new KeyIdentifier(derValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 118 */     return super.toString() + "SubjectKeyIdentifier [\n" + 
/* 119 */       String.valueOf(this.id) + "]\n";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 129 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 130 */     if (this.extensionValue == null) {
/* 131 */       this.extensionId = PKIXExtensions.SubjectKey_Id;
/* 132 */       this.critical = false;
/* 133 */       encodeThis();
/*     */     } 
/* 135 */     encode(derOutputStream);
/* 136 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 143 */     if (paramString.equalsIgnoreCase("key_id")) {
/* 144 */       if (!(paramObject instanceof KeyIdentifier)) {
/* 145 */         throw new IOException("Attribute value should be of type KeyIdentifier.");
/*     */       }
/*     */       
/* 148 */       this.id = (KeyIdentifier)paramObject;
/*     */     } else {
/* 150 */       throw new IOException("Attribute name not recognized by CertAttrSet:SubjectKeyIdentifierExtension.");
/*     */     } 
/*     */     
/* 153 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyIdentifier get(String paramString) throws IOException {
/* 160 */     if (paramString.equalsIgnoreCase("key_id")) {
/* 161 */       return this.id;
/*     */     }
/* 163 */     throw new IOException("Attribute name not recognized by CertAttrSet:SubjectKeyIdentifierExtension.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 172 */     if (paramString.equalsIgnoreCase("key_id")) {
/* 173 */       this.id = null;
/*     */     } else {
/* 175 */       throw new IOException("Attribute name not recognized by CertAttrSet:SubjectKeyIdentifierExtension.");
/*     */     } 
/*     */     
/* 178 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 186 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 187 */     attributeNameEnumeration.addElement("key_id");
/*     */     
/* 189 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 196 */     return "SubjectKeyIdentifier";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/SubjectKeyIdentifierExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */