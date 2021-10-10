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
/*     */ import java.security.interfaces.DSAPublicKey;
/*     */ import java.security.spec.DSAPublicKeySpec;
/*     */ import java.security.spec.InvalidKeySpecException;
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
/*     */ public class DSAKeyValue
/*     */   extends SignatureElementProxy
/*     */   implements KeyValueContent
/*     */ {
/*     */   public DSAKeyValue(Element paramElement, String paramString) throws XMLSecurityException {
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
/*     */ 
/*     */   
/*     */   public DSAKeyValue(Document paramDocument, BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4) {
/*  65 */     super(paramDocument);
/*     */     
/*  67 */     XMLUtils.addReturnToElement(this.constructionElement);
/*  68 */     addBigIntegerElement(paramBigInteger1, "P");
/*  69 */     addBigIntegerElement(paramBigInteger2, "Q");
/*  70 */     addBigIntegerElement(paramBigInteger3, "G");
/*  71 */     addBigIntegerElement(paramBigInteger4, "Y");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DSAKeyValue(Document paramDocument, Key paramKey) throws IllegalArgumentException {
/*  82 */     super(paramDocument);
/*     */     
/*  84 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */     
/*  86 */     if (paramKey instanceof DSAPublicKey) {
/*  87 */       addBigIntegerElement(((DSAPublicKey)paramKey).getParams().getP(), "P");
/*  88 */       addBigIntegerElement(((DSAPublicKey)paramKey).getParams().getQ(), "Q");
/*  89 */       addBigIntegerElement(((DSAPublicKey)paramKey).getParams().getG(), "G");
/*  90 */       addBigIntegerElement(((DSAPublicKey)paramKey).getY(), "Y");
/*     */     } else {
/*  92 */       Object[] arrayOfObject = { "DSAKeyValue", paramKey.getClass().getName() };
/*     */       
/*  94 */       throw new IllegalArgumentException(I18n.translate("KeyValue.IllegalArgument", arrayOfObject));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey getPublicKey() throws XMLSecurityException {
/*     */     try {
/* 112 */       DSAPublicKeySpec dSAPublicKeySpec = new DSAPublicKeySpec(getBigIntegerFromChildElement("Y", "http://www.w3.org/2000/09/xmldsig#"), getBigIntegerFromChildElement("P", "http://www.w3.org/2000/09/xmldsig#"), getBigIntegerFromChildElement("Q", "http://www.w3.org/2000/09/xmldsig#"), getBigIntegerFromChildElement("G", "http://www.w3.org/2000/09/xmldsig#"));
/*     */ 
/*     */ 
/*     */       
/* 116 */       KeyFactory keyFactory = KeyFactory.getInstance("DSA");
/* 117 */       return keyFactory.generatePublic(dSAPublicKeySpec);
/*     */     
/*     */     }
/* 120 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 121 */       throw new XMLSecurityException("empty", noSuchAlgorithmException);
/* 122 */     } catch (InvalidKeySpecException invalidKeySpecException) {
/* 123 */       throw new XMLSecurityException("empty", invalidKeySpecException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 129 */     return "DSAKeyValue";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/keyvalues/DSAKeyValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */