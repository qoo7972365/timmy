/*     */ package sun.security.pkcs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Hashtable;
/*     */ import sun.security.util.DerEncoder;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ public class PKCS9Attributes
/*     */ {
/*  46 */   private final Hashtable<ObjectIdentifier, PKCS9Attribute> attributes = new Hashtable<>(3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Hashtable<ObjectIdentifier, ObjectIdentifier> permittedAttributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final byte[] derEncoding;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean ignoreUnsupportedAttributes = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS9Attributes(ObjectIdentifier[] paramArrayOfObjectIdentifier, DerInputStream paramDerInputStream) throws IOException {
/*  86 */     if (paramArrayOfObjectIdentifier != null) {
/*  87 */       this.permittedAttributes = new Hashtable<>(paramArrayOfObjectIdentifier.length);
/*     */ 
/*     */ 
/*     */       
/*  91 */       for (byte b = 0; b < paramArrayOfObjectIdentifier.length; b++) {
/*  92 */         this.permittedAttributes.put(paramArrayOfObjectIdentifier[b], paramArrayOfObjectIdentifier[b]);
/*     */       }
/*     */     } else {
/*  95 */       this.permittedAttributes = null;
/*     */     } 
/*     */ 
/*     */     
/*  99 */     this.derEncoding = decode(paramDerInputStream);
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
/*     */   public PKCS9Attributes(DerInputStream paramDerInputStream) throws IOException {
/* 116 */     this(paramDerInputStream, false);
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
/*     */   public PKCS9Attributes(DerInputStream paramDerInputStream, boolean paramBoolean) throws IOException {
/* 138 */     this.ignoreUnsupportedAttributes = paramBoolean;
/*     */     
/* 140 */     this.derEncoding = decode(paramDerInputStream);
/* 141 */     this.permittedAttributes = null;
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
/*     */   public PKCS9Attributes(PKCS9Attribute[] paramArrayOfPKCS9Attribute) throws IllegalArgumentException, IOException {
/* 160 */     for (byte b = 0; b < paramArrayOfPKCS9Attribute.length; b++) {
/* 161 */       ObjectIdentifier objectIdentifier = paramArrayOfPKCS9Attribute[b].getOID();
/* 162 */       if (this.attributes.containsKey(objectIdentifier)) {
/* 163 */         throw new IllegalArgumentException("PKCSAttribute " + paramArrayOfPKCS9Attribute[b]
/* 164 */             .getOID() + " duplicated while constructing PKCS9Attributes.");
/*     */       }
/*     */ 
/*     */       
/* 168 */       this.attributes.put(objectIdentifier, paramArrayOfPKCS9Attribute[b]);
/*     */     } 
/* 170 */     this.derEncoding = generateDerEncoding();
/* 171 */     this.permittedAttributes = null;
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
/*     */   private byte[] decode(DerInputStream paramDerInputStream) throws IOException {
/* 188 */     DerValue derValue = paramDerInputStream.getDerValue();
/*     */ 
/*     */     
/* 191 */     byte[] arrayOfByte = derValue.toByteArray();
/* 192 */     arrayOfByte[0] = 49;
/*     */     
/* 194 */     DerInputStream derInputStream = new DerInputStream(arrayOfByte);
/* 195 */     DerValue[] arrayOfDerValue = derInputStream.getSet(3, true);
/*     */ 
/*     */ 
/*     */     
/* 199 */     boolean bool = true;
/*     */     
/* 201 */     for (byte b = 0; b < arrayOfDerValue.length; b++) {
/*     */       PKCS9Attribute pKCS9Attribute;
/*     */       try {
/* 204 */         pKCS9Attribute = new PKCS9Attribute(arrayOfDerValue[b]);
/*     */       }
/* 206 */       catch (ParsingException parsingException) {
/* 207 */         if (this.ignoreUnsupportedAttributes) {
/* 208 */           bool = false;
/*     */         } else {
/*     */           
/* 211 */           throw parsingException;
/*     */         } 
/*     */       } 
/* 214 */       ObjectIdentifier objectIdentifier = pKCS9Attribute.getOID();
/*     */       
/* 216 */       if (this.attributes.get(objectIdentifier) != null) {
/* 217 */         throw new IOException("Duplicate PKCS9 attribute: " + objectIdentifier);
/*     */       }
/* 219 */       if (this.permittedAttributes != null && 
/* 220 */         !this.permittedAttributes.containsKey(objectIdentifier)) {
/* 221 */         throw new IOException("Attribute " + objectIdentifier + " not permitted in this attribute set");
/*     */       }
/*     */       
/* 224 */       this.attributes.put(objectIdentifier, pKCS9Attribute);
/*     */     } 
/* 226 */     return bool ? arrayOfByte : generateDerEncoding();
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
/*     */   public void encode(byte paramByte, OutputStream paramOutputStream) throws IOException {
/* 239 */     paramOutputStream.write(paramByte);
/* 240 */     paramOutputStream.write(this.derEncoding, 1, this.derEncoding.length - 1);
/*     */   }
/*     */   
/*     */   private byte[] generateDerEncoding() throws IOException {
/* 244 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 245 */     Object[] arrayOfObject = this.attributes.values().toArray();
/*     */     
/* 247 */     derOutputStream.putOrderedSetOf((byte)49, 
/* 248 */         castToDerEncoder(arrayOfObject));
/* 249 */     return derOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getDerEncoding() throws IOException {
/* 257 */     return (byte[])this.derEncoding.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS9Attribute getAttribute(ObjectIdentifier paramObjectIdentifier) {
/* 265 */     return this.attributes.get(paramObjectIdentifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS9Attribute getAttribute(String paramString) {
/* 272 */     return this.attributes.get(PKCS9Attribute.getOID(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS9Attribute[] getAttributes() {
/* 280 */     PKCS9Attribute[] arrayOfPKCS9Attribute = new PKCS9Attribute[this.attributes.size()];
/*     */ 
/*     */     
/* 283 */     byte b1 = 0;
/* 284 */     for (byte b2 = 1; b2 < PKCS9Attribute.PKCS9_OIDS.length && b1 < arrayOfPKCS9Attribute.length; 
/* 285 */       b2++) {
/* 286 */       arrayOfPKCS9Attribute[b1] = getAttribute(PKCS9Attribute.PKCS9_OIDS[b2]);
/*     */       
/* 288 */       if (arrayOfPKCS9Attribute[b1] != null)
/* 289 */         b1++; 
/*     */     } 
/* 291 */     return arrayOfPKCS9Attribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getAttributeValue(ObjectIdentifier paramObjectIdentifier) throws IOException {
/*     */     try {
/* 300 */       return getAttribute(paramObjectIdentifier).getValue();
/*     */     }
/* 302 */     catch (NullPointerException nullPointerException) {
/* 303 */       throw new IOException("No value found for attribute " + paramObjectIdentifier);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getAttributeValue(String paramString) throws IOException {
/* 312 */     ObjectIdentifier objectIdentifier = PKCS9Attribute.getOID(paramString);
/*     */     
/* 314 */     if (objectIdentifier == null) {
/* 315 */       throw new IOException("Attribute name " + paramString + " not recognized or not supported.");
/*     */     }
/*     */     
/* 318 */     return getAttributeValue(objectIdentifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 326 */     StringBuffer stringBuffer = new StringBuffer(200);
/* 327 */     stringBuffer.append("PKCS9 Attributes: [\n\t");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 332 */     boolean bool = true;
/* 333 */     for (byte b = 1; b < PKCS9Attribute.PKCS9_OIDS.length; b++) {
/* 334 */       PKCS9Attribute pKCS9Attribute = getAttribute(PKCS9Attribute.PKCS9_OIDS[b]);
/*     */       
/* 336 */       if (pKCS9Attribute != null) {
/*     */ 
/*     */         
/* 339 */         if (bool) {
/* 340 */           bool = false;
/*     */         } else {
/* 342 */           stringBuffer.append(";\n\t");
/*     */         } 
/* 344 */         stringBuffer.append(pKCS9Attribute.toString());
/*     */       } 
/*     */     } 
/* 347 */     stringBuffer.append("\n\t] (end PKCS9 Attributes)");
/*     */     
/* 349 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static DerEncoder[] castToDerEncoder(Object[] paramArrayOfObject) {
/* 358 */     DerEncoder[] arrayOfDerEncoder = new DerEncoder[paramArrayOfObject.length];
/*     */     
/* 360 */     for (byte b = 0; b < arrayOfDerEncoder.length; b++) {
/* 361 */       arrayOfDerEncoder[b] = (DerEncoder)paramArrayOfObject[b];
/*     */     }
/* 363 */     return arrayOfDerEncoder;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs/PKCS9Attributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */