/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthorityKeyIdentifierExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.AuthorityKeyIdentifier";
/*     */   public static final String NAME = "AuthorityKeyIdentifier";
/*     */   public static final String KEY_ID = "key_id";
/*     */   public static final String AUTH_NAME = "auth_name";
/*     */   public static final String SERIAL_NUMBER = "serial_number";
/*     */   private static final byte TAG_ID = 0;
/*     */   private static final byte TAG_NAMES = 1;
/*     */   private static final byte TAG_SERIAL_NUM = 2;
/*  78 */   private KeyIdentifier id = null;
/*  79 */   private GeneralNames names = null;
/*  80 */   private SerialNumber serialNum = null;
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/*  84 */     if (this.id == null && this.names == null && this.serialNum == null) {
/*  85 */       this.extensionValue = null;
/*     */       return;
/*     */     } 
/*  88 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*  89 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*  90 */     if (this.id != null) {
/*  91 */       DerOutputStream derOutputStream = new DerOutputStream();
/*  92 */       this.id.encode(derOutputStream);
/*  93 */       derOutputStream2.writeImplicit(DerValue.createTag(-128, false, (byte)0), derOutputStream);
/*     */     } 
/*     */     
/*     */     try {
/*  97 */       if (this.names != null) {
/*  98 */         DerOutputStream derOutputStream = new DerOutputStream();
/*  99 */         this.names.encode(derOutputStream);
/* 100 */         derOutputStream2.writeImplicit(DerValue.createTag(-128, true, (byte)1), derOutputStream);
/*     */       }
/*     */     
/* 103 */     } catch (Exception exception) {
/* 104 */       throw new IOException(exception.toString());
/*     */     } 
/* 106 */     if (this.serialNum != null) {
/* 107 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 108 */       this.serialNum.encode(derOutputStream);
/* 109 */       derOutputStream2.writeImplicit(DerValue.createTag(-128, false, (byte)2), derOutputStream);
/*     */     } 
/*     */     
/* 112 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 113 */     this.extensionValue = derOutputStream1.toByteArray();
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
/*     */   public AuthorityKeyIdentifierExtension(KeyIdentifier paramKeyIdentifier, GeneralNames paramGeneralNames, SerialNumber paramSerialNumber) throws IOException {
/* 129 */     this.id = paramKeyIdentifier;
/* 130 */     this.names = paramGeneralNames;
/* 131 */     this.serialNum = paramSerialNumber;
/*     */     
/* 133 */     this.extensionId = PKIXExtensions.AuthorityKey_Id;
/* 134 */     this.critical = false;
/* 135 */     encodeThis();
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
/*     */   public AuthorityKeyIdentifierExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 148 */     this.extensionId = PKIXExtensions.AuthorityKey_Id;
/* 149 */     this.critical = paramBoolean.booleanValue();
/*     */     
/* 151 */     this.extensionValue = (byte[])paramObject;
/* 152 */     DerValue derValue = new DerValue(this.extensionValue);
/* 153 */     if (derValue.tag != 48) {
/* 154 */       throw new IOException("Invalid encoding for AuthorityKeyIdentifierExtension.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     while (derValue.data != null && derValue.data.available() != 0) {
/* 162 */       DerValue derValue1 = derValue.data.getDerValue();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 167 */       if (derValue1.isContextSpecific((byte)0) && !derValue1.isConstructed()) {
/* 168 */         if (this.id != null) {
/* 169 */           throw new IOException("Duplicate KeyIdentifier in AuthorityKeyIdentifier.");
/*     */         }
/* 171 */         derValue1.resetTag((byte)4);
/* 172 */         this.id = new KeyIdentifier(derValue1); continue;
/*     */       } 
/* 174 */       if (derValue1.isContextSpecific((byte)1) && derValue1
/* 175 */         .isConstructed()) {
/* 176 */         if (this.names != null) {
/* 177 */           throw new IOException("Duplicate GeneralNames in AuthorityKeyIdentifier.");
/*     */         }
/* 179 */         derValue1.resetTag((byte)48);
/* 180 */         this.names = new GeneralNames(derValue1); continue;
/*     */       } 
/* 182 */       if (derValue1.isContextSpecific((byte)2) && 
/* 183 */         !derValue1.isConstructed()) {
/* 184 */         if (this.serialNum != null) {
/* 185 */           throw new IOException("Duplicate SerialNumber in AuthorityKeyIdentifier.");
/*     */         }
/* 187 */         derValue1.resetTag((byte)2);
/* 188 */         this.serialNum = new SerialNumber(derValue1); continue;
/*     */       } 
/* 190 */       throw new IOException("Invalid encoding of AuthorityKeyIdentifierExtension.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 199 */     String str = super.toString() + "AuthorityKeyIdentifier [\n";
/* 200 */     if (this.id != null) {
/* 201 */       str = str + this.id.toString();
/*     */     }
/* 203 */     if (this.names != null) {
/* 204 */       str = str + this.names.toString() + "\n";
/*     */     }
/* 206 */     if (this.serialNum != null) {
/* 207 */       str = str + this.serialNum.toString() + "\n";
/*     */     }
/* 209 */     return str + "]\n";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 219 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 220 */     if (this.extensionValue == null) {
/* 221 */       this.extensionId = PKIXExtensions.AuthorityKey_Id;
/* 222 */       this.critical = false;
/* 223 */       encodeThis();
/*     */     } 
/* 225 */     encode(derOutputStream);
/* 226 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 233 */     if (paramString.equalsIgnoreCase("key_id")) {
/* 234 */       if (!(paramObject instanceof KeyIdentifier)) {
/* 235 */         throw new IOException("Attribute value should be of type KeyIdentifier.");
/*     */       }
/*     */       
/* 238 */       this.id = (KeyIdentifier)paramObject;
/* 239 */     } else if (paramString.equalsIgnoreCase("auth_name")) {
/* 240 */       if (!(paramObject instanceof GeneralNames)) {
/* 241 */         throw new IOException("Attribute value should be of type GeneralNames.");
/*     */       }
/*     */       
/* 244 */       this.names = (GeneralNames)paramObject;
/* 245 */     } else if (paramString.equalsIgnoreCase("serial_number")) {
/* 246 */       if (!(paramObject instanceof SerialNumber)) {
/* 247 */         throw new IOException("Attribute value should be of type SerialNumber.");
/*     */       }
/*     */       
/* 250 */       this.serialNum = (SerialNumber)paramObject;
/*     */     } else {
/* 252 */       throw new IOException("Attribute name not recognized by CertAttrSet:AuthorityKeyIdentifier.");
/*     */     } 
/*     */     
/* 255 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(String paramString) throws IOException {
/* 262 */     if (paramString.equalsIgnoreCase("key_id"))
/* 263 */       return this.id; 
/* 264 */     if (paramString.equalsIgnoreCase("auth_name"))
/* 265 */       return this.names; 
/* 266 */     if (paramString.equalsIgnoreCase("serial_number")) {
/* 267 */       return this.serialNum;
/*     */     }
/* 269 */     throw new IOException("Attribute name not recognized by CertAttrSet:AuthorityKeyIdentifier.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 278 */     if (paramString.equalsIgnoreCase("key_id")) {
/* 279 */       this.id = null;
/* 280 */     } else if (paramString.equalsIgnoreCase("auth_name")) {
/* 281 */       this.names = null;
/* 282 */     } else if (paramString.equalsIgnoreCase("serial_number")) {
/* 283 */       this.serialNum = null;
/*     */     } else {
/* 285 */       throw new IOException("Attribute name not recognized by CertAttrSet:AuthorityKeyIdentifier.");
/*     */     } 
/*     */     
/* 288 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 296 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 297 */     attributeNameEnumeration.addElement("key_id");
/* 298 */     attributeNameEnumeration.addElement("auth_name");
/* 299 */     attributeNameEnumeration.addElement("serial_number");
/*     */     
/* 301 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 308 */     return "AuthorityKeyIdentifier";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncodedKeyIdentifier() throws IOException {
/* 315 */     if (this.id != null) {
/* 316 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 317 */       this.id.encode(derOutputStream);
/* 318 */       return derOutputStream.toByteArray();
/*     */     } 
/* 320 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/AuthorityKeyIdentifierExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */