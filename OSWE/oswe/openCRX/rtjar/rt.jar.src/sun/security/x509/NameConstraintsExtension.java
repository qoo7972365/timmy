/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Enumeration;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.net.util.IPAddressUtil;
/*     */ import sun.security.pkcs.PKCS9Attribute;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NameConstraintsExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>, Cloneable
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.NameConstraints";
/*     */   public static final String NAME = "NameConstraints";
/*     */   public static final String PERMITTED_SUBTREES = "permitted_subtrees";
/*     */   public static final String EXCLUDED_SUBTREES = "excluded_subtrees";
/*     */   private static final byte TAG_PERMITTED = 0;
/*     */   private static final byte TAG_EXCLUDED = 1;
/*  82 */   private GeneralSubtrees permitted = null;
/*  83 */   private GeneralSubtrees excluded = null;
/*     */   
/*     */   private boolean hasMin;
/*     */   
/*     */   private boolean hasMax;
/*     */   private boolean minMaxValid = false;
/*     */   
/*     */   private void calcMinMax() throws IOException {
/*  91 */     this.hasMin = false;
/*  92 */     this.hasMax = false;
/*  93 */     if (this.excluded != null) {
/*  94 */       for (byte b = 0; b < this.excluded.size(); b++) {
/*  95 */         GeneralSubtree generalSubtree = this.excluded.get(b);
/*  96 */         if (generalSubtree.getMinimum() != 0)
/*  97 */           this.hasMin = true; 
/*  98 */         if (generalSubtree.getMaximum() != -1) {
/*  99 */           this.hasMax = true;
/*     */         }
/*     */       } 
/*     */     }
/* 103 */     if (this.permitted != null)
/* 104 */       for (byte b = 0; b < this.permitted.size(); b++) {
/* 105 */         GeneralSubtree generalSubtree = this.permitted.get(b);
/* 106 */         if (generalSubtree.getMinimum() != 0)
/* 107 */           this.hasMin = true; 
/* 108 */         if (generalSubtree.getMaximum() != -1) {
/* 109 */           this.hasMax = true;
/*     */         }
/*     */       }  
/* 112 */     this.minMaxValid = true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/* 117 */     this.minMaxValid = false;
/* 118 */     if (this.permitted == null && this.excluded == null) {
/* 119 */       this.extensionValue = null;
/*     */       return;
/*     */     } 
/* 122 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*     */     
/* 124 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 125 */     if (this.permitted != null) {
/* 126 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 127 */       this.permitted.encode(derOutputStream);
/* 128 */       derOutputStream2.writeImplicit(DerValue.createTag(-128, true, (byte)0), derOutputStream);
/*     */     } 
/*     */     
/* 131 */     if (this.excluded != null) {
/* 132 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 133 */       this.excluded.encode(derOutputStream);
/* 134 */       derOutputStream2.writeImplicit(DerValue.createTag(-128, true, (byte)1), derOutputStream);
/*     */     } 
/*     */     
/* 137 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 138 */     this.extensionValue = derOutputStream1.toByteArray();
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
/*     */   public NameConstraintsExtension(GeneralSubtrees paramGeneralSubtrees1, GeneralSubtrees paramGeneralSubtrees2) throws IOException {
/* 152 */     this.permitted = paramGeneralSubtrees1;
/* 153 */     this.excluded = paramGeneralSubtrees2;
/*     */     
/* 155 */     this.extensionId = PKIXExtensions.NameConstraints_Id;
/* 156 */     this.critical = true;
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
/*     */   public NameConstraintsExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 170 */     this.extensionId = PKIXExtensions.NameConstraints_Id;
/* 171 */     this.critical = paramBoolean.booleanValue();
/*     */     
/* 173 */     this.extensionValue = (byte[])paramObject;
/* 174 */     DerValue derValue = new DerValue(this.extensionValue);
/* 175 */     if (derValue.tag != 48) {
/* 176 */       throw new IOException("Invalid encoding for NameConstraintsExtension.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     if (derValue.data == null)
/*     */       return; 
/* 188 */     while (derValue.data.available() != 0) {
/* 189 */       DerValue derValue1 = derValue.data.getDerValue();
/*     */       
/* 191 */       if (derValue1.isContextSpecific((byte)0) && derValue1.isConstructed()) {
/* 192 */         if (this.permitted != null) {
/* 193 */           throw new IOException("Duplicate permitted GeneralSubtrees in NameConstraintsExtension.");
/*     */         }
/*     */         
/* 196 */         derValue1.resetTag((byte)48);
/* 197 */         this.permitted = new GeneralSubtrees(derValue1); continue;
/*     */       } 
/* 199 */       if (derValue1.isContextSpecific((byte)1) && derValue1
/* 200 */         .isConstructed()) {
/* 201 */         if (this.excluded != null) {
/* 202 */           throw new IOException("Duplicate excluded GeneralSubtrees in NameConstraintsExtension.");
/*     */         }
/*     */         
/* 205 */         derValue1.resetTag((byte)48);
/* 206 */         this.excluded = new GeneralSubtrees(derValue1); continue;
/*     */       } 
/* 208 */       throw new IOException("Invalid encoding of NameConstraintsExtension.");
/*     */     } 
/*     */     
/* 211 */     this.minMaxValid = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 218 */     return super.toString() + "NameConstraints: [" + ((this.permitted == null) ? "" : ("\n    Permitted:" + this.permitted
/*     */       
/* 220 */       .toString())) + ((this.excluded == null) ? "" : ("\n    Excluded:" + this.excluded
/*     */       
/* 222 */       .toString())) + "   ]\n";
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
/* 233 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 234 */     if (this.extensionValue == null) {
/* 235 */       this.extensionId = PKIXExtensions.NameConstraints_Id;
/* 236 */       this.critical = true;
/* 237 */       encodeThis();
/*     */     } 
/* 239 */     encode(derOutputStream);
/* 240 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 247 */     if (paramString.equalsIgnoreCase("permitted_subtrees")) {
/* 248 */       if (!(paramObject instanceof GeneralSubtrees)) {
/* 249 */         throw new IOException("Attribute value should be of type GeneralSubtrees.");
/*     */       }
/*     */       
/* 252 */       this.permitted = (GeneralSubtrees)paramObject;
/* 253 */     } else if (paramString.equalsIgnoreCase("excluded_subtrees")) {
/* 254 */       if (!(paramObject instanceof GeneralSubtrees)) {
/* 255 */         throw new IOException("Attribute value should be of type GeneralSubtrees.");
/*     */       }
/*     */       
/* 258 */       this.excluded = (GeneralSubtrees)paramObject;
/*     */     } else {
/* 260 */       throw new IOException("Attribute name not recognized by CertAttrSet:NameConstraintsExtension.");
/*     */     } 
/*     */     
/* 263 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralSubtrees get(String paramString) throws IOException {
/* 270 */     if (paramString.equalsIgnoreCase("permitted_subtrees"))
/* 271 */       return this.permitted; 
/* 272 */     if (paramString.equalsIgnoreCase("excluded_subtrees")) {
/* 273 */       return this.excluded;
/*     */     }
/* 275 */     throw new IOException("Attribute name not recognized by CertAttrSet:NameConstraintsExtension.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 284 */     if (paramString.equalsIgnoreCase("permitted_subtrees")) {
/* 285 */       this.permitted = null;
/* 286 */     } else if (paramString.equalsIgnoreCase("excluded_subtrees")) {
/* 287 */       this.excluded = null;
/*     */     } else {
/* 289 */       throw new IOException("Attribute name not recognized by CertAttrSet:NameConstraintsExtension.");
/*     */     } 
/*     */     
/* 292 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 300 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 301 */     attributeNameEnumeration.addElement("permitted_subtrees");
/* 302 */     attributeNameEnumeration.addElement("excluded_subtrees");
/*     */     
/* 304 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 311 */     return "NameConstraints";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void merge(NameConstraintsExtension paramNameConstraintsExtension) throws IOException {
/* 339 */     if (paramNameConstraintsExtension == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 350 */     GeneralSubtrees generalSubtrees1 = paramNameConstraintsExtension.get("excluded_subtrees");
/* 351 */     if (this.excluded == null) {
/* 352 */       this
/* 353 */         .excluded = (generalSubtrees1 != null) ? (GeneralSubtrees)generalSubtrees1.clone() : null;
/*     */     }
/* 355 */     else if (generalSubtrees1 != null) {
/*     */       
/* 357 */       this.excluded.union(generalSubtrees1);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 367 */     GeneralSubtrees generalSubtrees2 = paramNameConstraintsExtension.get("permitted_subtrees");
/* 368 */     if (this.permitted == null) {
/* 369 */       this
/* 370 */         .permitted = (generalSubtrees2 != null) ? (GeneralSubtrees)generalSubtrees2.clone() : null;
/*     */     }
/* 372 */     else if (generalSubtrees2 != null) {
/*     */       
/* 374 */       generalSubtrees1 = this.permitted.intersect(generalSubtrees2);
/*     */ 
/*     */       
/* 377 */       if (generalSubtrees1 != null) {
/* 378 */         if (this.excluded != null) {
/* 379 */           this.excluded.union(generalSubtrees1);
/*     */         } else {
/* 381 */           this.excluded = (GeneralSubtrees)generalSubtrees1.clone();
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 391 */     if (this.permitted != null) {
/* 392 */       this.permitted.reduce(this.excluded);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 397 */     encodeThis();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verify(X509Certificate paramX509Certificate) throws IOException {
/* 415 */     if (paramX509Certificate == null) {
/* 416 */       throw new IOException("Certificate is null");
/*     */     }
/*     */ 
/*     */     
/* 420 */     if (!this.minMaxValid) {
/* 421 */       calcMinMax();
/*     */     }
/*     */     
/* 424 */     if (this.hasMin) {
/* 425 */       throw new IOException("Non-zero minimum BaseDistance in name constraints not supported");
/*     */     }
/*     */ 
/*     */     
/* 429 */     if (this.hasMax) {
/* 430 */       throw new IOException("Maximum BaseDistance in name constraints not supported");
/*     */     }
/*     */ 
/*     */     
/* 434 */     X500Principal x500Principal = paramX509Certificate.getSubjectX500Principal();
/* 435 */     X500Name x500Name = X500Name.asX500Name(x500Principal);
/*     */ 
/*     */     
/* 438 */     if (!x500Name.isEmpty() && 
/* 439 */       !verify(x500Name)) {
/* 440 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 444 */     GeneralNames generalNames = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 449 */       X509CertImpl x509CertImpl = X509CertImpl.toImpl(paramX509Certificate);
/*     */       
/* 451 */       SubjectAlternativeNameExtension subjectAlternativeNameExtension = x509CertImpl.getSubjectAlternativeNameExtension();
/* 452 */       if (subjectAlternativeNameExtension != null)
/*     */       {
/*     */         
/* 455 */         generalNames = subjectAlternativeNameExtension.get("subject_name");
/*     */       }
/*     */     }
/* 458 */     catch (CertificateException certificateException) {
/* 459 */       throw new IOException("Unable to extract extensions from certificate: " + certificateException
/* 460 */           .getMessage());
/*     */     } 
/*     */     
/* 463 */     if (generalNames == null) {
/* 464 */       generalNames = new GeneralNames();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 471 */       for (AVA aVA : x500Name.allAvas()) {
/* 472 */         ObjectIdentifier objectIdentifier = aVA.getObjectIdentifier();
/* 473 */         if (objectIdentifier.equals(PKCS9Attribute.EMAIL_ADDRESS_OID)) {
/* 474 */           String str1 = aVA.getValueString();
/* 475 */           if (str1 != null) {
/*     */             try {
/* 477 */               generalNames.add(new GeneralName(new RFC822Name(str1)));
/*     */             }
/* 479 */             catch (IOException iOException) {}
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 490 */     DerValue derValue = x500Name.findMostSpecificAttribute(X500Name.commonName_oid);
/* 491 */     String str = (derValue == null) ? null : derValue.getAsString();
/*     */     
/* 493 */     if (str != null) {
/*     */       try {
/* 495 */         if (IPAddressUtil.isIPv4LiteralAddress(str) || 
/* 496 */           IPAddressUtil.isIPv6LiteralAddress(str)) {
/* 497 */           if (!hasNameType(generalNames, 7)) {
/* 498 */             generalNames.add(new GeneralName(new IPAddressName(str)));
/*     */           }
/*     */         }
/* 501 */         else if (!hasNameType(generalNames, 2)) {
/* 502 */           generalNames.add(new GeneralName(new DNSName(str)));
/*     */         }
/*     */       
/* 505 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 511 */     for (byte b = 0; b < generalNames.size(); b++) {
/* 512 */       GeneralNameInterface generalNameInterface = generalNames.get(b).getName();
/* 513 */       if (!verify(generalNameInterface)) {
/* 514 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 519 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean hasNameType(GeneralNames paramGeneralNames, int paramInt) {
/* 523 */     for (GeneralName generalName : paramGeneralNames.names()) {
/* 524 */       if (generalName.getType() == paramInt) {
/* 525 */         return true;
/*     */       }
/*     */     } 
/* 528 */     return false;
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
/*     */   public boolean verify(GeneralNameInterface paramGeneralNameInterface) throws IOException {
/* 541 */     if (paramGeneralNameInterface == null) {
/* 542 */       throw new IOException("name is null");
/*     */     }
/*     */ 
/*     */     
/* 546 */     if (this.excluded != null && this.excluded.size() > 0)
/*     */     {
/* 548 */       for (byte b = 0; b < this.excluded.size(); b++) {
/* 549 */         GeneralSubtree generalSubtree = this.excluded.get(b);
/* 550 */         if (generalSubtree != null) {
/*     */           
/* 552 */           GeneralName generalName = generalSubtree.getName();
/* 553 */           if (generalName != null) {
/*     */             
/* 555 */             GeneralNameInterface generalNameInterface = generalName.getName();
/* 556 */             if (generalNameInterface != null)
/*     */             {
/*     */ 
/*     */ 
/*     */               
/* 561 */               switch (generalNameInterface.constrains(paramGeneralNameInterface)) {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 case 0:
/*     */                 case 1:
/* 568 */                   return false;
/*     */               }  } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 574 */     if (this.permitted != null && this.permitted.size() > 0) {
/*     */       
/* 576 */       boolean bool = false;
/*     */       
/* 578 */       for (byte b = 0; b < this.permitted.size(); b++) {
/* 579 */         GeneralSubtree generalSubtree = this.permitted.get(b);
/* 580 */         if (generalSubtree != null) {
/*     */           
/* 582 */           GeneralName generalName = generalSubtree.getName();
/* 583 */           if (generalName != null) {
/*     */             
/* 585 */             GeneralNameInterface generalNameInterface = generalName.getName();
/* 586 */             if (generalNameInterface != null)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 592 */               switch (generalNameInterface.constrains(paramGeneralNameInterface))
/*     */               
/*     */               { 
/*     */                 case 2:
/*     */                 case 3:
/* 597 */                   bool = true;
/*     */                   break;
/*     */                 
/*     */                 case 0:
/*     */                 case 1:
/* 602 */                   return true; }  } 
/*     */           } 
/*     */         } 
/* 605 */       }  if (bool) {
/* 606 */         return false;
/*     */       }
/*     */     } 
/* 609 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 618 */       NameConstraintsExtension nameConstraintsExtension = (NameConstraintsExtension)super.clone();
/*     */       
/* 620 */       if (this.permitted != null) {
/* 621 */         nameConstraintsExtension.permitted = (GeneralSubtrees)this.permitted.clone();
/*     */       }
/* 623 */       if (this.excluded != null) {
/* 624 */         nameConstraintsExtension.excluded = (GeneralSubtrees)this.excluded.clone();
/*     */       }
/* 626 */       return nameConstraintsExtension;
/* 627 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 628 */       throw new RuntimeException("CloneNotSupportedException while cloning NameConstraintsException. This should never happen.");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/NameConstraintsExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */