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
/*     */ public class PolicyMappingsExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.PolicyMappings";
/*     */   public static final String NAME = "PolicyMappings";
/*     */   public static final String MAP = "map";
/*     */   private List<CertificatePolicyMap> maps;
/*     */   
/*     */   private void encodeThis() throws IOException {
/*  71 */     if (this.maps == null || this.maps.isEmpty()) {
/*  72 */       this.extensionValue = null;
/*     */       return;
/*     */     } 
/*  75 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*  76 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/*  78 */     for (CertificatePolicyMap certificatePolicyMap : this.maps) {
/*  79 */       certificatePolicyMap.encode(derOutputStream2);
/*     */     }
/*     */     
/*  82 */     derOutputStream1.write((byte)48, derOutputStream2);
/*  83 */     this.extensionValue = derOutputStream1.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyMappingsExtension(List<CertificatePolicyMap> paramList) throws IOException {
/*  93 */     this.maps = paramList;
/*  94 */     this.extensionId = PKIXExtensions.PolicyMappings_Id;
/*  95 */     this.critical = false;
/*  96 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyMappingsExtension() {
/* 103 */     this.extensionId = PKIXExtensions.KeyUsage_Id;
/* 104 */     this.critical = false;
/* 105 */     this.maps = Collections.emptyList();
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
/*     */   public PolicyMappingsExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 118 */     this.extensionId = PKIXExtensions.PolicyMappings_Id;
/* 119 */     this.critical = paramBoolean.booleanValue();
/*     */     
/* 121 */     this.extensionValue = (byte[])paramObject;
/* 122 */     DerValue derValue = new DerValue(this.extensionValue);
/* 123 */     if (derValue.tag != 48) {
/* 124 */       throw new IOException("Invalid encoding for PolicyMappingsExtension.");
/*     */     }
/*     */     
/* 127 */     this.maps = new ArrayList<>();
/* 128 */     while (derValue.data.available() != 0) {
/* 129 */       DerValue derValue1 = derValue.data.getDerValue();
/* 130 */       CertificatePolicyMap certificatePolicyMap = new CertificatePolicyMap(derValue1);
/* 131 */       this.maps.add(certificatePolicyMap);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 139 */     if (this.maps == null) return ""; 
/* 140 */     return super.toString() + "PolicyMappings [\n" + this.maps
/* 141 */       .toString() + "]\n";
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
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 153 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 154 */     if (this.extensionValue == null) {
/* 155 */       this.extensionId = PKIXExtensions.PolicyMappings_Id;
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
/* 168 */     if (paramString.equalsIgnoreCase("map")) {
/* 169 */       if (!(paramObject instanceof List)) {
/* 170 */         throw new IOException("Attribute value should be of type List.");
/*     */       }
/*     */       
/* 173 */       this.maps = (List<CertificatePolicyMap>)paramObject;
/*     */     } else {
/* 175 */       throw new IOException("Attribute name not recognized by CertAttrSet:PolicyMappingsExtension.");
/*     */     } 
/*     */     
/* 178 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CertificatePolicyMap> get(String paramString) throws IOException {
/* 185 */     if (paramString.equalsIgnoreCase("map")) {
/* 186 */       return this.maps;
/*     */     }
/* 188 */     throw new IOException("Attribute name not recognized by CertAttrSet:PolicyMappingsExtension.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 197 */     if (paramString.equalsIgnoreCase("map")) {
/* 198 */       this.maps = null;
/*     */     } else {
/* 200 */       throw new IOException("Attribute name not recognized by CertAttrSet:PolicyMappingsExtension.");
/*     */     } 
/*     */     
/* 203 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 211 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 212 */     attributeNameEnumeration.addElement("map");
/*     */     
/* 214 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 221 */     return "PolicyMappings";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/PolicyMappingsExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */