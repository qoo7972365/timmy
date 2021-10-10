/*     */ package com.sun.org.apache.xml.internal.security.keys.content;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Signature11ElementProxy;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DEREncodedKeyValue
/*     */   extends Signature11ElementProxy
/*     */   implements KeyInfoContent
/*     */ {
/*  45 */   private static final String[] supportedKeyTypes = new String[] { "RSA", "DSA", "EC" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DEREncodedKeyValue(Element paramElement, String paramString) throws XMLSecurityException {
/*  55 */     super(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DEREncodedKeyValue(Document paramDocument, PublicKey paramPublicKey) throws XMLSecurityException {
/*  66 */     super(paramDocument);
/*     */     
/*  68 */     addBase64Text(getEncodedDER(paramPublicKey));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DEREncodedKeyValue(Document paramDocument, byte[] paramArrayOfbyte) {
/*  78 */     super(paramDocument);
/*     */     
/*  80 */     addBase64Text(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String paramString) {
/*  89 */     if (paramString != null) {
/*  90 */       this.constructionElement.setAttributeNS(null, "Id", paramString);
/*  91 */       this.constructionElement.setIdAttributeNS(null, "Id", true);
/*     */     } else {
/*  93 */       this.constructionElement.removeAttributeNS(null, "Id");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 103 */     return this.constructionElement.getAttributeNS(null, "Id");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 108 */     return "DEREncodedKeyValue";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey getPublicKey() throws XMLSecurityException {
/* 118 */     byte[] arrayOfByte = getBytesFromTextChild();
/*     */ 
/*     */     
/* 121 */     for (String str : supportedKeyTypes) {
/*     */       try {
/* 123 */         KeyFactory keyFactory = KeyFactory.getInstance(str);
/* 124 */         X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(arrayOfByte);
/* 125 */         PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
/* 126 */         if (publicKey != null) {
/* 127 */           return publicKey;
/*     */         }
/* 129 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */       
/* 131 */       } catch (InvalidKeySpecException invalidKeySpecException) {}
/*     */     } 
/*     */ 
/*     */     
/* 135 */     throw new XMLSecurityException("DEREncodedKeyValue.UnsupportedEncodedKey");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] getEncodedDER(PublicKey paramPublicKey) throws XMLSecurityException {
/*     */     try {
/* 146 */       KeyFactory keyFactory = KeyFactory.getInstance(paramPublicKey.getAlgorithm());
/* 147 */       X509EncodedKeySpec x509EncodedKeySpec = keyFactory.<X509EncodedKeySpec>getKeySpec(paramPublicKey, X509EncodedKeySpec.class);
/* 148 */       return x509EncodedKeySpec.getEncoded();
/* 149 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 150 */       Object[] arrayOfObject = { paramPublicKey.getAlgorithm(), paramPublicKey.getFormat(), paramPublicKey.getClass().getName() };
/* 151 */       throw new XMLSecurityException("DEREncodedKeyValue.UnsupportedPublicKey", arrayOfObject, noSuchAlgorithmException);
/* 152 */     } catch (InvalidKeySpecException invalidKeySpecException) {
/* 153 */       Object[] arrayOfObject = { paramPublicKey.getAlgorithm(), paramPublicKey.getFormat(), paramPublicKey.getClass().getName() };
/* 154 */       throw new XMLSecurityException("DEREncodedKeyValue.UnsupportedPublicKey", arrayOfObject, invalidKeySpecException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/DEREncodedKeyValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */