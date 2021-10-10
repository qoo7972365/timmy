/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IssuingDistributionPointExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.IssuingDistributionPoint";
/*     */   public static final String NAME = "IssuingDistributionPoint";
/*     */   public static final String POINT = "point";
/*     */   public static final String REASONS = "reasons";
/*     */   public static final String ONLY_USER_CERTS = "only_user_certs";
/*     */   public static final String ONLY_CA_CERTS = "only_ca_certs";
/*     */   public static final String ONLY_ATTRIBUTE_CERTS = "only_attribute_certs";
/*     */   public static final String INDIRECT_CRL = "indirect_crl";
/*  93 */   private DistributionPointName distributionPoint = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   private ReasonFlags revocationReasons = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasOnlyUserCerts = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasOnlyCACerts = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasOnlyAttributeCerts = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isIndirectCRL = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte TAG_DISTRIBUTION_POINT = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte TAG_ONLY_USER_CERTS = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte TAG_ONLY_CA_CERTS = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte TAG_ONLY_SOME_REASONS = 3;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte TAG_INDIRECT_CRL = 4;
/*     */ 
/*     */   
/*     */   private static final byte TAG_ONLY_ATTRIBUTE_CERTS = 5;
/*     */ 
/*     */ 
/*     */   
/*     */   public IssuingDistributionPointExtension(DistributionPointName paramDistributionPointName, ReasonFlags paramReasonFlags, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) throws IOException {
/* 142 */     if ((paramBoolean1 && (paramBoolean2 || paramBoolean3)) || (paramBoolean2 && (paramBoolean1 || paramBoolean3)) || (paramBoolean3 && (paramBoolean1 || paramBoolean2)))
/*     */     {
/*     */       
/* 145 */       throw new IllegalArgumentException("Only one of hasOnlyUserCerts, hasOnlyCACerts, hasOnlyAttributeCerts may be set to true");
/*     */     }
/*     */ 
/*     */     
/* 149 */     this.extensionId = PKIXExtensions.IssuingDistributionPoint_Id;
/* 150 */     this.critical = true;
/* 151 */     this.distributionPoint = paramDistributionPointName;
/* 152 */     this.revocationReasons = paramReasonFlags;
/* 153 */     this.hasOnlyUserCerts = paramBoolean1;
/* 154 */     this.hasOnlyCACerts = paramBoolean2;
/* 155 */     this.hasOnlyAttributeCerts = paramBoolean3;
/* 156 */     this.isIndirectCRL = paramBoolean4;
/* 157 */     encodeThis();
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
/*     */   public IssuingDistributionPointExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 170 */     this.extensionId = PKIXExtensions.IssuingDistributionPoint_Id;
/* 171 */     this.critical = paramBoolean.booleanValue();
/*     */     
/* 173 */     if (!(paramObject instanceof byte[])) {
/* 174 */       throw new IOException("Illegal argument type");
/*     */     }
/*     */     
/* 177 */     this.extensionValue = (byte[])paramObject;
/* 178 */     DerValue derValue = new DerValue(this.extensionValue);
/* 179 */     if (derValue.tag != 48) {
/* 180 */       throw new IOException("Invalid encoding for IssuingDistributionPointExtension.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 185 */     if (derValue.data == null || derValue.data.available() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 189 */     DerInputStream derInputStream = derValue.data;
/* 190 */     while (derInputStream != null && derInputStream.available() != 0) {
/* 191 */       DerValue derValue1 = derInputStream.getDerValue();
/*     */       
/* 193 */       if (derValue1.isContextSpecific((byte)0) && derValue1
/* 194 */         .isConstructed()) {
/* 195 */         this
/* 196 */           .distributionPoint = new DistributionPointName(derValue1.data.getDerValue()); continue;
/* 197 */       }  if (derValue1.isContextSpecific((byte)1) && 
/* 198 */         !derValue1.isConstructed()) {
/* 199 */         derValue1.resetTag((byte)1);
/* 200 */         this.hasOnlyUserCerts = derValue1.getBoolean(); continue;
/* 201 */       }  if (derValue1.isContextSpecific((byte)2) && 
/* 202 */         !derValue1.isConstructed()) {
/* 203 */         derValue1.resetTag((byte)1);
/* 204 */         this.hasOnlyCACerts = derValue1.getBoolean(); continue;
/* 205 */       }  if (derValue1.isContextSpecific((byte)3) && 
/* 206 */         !derValue1.isConstructed()) {
/* 207 */         this.revocationReasons = new ReasonFlags(derValue1); continue;
/* 208 */       }  if (derValue1.isContextSpecific((byte)4) && 
/* 209 */         !derValue1.isConstructed()) {
/* 210 */         derValue1.resetTag((byte)1);
/* 211 */         this.isIndirectCRL = derValue1.getBoolean(); continue;
/* 212 */       }  if (derValue1.isContextSpecific((byte)5) && 
/* 213 */         !derValue1.isConstructed()) {
/* 214 */         derValue1.resetTag((byte)1);
/* 215 */         this.hasOnlyAttributeCerts = derValue1.getBoolean(); continue;
/*     */       } 
/* 217 */       throw new IOException("Invalid encoding of IssuingDistributionPoint");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 227 */     return "IssuingDistributionPoint";
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
/* 238 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 239 */     if (this.extensionValue == null) {
/* 240 */       this.extensionId = PKIXExtensions.IssuingDistributionPoint_Id;
/* 241 */       this.critical = false;
/* 242 */       encodeThis();
/*     */     } 
/* 244 */     encode(derOutputStream);
/* 245 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 252 */     if (paramString.equalsIgnoreCase("point")) {
/* 253 */       if (!(paramObject instanceof DistributionPointName)) {
/* 254 */         throw new IOException("Attribute value should be of type DistributionPointName.");
/*     */       }
/*     */       
/* 257 */       this.distributionPoint = (DistributionPointName)paramObject;
/*     */     }
/* 259 */     else if (paramString.equalsIgnoreCase("reasons")) {
/* 260 */       if (!(paramObject instanceof ReasonFlags)) {
/* 261 */         throw new IOException("Attribute value should be of type ReasonFlags.");
/*     */       }
/*     */       
/* 264 */       this.revocationReasons = (ReasonFlags)paramObject;
/*     */     }
/* 266 */     else if (paramString.equalsIgnoreCase("indirect_crl")) {
/* 267 */       if (!(paramObject instanceof Boolean)) {
/* 268 */         throw new IOException("Attribute value should be of type Boolean.");
/*     */       }
/*     */       
/* 271 */       this.isIndirectCRL = ((Boolean)paramObject).booleanValue();
/*     */     }
/* 273 */     else if (paramString.equalsIgnoreCase("only_user_certs")) {
/* 274 */       if (!(paramObject instanceof Boolean)) {
/* 275 */         throw new IOException("Attribute value should be of type Boolean.");
/*     */       }
/*     */       
/* 278 */       this.hasOnlyUserCerts = ((Boolean)paramObject).booleanValue();
/*     */     }
/* 280 */     else if (paramString.equalsIgnoreCase("only_ca_certs")) {
/* 281 */       if (!(paramObject instanceof Boolean)) {
/* 282 */         throw new IOException("Attribute value should be of type Boolean.");
/*     */       }
/*     */       
/* 285 */       this.hasOnlyCACerts = ((Boolean)paramObject).booleanValue();
/*     */     }
/* 287 */     else if (paramString.equalsIgnoreCase("only_attribute_certs")) {
/* 288 */       if (!(paramObject instanceof Boolean)) {
/* 289 */         throw new IOException("Attribute value should be of type Boolean.");
/*     */       }
/*     */       
/* 292 */       this.hasOnlyAttributeCerts = ((Boolean)paramObject).booleanValue();
/*     */     } else {
/*     */       
/* 295 */       throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:IssuingDistributionPointExtension.");
/*     */     } 
/*     */ 
/*     */     
/* 299 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(String paramString) throws IOException {
/* 306 */     if (paramString.equalsIgnoreCase("point")) {
/* 307 */       return this.distributionPoint;
/*     */     }
/* 309 */     if (paramString.equalsIgnoreCase("indirect_crl")) {
/* 310 */       return Boolean.valueOf(this.isIndirectCRL);
/*     */     }
/* 312 */     if (paramString.equalsIgnoreCase("reasons")) {
/* 313 */       return this.revocationReasons;
/*     */     }
/* 315 */     if (paramString.equalsIgnoreCase("only_user_certs")) {
/* 316 */       return Boolean.valueOf(this.hasOnlyUserCerts);
/*     */     }
/* 318 */     if (paramString.equalsIgnoreCase("only_ca_certs")) {
/* 319 */       return Boolean.valueOf(this.hasOnlyCACerts);
/*     */     }
/* 321 */     if (paramString.equalsIgnoreCase("only_attribute_certs")) {
/* 322 */       return Boolean.valueOf(this.hasOnlyAttributeCerts);
/*     */     }
/*     */     
/* 325 */     throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:IssuingDistributionPointExtension.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 335 */     if (paramString.equalsIgnoreCase("point")) {
/* 336 */       this.distributionPoint = null;
/*     */     }
/* 338 */     else if (paramString.equalsIgnoreCase("indirect_crl")) {
/* 339 */       this.isIndirectCRL = false;
/*     */     }
/* 341 */     else if (paramString.equalsIgnoreCase("reasons")) {
/* 342 */       this.revocationReasons = null;
/*     */     }
/* 344 */     else if (paramString.equalsIgnoreCase("only_user_certs")) {
/* 345 */       this.hasOnlyUserCerts = false;
/*     */     }
/* 347 */     else if (paramString.equalsIgnoreCase("only_ca_certs")) {
/* 348 */       this.hasOnlyCACerts = false;
/*     */     }
/* 350 */     else if (paramString.equalsIgnoreCase("only_attribute_certs")) {
/* 351 */       this.hasOnlyAttributeCerts = false;
/*     */     } else {
/*     */       
/* 354 */       throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:IssuingDistributionPointExtension.");
/*     */     } 
/*     */ 
/*     */     
/* 358 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 366 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 367 */     attributeNameEnumeration.addElement("point");
/* 368 */     attributeNameEnumeration.addElement("reasons");
/* 369 */     attributeNameEnumeration.addElement("only_user_certs");
/* 370 */     attributeNameEnumeration.addElement("only_ca_certs");
/* 371 */     attributeNameEnumeration.addElement("only_attribute_certs");
/* 372 */     attributeNameEnumeration.addElement("indirect_crl");
/* 373 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/* 379 */     if (this.distributionPoint == null && this.revocationReasons == null && !this.hasOnlyUserCerts && !this.hasOnlyCACerts && !this.hasOnlyAttributeCerts && !this.isIndirectCRL) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 386 */       this.extensionValue = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 391 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*     */     
/* 393 */     if (this.distributionPoint != null) {
/* 394 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 395 */       this.distributionPoint.encode(derOutputStream);
/* 396 */       derOutputStream1.writeImplicit(DerValue.createTag(-128, true, (byte)0), derOutputStream);
/*     */     } 
/*     */ 
/*     */     
/* 400 */     if (this.hasOnlyUserCerts) {
/* 401 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 402 */       derOutputStream.putBoolean(this.hasOnlyUserCerts);
/* 403 */       derOutputStream1.writeImplicit(DerValue.createTag(-128, false, (byte)1), derOutputStream);
/*     */     } 
/*     */ 
/*     */     
/* 407 */     if (this.hasOnlyCACerts) {
/* 408 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 409 */       derOutputStream.putBoolean(this.hasOnlyCACerts);
/* 410 */       derOutputStream1.writeImplicit(DerValue.createTag(-128, false, (byte)2), derOutputStream);
/*     */     } 
/*     */ 
/*     */     
/* 414 */     if (this.revocationReasons != null) {
/* 415 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 416 */       this.revocationReasons.encode(derOutputStream);
/* 417 */       derOutputStream1.writeImplicit(DerValue.createTag(-128, false, (byte)3), derOutputStream);
/*     */     } 
/*     */ 
/*     */     
/* 421 */     if (this.isIndirectCRL) {
/* 422 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 423 */       derOutputStream.putBoolean(this.isIndirectCRL);
/* 424 */       derOutputStream1.writeImplicit(DerValue.createTag(-128, false, (byte)4), derOutputStream);
/*     */     } 
/*     */ 
/*     */     
/* 428 */     if (this.hasOnlyAttributeCerts) {
/* 429 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 430 */       derOutputStream.putBoolean(this.hasOnlyAttributeCerts);
/* 431 */       derOutputStream1.writeImplicit(DerValue.createTag(-128, false, (byte)5), derOutputStream);
/*     */     } 
/*     */ 
/*     */     
/* 435 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 436 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 437 */     this.extensionValue = derOutputStream2.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 445 */     StringBuilder stringBuilder = new StringBuilder(super.toString());
/* 446 */     stringBuilder.append("IssuingDistributionPoint [\n  ");
/*     */     
/* 448 */     if (this.distributionPoint != null) {
/* 449 */       stringBuilder.append(this.distributionPoint);
/*     */     }
/*     */     
/* 452 */     if (this.revocationReasons != null) {
/* 453 */       stringBuilder.append(this.revocationReasons);
/*     */     }
/*     */     
/* 456 */     stringBuilder.append(this.hasOnlyUserCerts ? "  Only contains user certs: true" : "  Only contains user certs: false")
/*     */       
/* 458 */       .append("\n");
/*     */     
/* 460 */     stringBuilder.append(this.hasOnlyCACerts ? "  Only contains CA certs: true" : "  Only contains CA certs: false")
/*     */       
/* 462 */       .append("\n");
/*     */     
/* 464 */     stringBuilder.append(this.hasOnlyAttributeCerts ? "  Only contains attribute certs: true" : "  Only contains attribute certs: false")
/*     */       
/* 466 */       .append("\n");
/*     */     
/* 468 */     stringBuilder.append(this.isIndirectCRL ? "  Indirect CRL: true" : "  Indirect CRL: false")
/*     */       
/* 470 */       .append("\n");
/*     */     
/* 472 */     stringBuilder.append("]\n");
/*     */     
/* 474 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/IssuingDistributionPointExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */