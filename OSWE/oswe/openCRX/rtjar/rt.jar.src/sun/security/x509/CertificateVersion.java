/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificateVersion
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final int V1 = 0;
/*     */   public static final int V2 = 1;
/*     */   public static final int V3 = 2;
/*     */   public static final String IDENT = "x509.info.version";
/*     */   public static final String NAME = "version";
/*     */   public static final String VERSION = "number";
/*  67 */   int version = 0;
/*     */ 
/*     */   
/*     */   private int getVersion() {
/*  71 */     return this.version;
/*     */   }
/*     */ 
/*     */   
/*     */   private void construct(DerValue paramDerValue) throws IOException {
/*  76 */     if (paramDerValue.isConstructed() && paramDerValue.isContextSpecific()) {
/*  77 */       paramDerValue = paramDerValue.data.getDerValue();
/*  78 */       this.version = paramDerValue.getInteger();
/*  79 */       if (paramDerValue.data.available() != 0) {
/*  80 */         throw new IOException("X.509 version, bad format");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertificateVersion() {
/*  90 */     this.version = 0;
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
/*     */   public CertificateVersion(int paramInt) throws IOException {
/* 102 */     if (paramInt == 0 || paramInt == 1 || paramInt == 2) {
/* 103 */       this.version = paramInt;
/*     */     } else {
/* 105 */       throw new IOException("X.509 Certificate version " + paramInt + " not supported.\n");
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
/*     */   public CertificateVersion(DerInputStream paramDerInputStream) throws IOException {
/* 117 */     this.version = 0;
/* 118 */     DerValue derValue = paramDerInputStream.getDerValue();
/*     */     
/* 120 */     construct(derValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertificateVersion(InputStream paramInputStream) throws IOException {
/* 130 */     this.version = 0;
/* 131 */     DerValue derValue = new DerValue(paramInputStream);
/*     */     
/* 133 */     construct(derValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertificateVersion(DerValue paramDerValue) throws IOException {
/* 143 */     this.version = 0;
/*     */     
/* 145 */     construct(paramDerValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 152 */     return "Version: V" + (this.version + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 163 */     if (this.version == 0) {
/*     */       return;
/*     */     }
/* 166 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 167 */     derOutputStream1.putInteger(this.version);
/*     */     
/* 169 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 170 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)0), derOutputStream1);
/*     */ 
/*     */     
/* 173 */     paramOutputStream.write(derOutputStream2.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 180 */     if (!(paramObject instanceof Integer)) {
/* 181 */       throw new IOException("Attribute must be of type Integer.");
/*     */     }
/* 183 */     if (paramString.equalsIgnoreCase("number")) {
/* 184 */       this.version = ((Integer)paramObject).intValue();
/*     */     } else {
/* 186 */       throw new IOException("Attribute name not recognized by CertAttrSet: CertificateVersion.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer get(String paramString) throws IOException {
/* 195 */     if (paramString.equalsIgnoreCase("number")) {
/* 196 */       return new Integer(getVersion());
/*     */     }
/* 198 */     throw new IOException("Attribute name not recognized by CertAttrSet: CertificateVersion.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 207 */     if (paramString.equalsIgnoreCase("number")) {
/* 208 */       this.version = 0;
/*     */     } else {
/* 210 */       throw new IOException("Attribute name not recognized by CertAttrSet: CertificateVersion.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 220 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 221 */     attributeNameEnumeration.addElement("number");
/*     */     
/* 223 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 230 */     return "version";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compare(int paramInt) {
/* 237 */     return this.version - paramInt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/CertificateVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */