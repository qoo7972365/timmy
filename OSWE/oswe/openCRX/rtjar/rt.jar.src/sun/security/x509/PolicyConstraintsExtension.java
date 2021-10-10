/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolicyConstraintsExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.PolicyConstraints";
/*     */   public static final String NAME = "PolicyConstraints";
/*     */   public static final String REQUIRE = "require";
/*     */   public static final String INHIBIT = "inhibit";
/*     */   private static final byte TAG_REQUIRE = 0;
/*     */   private static final byte TAG_INHIBIT = 1;
/*  76 */   private int require = -1;
/*  77 */   private int inhibit = -1;
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/*  81 */     if (this.require == -1 && this.inhibit == -1) {
/*  82 */       this.extensionValue = null;
/*     */       return;
/*     */     } 
/*  85 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*  86 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/*  88 */     if (this.require != -1) {
/*  89 */       DerOutputStream derOutputStream = new DerOutputStream();
/*  90 */       derOutputStream.putInteger(this.require);
/*  91 */       derOutputStream1.writeImplicit(DerValue.createTag(-128, false, (byte)0), derOutputStream);
/*     */     } 
/*     */     
/*  94 */     if (this.inhibit != -1) {
/*  95 */       DerOutputStream derOutputStream = new DerOutputStream();
/*  96 */       derOutputStream.putInteger(this.inhibit);
/*  97 */       derOutputStream1.writeImplicit(DerValue.createTag(-128, false, (byte)1), derOutputStream);
/*     */     } 
/*     */     
/* 100 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 101 */     this.extensionValue = derOutputStream2.toByteArray();
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
/*     */   public PolicyConstraintsExtension(int paramInt1, int paramInt2) throws IOException {
/* 114 */     this(Boolean.FALSE, paramInt1, paramInt2);
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
/*     */   public PolicyConstraintsExtension(Boolean paramBoolean, int paramInt1, int paramInt2) throws IOException {
/* 128 */     this.require = paramInt1;
/* 129 */     this.inhibit = paramInt2;
/* 130 */     this.extensionId = PKIXExtensions.PolicyConstraints_Id;
/* 131 */     this.critical = paramBoolean.booleanValue();
/* 132 */     encodeThis();
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
/*     */   public PolicyConstraintsExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 145 */     this.extensionId = PKIXExtensions.PolicyConstraints_Id;
/* 146 */     this.critical = paramBoolean.booleanValue();
/*     */     
/* 148 */     this.extensionValue = (byte[])paramObject;
/* 149 */     DerValue derValue = new DerValue(this.extensionValue);
/* 150 */     if (derValue.tag != 48) {
/* 151 */       throw new IOException("Sequence tag missing for PolicyConstraint.");
/*     */     }
/* 153 */     DerInputStream derInputStream = derValue.data;
/* 154 */     while (derInputStream != null && derInputStream.available() != 0) {
/* 155 */       DerValue derValue1 = derInputStream.getDerValue();
/*     */       
/* 157 */       if (derValue1.isContextSpecific((byte)0) && !derValue1.isConstructed()) {
/* 158 */         if (this.require != -1) {
/* 159 */           throw new IOException("Duplicate requireExplicitPolicyfound in the PolicyConstraintsExtension");
/*     */         }
/* 161 */         derValue1.resetTag((byte)2);
/* 162 */         this.require = derValue1.getInteger(); continue;
/*     */       } 
/* 164 */       if (derValue1.isContextSpecific((byte)1) && 
/* 165 */         !derValue1.isConstructed()) {
/* 166 */         if (this.inhibit != -1) {
/* 167 */           throw new IOException("Duplicate inhibitPolicyMappingfound in the PolicyConstraintsExtension");
/*     */         }
/* 169 */         derValue1.resetTag((byte)2);
/* 170 */         this.inhibit = derValue1.getInteger(); continue;
/*     */       } 
/* 172 */       throw new IOException("Invalid encoding of PolicyConstraint");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 181 */     String str = super.toString() + "PolicyConstraints: [  Require: ";
/* 182 */     if (this.require == -1) {
/* 183 */       str = str + "unspecified;";
/*     */     } else {
/* 185 */       str = str + this.require + ";";
/* 186 */     }  str = str + "\tInhibit: ";
/* 187 */     if (this.inhibit == -1) {
/* 188 */       str = str + "unspecified";
/*     */     } else {
/* 190 */       str = str + this.inhibit;
/* 191 */     }  str = str + " ]\n";
/* 192 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 202 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 203 */     if (this.extensionValue == null) {
/* 204 */       this.extensionId = PKIXExtensions.PolicyConstraints_Id;
/* 205 */       this.critical = false;
/* 206 */       encodeThis();
/*     */     } 
/* 208 */     encode(derOutputStream);
/* 209 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 216 */     if (!(paramObject instanceof Integer)) {
/* 217 */       throw new IOException("Attribute value should be of type Integer.");
/*     */     }
/* 219 */     if (paramString.equalsIgnoreCase("require")) {
/* 220 */       this.require = ((Integer)paramObject).intValue();
/* 221 */     } else if (paramString.equalsIgnoreCase("inhibit")) {
/* 222 */       this.inhibit = ((Integer)paramObject).intValue();
/*     */     } else {
/* 224 */       throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:PolicyConstraints.");
/*     */     } 
/*     */ 
/*     */     
/* 228 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer get(String paramString) throws IOException {
/* 235 */     if (paramString.equalsIgnoreCase("require"))
/* 236 */       return new Integer(this.require); 
/* 237 */     if (paramString.equalsIgnoreCase("inhibit")) {
/* 238 */       return new Integer(this.inhibit);
/*     */     }
/* 240 */     throw new IOException("Attribute name not recognized by CertAttrSet:PolicyConstraints.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 249 */     if (paramString.equalsIgnoreCase("require")) {
/* 250 */       this.require = -1;
/* 251 */     } else if (paramString.equalsIgnoreCase("inhibit")) {
/* 252 */       this.inhibit = -1;
/*     */     } else {
/* 254 */       throw new IOException("Attribute name not recognized by CertAttrSet:PolicyConstraints.");
/*     */     } 
/*     */     
/* 257 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 265 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 266 */     attributeNameEnumeration.addElement("require");
/* 267 */     attributeNameEnumeration.addElement("inhibit");
/*     */     
/* 269 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 276 */     return "PolicyConstraints";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/PolicyConstraintsExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */