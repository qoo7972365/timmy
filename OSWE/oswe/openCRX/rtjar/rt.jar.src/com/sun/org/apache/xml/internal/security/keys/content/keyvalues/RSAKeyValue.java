/*     */ package com.sun.org.apache.xml.internal.security.keys.content.keyvalues;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.I18n;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.math.BigInteger;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.RSAPublicKeySpec;
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
/*     */ 
/*     */ public class RSAKeyValue
/*     */   extends SignatureElementProxy
/*     */   implements KeyValueContent
/*     */ {
/*     */   public RSAKeyValue(Element paramElement, String paramString) throws XMLSecurityException {
/*  52 */     super(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RSAKeyValue(Document paramDocument, BigInteger paramBigInteger1, BigInteger paramBigInteger2) {
/*  63 */     super(paramDocument);
/*     */     
/*  65 */     XMLUtils.addReturnToElement(this.constructionElement);
/*  66 */     addBigIntegerElement(paramBigInteger1, "Modulus");
/*  67 */     addBigIntegerElement(paramBigInteger2, "Exponent");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RSAKeyValue(Document paramDocument, Key paramKey) throws IllegalArgumentException {
/*  78 */     super(paramDocument);
/*     */     
/*  80 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */     
/*  82 */     if (paramKey instanceof RSAPublicKey) {
/*  83 */       addBigIntegerElement(((RSAPublicKey)paramKey)
/*  84 */           .getModulus(), "Modulus");
/*     */       
/*  86 */       addBigIntegerElement(((RSAPublicKey)paramKey)
/*  87 */           .getPublicExponent(), "Exponent");
/*     */     } else {
/*     */       
/*  90 */       Object[] arrayOfObject = { "RSAKeyValue", paramKey.getClass().getName() };
/*     */       
/*  92 */       throw new IllegalArgumentException(I18n.translate("KeyValue.IllegalArgument", arrayOfObject));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public PublicKey getPublicKey() throws XMLSecurityException {
/*     */     try {
/*  99 */       KeyFactory keyFactory = KeyFactory.getInstance("RSA");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 106 */       RSAPublicKeySpec rSAPublicKeySpec = new RSAPublicKeySpec(getBigIntegerFromChildElement("Modulus", "http://www.w3.org/2000/09/xmldsig#"), getBigIntegerFromChildElement("Exponent", "http://www.w3.org/2000/09/xmldsig#"));
/*     */ 
/*     */ 
/*     */       
/* 110 */       return keyFactory.generatePublic(rSAPublicKeySpec);
/*     */     
/*     */     }
/* 113 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 114 */       throw new XMLSecurityException("empty", noSuchAlgorithmException);
/* 115 */     } catch (InvalidKeySpecException invalidKeySpecException) {
/* 116 */       throw new XMLSecurityException("empty", invalidKeySpecException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 122 */     return "RSAKeyValue";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/keyvalues/RSAKeyValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */