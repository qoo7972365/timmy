/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Enumeration;
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
/*     */ public class CertificateX509Key
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.key";
/*     */   public static final String NAME = "key";
/*     */   public static final String KEY = "value";
/*     */   private PublicKey key;
/*     */   
/*     */   public CertificateX509Key(PublicKey paramPublicKey) {
/*  64 */     this.key = paramPublicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertificateX509Key(DerInputStream paramDerInputStream) throws IOException {
/*  74 */     DerValue derValue = paramDerInputStream.getDerValue();
/*  75 */     this.key = X509Key.parse(derValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertificateX509Key(InputStream paramInputStream) throws IOException {
/*  85 */     DerValue derValue = new DerValue(paramInputStream);
/*  86 */     this.key = X509Key.parse(derValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  93 */     if (this.key == null) return ""; 
/*  94 */     return this.key.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 104 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 105 */     derOutputStream.write(this.key.getEncoded());
/*     */     
/* 107 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 114 */     if (paramString.equalsIgnoreCase("value")) {
/* 115 */       this.key = (PublicKey)paramObject;
/*     */     } else {
/* 117 */       throw new IOException("Attribute name not recognized by CertAttrSet: CertificateX509Key.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey get(String paramString) throws IOException {
/* 126 */     if (paramString.equalsIgnoreCase("value")) {
/* 127 */       return this.key;
/*     */     }
/* 129 */     throw new IOException("Attribute name not recognized by CertAttrSet: CertificateX509Key.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 138 */     if (paramString.equalsIgnoreCase("value")) {
/* 139 */       this.key = null;
/*     */     } else {
/* 141 */       throw new IOException("Attribute name not recognized by CertAttrSet: CertificateX509Key.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 151 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 152 */     attributeNameEnumeration.addElement("value");
/*     */     
/* 154 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 161 */     return "key";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/CertificateX509Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */